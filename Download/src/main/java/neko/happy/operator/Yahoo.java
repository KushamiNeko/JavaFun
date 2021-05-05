package neko.happy.operator;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.io.Files;

public class Yahoo extends Operator {

  private final String baseUrl = "https://finance.yahoo.com/quote/%s/history?";

  private final Map<String, String> source = Map.ofEntries(
      // Map.entry("btc-usd", "20141001"),
      // Map.entry("eth-usd", "20150901"),
      // Map.entry("ltc-usd", "20141001"),
      // Map.entry("bch-usd", "20170801"),
      // Map.entry("xrp-usd", "20141001"),
      // Map.entry("usdc-usd", "20200601"),
      //
      Map.entry("^vix", "19900101"),
      Map.entry("^vxn", "20000101"),
      // Map.entry("^ovx", "20070101"),
      // Map.entry("^gvz", "20100101"),
      //
      /* Map.entry("^gspc", "19270101"),
      Map.entry("^ixic", "19710101"),
      Map.entry("^ndx", "19850101"),
      Map.entry("^rut", "19880101"),
      // "^sml": "19890101",
      Map.entry("^dji", "19860101"),
      Map.entry("^n225", "19650101"),
      // "^nya": "19650101",
      Map.entry("ezu", "20000101"),
      Map.entry("eem", "20030101"),
      Map.entry("^hsi", "19860101"),
      Map.entry("fxi", "20040101") */
      //
      Map.entry("icsh", "20140101"),
      Map.entry("jpst", "20170701"),
      Map.entry("gsy", "20080301"),
      Map.entry("near", "20130101"),
      Map.entry("shv", "20070101"),
      Map.entry("flot", "20110701"),
      Map.entry("ushy", "20180101"),
      Map.entry("shyg", "20140101"),
      Map.entry("hyg", "20070101"),
      Map.entry("sjnk", "20120501"),
      Map.entry("jnk", "20080101"),
      Map.entry("faln", "20160701"),
      Map.entry("emb", "20070101"),
      Map.entry("pcy", "20071101"),
      Map.entry("emhy", "20120501"),
      Map.entry("bkln", "20110401"),
      Map.entry("srln", "20130501"),
      Map.entry("lqd", "20020101"),
      Map.entry("mbb", "20070401"),
      // Map.entry("mub", "20071001"),
      Map.entry("shy", "20020801"),
      Map.entry("iei", "20070201"),
      Map.entry("ief", "20020801"),
      Map.entry("tlh", "20070201"),
      Map.entry("tlt", "20020801"),
      //
      // Map.entry("iyr", "20000101"),
      // Map.entry("rem", "20070101"),
      // Map.entry("reet", "20140801"),
      //
      Map.entry("pff", "20070401"),
      Map.entry("pgx", "20080201"),
      // Map.entry("pgf", "20070101"),
      Map.entry("hdv", "20110401"),
      Map.entry("dvy", "20031201"),
      // Map.entry("idv", "20070701"),
      // Map.entry("dvye", "20120301"),
      Map.entry("spyd", "20151101"),
      Map.entry("sphd", "20121101")
      // Map.entry("sdy", "20051201"),
      // Map.entry("schd", "20111101"),
      // Map.entry("vym", "20061201"),
      // Map.entry("dgro", "20140701")
  //
  // "usig": "20070201",
  // "slqd": "20140101",
  // "igsb": "20070201",
  // "igib": "20070201",
  // "iglb": "20100101",
  // "qlta": "20120301",
  // "lqdh": "20140701",
  // "govt": "20120301",
  // "igov": "20090201",
  // "stip": "20110101",
  // "tip": "20040101",
  // "sub": "20090101",
  // "icvt": "20150701",
  // "istb": "20130101",
  // "iusb": "20140701",
  // "agg": "20040101",
  // "byld": "20140501",
  );

  public Yahoo() {
    super();
  }

  public Yahoo(String srcDir, String dstDir) {
    super(srcDir, dstDir);
  }

  private String getPage(String symbol, String datetime) throws UnsupportedEncodingException {

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    final long start = LocalDate.parse(datetime, formatter).toEpochSecond(LocalTime.of(0, 0, 0), ZoneOffset.UTC);

    final long end = LocalDate.now().plusDays(5).toEpochSecond(LocalTime.of(0, 0, 0), ZoneOffset.UTC);

    final StringBuilder builder = new StringBuilder(
        String.format(this.baseUrl, URLEncoder.encode(symbol, StandardCharsets.UTF_8.toString()))
    );

    builder.append(String.format("period1=%d&", start));
    builder.append(String.format("period2=%d&", end));
    builder.append("interval=1d&filter=history&frequency=1d");

    return builder.toString();
  }

  private String renameSymbol(String symbol) {
    switch (symbol) {
      case "n225":
        return "nikk";
      case "gspc":
        return "spx";
      case "ixic":
        return "compq";
      case "dot1usd":
        return "dotusd";
      default:
        return symbol;
    }
  }

  @Override
  public void greeting() {
    this.greetingMessage("Yahoo");
  }

  @Override
  public void download() {
    this.source.forEach((symbol, datetime) -> {
      try {
        this.downloadOp(this.getPage(symbol, datetime), symbol);
        this.checkProcessLimit();
      } catch (Exception err) {
        this.error(err);
      }
    });

    this.downloadCompleted();
  }

  @Override
  public void rename() {
    final Pattern regex = Pattern.compile("(\\^(\\w+))");

    final File[] files = new File(this.srcDir).listFiles();

    for (File f : files) {
      if (!f.isFile()) {
        continue;
      }

      String symbol = f.getName().toLowerCase()
          .replaceAll(String.format(".%s", Files.getFileExtension(f.getName())), "");

      if (this.source.containsKey(symbol)) {
        Matcher match = regex.matcher(symbol);
        if (match.find()) {
          symbol = match.group(2).toLowerCase();
        }

        symbol = symbol.replaceAll("-", "");
        symbol = this.renameSymbol(symbol);

        final String srcPath = Paths.get(this.srcDir, f.getName()).toString();
        // final String dstPath = Paths.get(this.dstDir, "yahoo",
        // String.format("%s.csv", symbol)).toString();
        final String dstPath = this.getDstPath(symbol);

        this.renameOp(srcPath, dstPath);
      }
    }

    this.renameCompleted();
  }

  @Override
  public void check() {
    final Pattern regex = Pattern.compile("(\\^(\\w+))");

    this.source.forEach((symbol, datetime) -> {
      Matcher match = regex.matcher(symbol);
      if (match.find()) {
        symbol = match.group(2).toLowerCase();
      }

      symbol = symbol.replaceAll("-", "");
      symbol = this.renameSymbol(symbol);

      // final String filePath = Paths.get(this.dstDir, "yahoo",
      // String.format("%s.csv", symbol)).toString();
      final String filePath = this.getDstPath(symbol);
      this.checkOp(filePath);

    });

    this.checkCompleted();
  }

  String getDstPath(String symbol) {
    return Paths.get(this.dstDir, "yahoo", String.format("%s.csv", symbol)).toString();
  }

}
