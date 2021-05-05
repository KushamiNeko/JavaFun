package neko.happy.operator;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import neko.fun.utils.Pretty;

public class Investing extends Operator {

  private final Map<String, String> source = Map.ofEntries(
      Map.entry("https://www.investing.com/indices/stoxx-50-volatility-vstoxx-eur-historical-data", "vstx"),
      // Map.entry("https://www.investing.com/indices/jpx-nikkei-400-historical-data", "nk400"),
      Map.entry("https://www.investing.com/indices/nikkei-volatility-historical-data", "jniv"),
      Map.entry("https://www.investing.com/indices/hsi-volatility-historical-data", "vhsi")
      // Map.entry("https://www.investing.com/indices/cboe-china-etf-volatility-historical-data", "vxfxi")
      // Map.entry("https://www.investing.com/crypto/uniswap/historical-data",
      // "uniusd")
      //
      // Map.entry("https://www.investing.com/crypto/bitcoin/historical-data",
      // "btcusd"),
      // Map.entry("https://www.investing.com/crypto/ethereum/historical-data",
      // "ethusd"),
      // Map.entry("https://www.investing.com/crypto/litecoin/historical-data",
      // "ltcusd"),
      // Map.entry("https://www.investing.com/crypto/bitcoin-cash/historical-data",
      // "bchusd"),
      // Map.entry("https://www.investing.com/crypto/xrp/historical-data", "xrpusd"),
      // Map.entry("https://www.investing.com/crypto/usd-coin/historical-data",
      // "usdcusd"),
      // Map.entry("https://www.investing.com/crypto/chainlink/historical-data",
      // "linkusd"),
      // Map.entry("https://www.investing.com/crypto/cardano/historical-data",
      // "adausd"),
      // Map.entry("https://www.investing.com/crypto/polkadot-new/historical-data",
      // "dotusd"),
      // Map.entry("https://www.investing.com/crypto/stellar/historical-data",
      // "xlmusd"),
      // Map.entry("https://www.investing.com/crypto/monero/historical-data",
      // "xmrusd"),
      // Map.entry("https://www.investing.com/crypto/binance-coin/historical-data",
      // "bnbusd"),
      // Map.entry("https://www.investing.com/crypto/tether/historical-data",
      // "usdtusd"),
      // Map.entry("https://www.investing.com/crypto/dash/historical-data",
      // "dashusd"),
      // Map.entry("https://www.investing.com/crypto/eos/historical-data", "eosusd"),
      // Map.entry("https://www.investing.com/crypto/bitcoin-sv/historical-data",
      // "bsvusd"),
      // Map.entry("https://www.investing.com/crypto/tron/historical-data", "trxusd")
      // Map.entry("https://www.investing.com/crypto/ethereum-classic/historical-data",
      // "etcusd")
  );

  public Investing() {
    super();
  }

  public Investing(String srcDir, String dstDir) {
    super(srcDir, dstDir);
  }

  @Override
  public void greeting() {
    this.greetingMessage("Investing.com");
  }

  @Override
  public void download() {
    this.source.forEach((page, symbol) -> {
      try {
        this.downloadOp(page, symbol);
        this.checkProcessLimit();
      } catch (Exception err) {
        this.error(err);
      }
    });

    this.downloadCompleted();
  }

  @Override
  public void rename() {

    final File[] files = new File(this.srcDir).listFiles();

    for (File f : files) {
      if (!f.isFile()) {
        continue;
      }

      for (Entry<String, String> entity : this.source.entrySet()) {
        final String page = entity.getKey();
        final String symbol = entity.getValue();

        Pattern regex;
        Matcher match;

        regex = Pattern.compile("^.+(?:indices|crypto)/(.+)$");
        match = regex.matcher(page);

        if (!match.matches()) {
          this.error(new Exception(String.format("unknown page regex: %s", page)));
          continue;
        }

        String base = match.group(1);
        base = base.toLowerCase().replaceAll("/", " ").replaceAll("-", " ").trim();

        // if (base.contains("polkadot")) {
        // base = base.replaceAll(" new", "");
        // }

        regex = Pattern.compile("^(.+?)(?:\s*-\s*Investing.com\s*)*.csv");
        match = regex.matcher(f.getName());

        if (!match.matches()) {
          // this.error(new Exception(String.format("unknown file name: %s",
          // f.getName())));
          Pretty.colorPrintln(
              Pretty.PaperPink300,
              String.format("investing.com operator skips renaming file: %s", f.getName())
          );
          continue;
        }

        String fileName = match.group(1).toLowerCase().replaceAll("-", " ").trim();

        if (fileName.equals(base)) {
          final String srcPath = Paths.get(this.srcDir, f.getName()).toString();
          // final String dstPath = Paths.get(this.dstDir, "investing.com",
          // String.format("%s.csv", symbol)).toString();
          final String dstPath = this.getDstPath(symbol);

          this.renameOp(srcPath, dstPath);
          break;
        }

      }

    }

    this.renameCompleted();
  }

  @Override
  public void check() {
    this.source.forEach((page, symbol) -> {
      // final String filePath = Paths.get(this.dstDir, "investing.com",
      // String.format("%s.csv", symbol)).toString();
      final String filePath = this.getDstPath(symbol);
      this.checkOp(filePath);

    });

    this.checkCompleted();
  }

  String getDstPath(String symbol) {
    return Paths.get(this.dstDir, "investing.com", String.format("%s.csv", symbol)).toString();
  }

}
