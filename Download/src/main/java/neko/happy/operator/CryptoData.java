package neko.happy.operator;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CryptoData extends Operator {

  private final String baseUrl = "https://www.cryptodatadownload.com/cdd/%s_%s_d.csv";

  private final List<String> source = List.of(
      String.format(this.baseUrl, "Bitstamp", "BTCUSD"),
      String.format(this.baseUrl, "Bitstamp", "ETHUSD"),
      String.format(this.baseUrl, "Bitstamp", "LTCUSD"),
      String.format(this.baseUrl, "Bitstamp", "XRPUSD"),
      String.format(this.baseUrl, "Bitstamp", "BCHUSD")
      //
      // String.format(this.baseUrl, "Kraken", "BTCUSD"),
      // String.format(this.baseUrl, "Kraken", "ETHUSD"),
      // String.format(this.baseUrl, "Kraken", "LTCUSD"),
      // String.format(this.baseUrl, "Kraken", "XRPUSD"),
      // String.format(this.baseUrl, "Kraken", "BCHUSD"),
      // String.format(this.baseUrl, "Kraken", "LINKUSD"),
      // String.format(this.baseUrl, "Kraken", "DOTUSD"),
      // String.format(this.baseUrl, "Kraken", "EOSUSD"),
      // String.format(this.baseUrl, "Kraken", "ADAUSD"),
      // String.format(this.baseUrl, "Kraken", "XMRUSD"),
      // String.format(this.baseUrl, "Kraken", "DASHUSD"),
      // String.format(this.baseUrl, "Kraken", "ZECUSD"),
      // String.format(this.baseUrl, "Kraken", "TRXUSD"),
      // String.format(this.baseUrl, "Kraken", "ETCUSD"),
      // String.format(this.baseUrl, "Bitfinex", "NEOUSD"),
      // String.format(this.baseUrl, "Bitfinex", "XLMUSD")
  );

  public CryptoData() {
    super();
  }

  public CryptoData(String srcDir, String dstDir) {
    super(srcDir, dstDir);
  }

  @Override
  public void greeting() {
    this.greetingMessage("CryptoData");
  }

  @Override
  public void download() {
    final Pattern regex = Pattern.compile("https://www.cryptodatadownload.com/cdd/[a-zA-Z]+_([a-zA-Z]+)_d.csv");

    this.source.forEach((url) -> {
      final Matcher match = regex.matcher(url);
      if (match.find()) {
        final String symbol = match.group(1);
        this.downloadOp(url, symbol);
        this.checkProcessLimit();
      } else {
        this.error(new Exception(String.format("cryptodata url regex fail to match url: %s", url)));
      }

    });

    this.downloadCompleted();
  }

  @Override
  public void rename() {
    final Pattern regex = Pattern.compile("^[a-zA-Z]+_([a-zA-Z]+)_d.csv$");

    final File[] files = new File(this.srcDir).listFiles();

    for (File f : files) {
      if (!f.isFile()) {
        continue;
      }

      final Matcher match = regex.matcher(f.getName());
      if (match.find()) {
        final String symbol = match.group(1).toLowerCase();

        final String srcPath = Paths.get(this.srcDir, f.getName()).toString();
        // final String dstPath = Paths.get(this.dstDir, "cryptodata",
        // String.format("%s.csv", symbol)).toString();
        final String dstPath = this.getDstPath(symbol);

        this.renameOp(srcPath, dstPath);
      }

    }

    this.renameCompleted();
  }

  @Override
  public void check() {
    final Pattern regex = Pattern.compile("https://www.cryptodatadownload.com/cdd/[a-zA-Z]+_([a-zA-Z]+)_d.csv");

    this.source.forEach((url) -> {
      final Matcher match = regex.matcher(url);

      if (match.find()) {
        final String symbol = match.group(1).toLowerCase();
        // final String filePath = Paths.get(this.dstDir, "cryptodata",
        // String.format("%s.csv", symbol)).toString();
        final String filePath = this.getDstPath(symbol);
        this.checkOp(filePath);
      }

    });

    this.checkCompleted();
  }

  String getDstPath(String symbol) {
    return Paths.get(this.dstDir, "cryptodata", String.format("%s.csv", symbol)).toString();
  }

}
