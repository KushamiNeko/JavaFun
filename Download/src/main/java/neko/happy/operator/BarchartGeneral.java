package neko.happy.operator;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import neko.fun.utils.Pretty;

public class BarchartGeneral extends Barchart {

  private final Map<String, String> source = Map.ofEntries(
      // Map.entry("$iqx", "spxew"),
      // Map.entry("$slew", "smlew"),
      // Map.entry("$sdew", "midew"),
      // Map.entry("$topx", "topix"),
      // Map.entry("$addn", "addn"),
      // Map.entry("$addq", "addq"),
      // Map.entry("$avdn", "avdn"),
      // Map.entry("$avdq", "avdq"),
      // Map.entry("$addt", "addt"),
      // Map.entry("$avdt", "avdt"),
      //
      // Map.entry("fedfunds.rt", "fedfunds"),
      // Map.entry("ustm1.rt", "ustm1"),
      Map.entry("ustm3.rt", "ustm3"),
      // Map.entry("ustm6.rt", "ustm6"),
      Map.entry("usty2.rt", "usty2"),
      // Map.entry("usty5.rt", "usty5"),
      Map.entry("usty10.rt", "usty10"),
      // Map.entry("usty30.rt", "usty30"),
      //
      Map.entry("$vstx", "vstx"),
      //
      // Map.entry("^btcusd", "btcusd"),
      // Map.entry("^ethusd", "ethusd"),
      // Map.entry("^ltcusd", "ltcusd"),
      // Map.entry("^bchusd", "bchusd"),
      // Map.entry("^xrpusd", "xrpusd"),
      //
      Map.entry("$dxy", "dxy"),
      Map.entry("^eurusd", "eurusd"),
      Map.entry("^usdjpy", "usdjpy"),
      // Map.entry("^jpyusd", "jpyusd"),
      Map.entry("^gbpusd", "gbpusd"),
      Map.entry("^audusd", "audusd"),
      Map.entry("^usdcad", "usdcad"),
      // Map.entry("^cadusd", "cadusd"),
      Map.entry("^usdchf", "usdchf"),
      // Map.entry("^chfusd", "chfusd"),
      Map.entry("^nzdusd", "nzdusd")
  //
  // Map.entry("^eurjpy", "eurjpy"),
  // Map.entry("^eurgbp", "eurgbp"),
  // Map.entry("^euraud", "euraud"),
  // Map.entry("^eurcad", "eurcad"),
  // Map.entry("^eurchf", "eurchf"),
  // Map.entry("^gbpjpy", "gbpjpy"),
  // Map.entry("^audjpy", "audjpy"),
  // Map.entry("^cadjpy", "cadjpy")
  );

  public BarchartGeneral() {
    super();
  }

  public BarchartGeneral(String srcDir, String dstDir) {
    super(srcDir, dstDir);
  }

  public BarchartGeneral fromHistorical() {
    this.barchartPage = BarchartPage.HISTORICAL;
    return this;
  }

  public BarchartGeneral fromInteractive() {
    this.barchartPage = BarchartPage.INTERACTIVE;
    return this;
  }

  @Override
  public void greeting() {
    this.greetingMessage("Barchart General");
  }

  @Override
  public void download() {
    this.source.forEach((symbol, datetime) -> {
      try {
        this.downloadOp(this.getPage(symbol), symbol);
        this.checkProcessLimit();
      } catch (Exception err) {
        this.error(err);
      }
    });

    this.downloadCompleted();
  }

  @Override
  public void rename() {
    final Pattern regex = Pattern
        .compile(String.format(this.barchartPage.getFilePattern(), this.symbolPatternForexOrIndex));

    final File[] files = new File(this.srcDir).listFiles();

    for (File f : files) {
      if (!f.isFile()) {
        continue;
      }

      final Matcher match = regex.matcher(f.getName());
      if (match.find()) {
        String symbol = match.group(1);

        if (this.source.containsKey(symbol)) {
          symbol = this.source.get(symbol);

          final String srcPath = Paths.get(this.srcDir, f.getName()).toString();
          // final String dstPath = Paths.get(this.dstDir, "barchart",
          // String.format("%s.csv", symbol)).toString();
          final String dstPath = this.getDstPath(symbol);

          this.renameOp(srcPath, dstPath);
        } else {
          Pretty.colorPrintln(
              Pretty.PaperPink300,
              String.format("barchart general operator skips renaming symbol: %s", symbol)
          );
          continue;
        }
      }
    }

    this.renameCompleted();
  }

  @Override
  public void check() {
    this.source.forEach((code, symbol) -> {
      // final String filePath = Paths.get(this.dstDir, "barchart",
      // String.format("%s.csv", symbol)).toString();
      final String filePath = this.getDstPath(symbol);
      this.checkOp(filePath);

    });

    this.checkCompleted();
  }

  String getDstPath(String symbol) {
    return Paths.get(this.dstDir, "barchart", String.format("%s.csv", symbol)).toString();
  }
}
