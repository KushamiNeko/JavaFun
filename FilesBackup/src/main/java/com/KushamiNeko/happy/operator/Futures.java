package com.KushamiNeko.happy.operator;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.KushamiNeko.happy.futures.ContractMonths;
import com.KushamiNeko.happy.pretty.Pretty;
import com.KushamiNeko.happy.pretty.Utils;

public class Futures extends Barchart {

  private List<String> source = List.of(
      "es",
      "nq",
      "qr",
      "ym",
      "np",
      "fx",
      "zn",
      "zf",
      "zt",
      "zb",
      "ge",
      "tj",
      "gg",
      "dx",
      "e6",
      "j6",
      "b6",
      "a6",
      "d6",
      "s6",
      "n6",
      "gc",
      "si",
      "cl",
      "ng",
      "zs",
      "zc",
      "zw"
  );

  private String start = null;
  private String end = null;

  public Futures() {
    super();
  }

  public Futures(String srcDir, String dstDir) {
    super(srcDir, dstDir);
  }

  public Futures setCustomRange(String start, String end) {
    if (start != null && end != null) {
      final String pattern = "^\\d{6}$";

      Utils.validateInput(start, pattern);
      this.start = start;

      Utils.validateInput(end, pattern);
      this.end = end;
    } else {
      throw new IllegalArgumentException("range start and range end should not be null");
    }

    return this;
  }

  public Futures setCustomSymbols(List<String> symbols) {
    if (symbols != null) {
      this.source = symbols;
    }

    return this;
  }

  void process(Consumer<String> func) {
    this.source.forEach((symbol) -> {
      final ContractMonths months = new ContractMonths(symbol);

      try {
        final int iStart = Integer.parseInt(this.start);
        final int iEnd = Integer.parseInt(this.end);

        final int startYear = iStart / 100;
        final int endYear = iEnd / 100;

        for (int i = startYear; i <= endYear; i++) {
          final int y = i;

          months.forEach((mc) -> {
            final int ic = (y * 100) + mc.getMonthValue();

            if (ic >= iStart && ic <= iEnd) {
              final String code = String.format("%s%s%02d", symbol, mc.getCode(), y % 100);
              func.accept(code);
            }
          });

        }

      } catch (Exception err) {
        this.error(err);
      }
    });

  }

  @Override
  public void download() {
    this.process((String code) -> {
      try {
        this.downloadOp(this.getPage(code), code);
        this.checkProcessLimit();
      } catch (Exception err) {
        this.error(err);
      }
    });

    this.downloadCompleted();
  }

  @Override
  public void rename() {
    final Pattern regex = Pattern.compile(String.format(this.barchartPage.getFilePattern(), this.symbolPatternFutures));

    final File[] files = new File(this.srcDir).listFiles();

    for (File f : files) {
      if (!f.isFile()) {
        continue;
      }

      final Matcher match = regex.matcher(f.getName());
      if (match.find()) {
        final String code = match.group(1);

        if (this.source.contains(code.substring(0, 2))) {
          final String srcPath = Paths.get(this.srcDir, f.getName()).toString();
          final String dstPath = Paths
              .get(this.dstDir, "continuous", code.substring(0, 2), String.format("%s.csv", code)).toString();

          this.renameOp(srcPath, dstPath);
        } else {
          Pretty.colorPrintln(
              Pretty.PaperPink300,
              String.format("barchart futures operator skips renaming symbol: %s", code)
          );
          continue;
        }
      }
    }

    this.renameCompleted();
  }

  @Override
  public void check() {
    this.process((String code) -> {
      final String filePath = Paths.get(this.dstDir, "continuous", code.substring(0, 2), String.format("%s.csv", code))
          .toString();
      this.checkOp(filePath);
    });

    this.checkCompleted();
  }
}
