package neko.happy.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import neko.fun.utils.Pretty;
import neko.happy.operator.CoinAPI;
import neko.happy.operator.BarchartGeneral;
import neko.happy.operator.BarchartFutures;
import neko.happy.operator.Investing;
import neko.happy.operator.Operator;
import neko.happy.operator.Yahoo;

import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "App", description = "Download Data Source Files")
public class App implements Callable<Integer> {

  @Option(names = "--futures-start", description = "range start for futures data (yyyyMM)")
  private String futuresStart = null;

  @Option(names = "--futures-end", description = "range end for futures data (yyyyMM)")
  private String futuresEnd = null;

  @Option(names = "--futures-symbols", arity = "0..*", description = "custom symbols for futures")
  private String[] futuresSymbols = null;

  @Option(names = "--futures-daily", description = "download data from barchart futures")
  private boolean futuresDaily = false;

  @Option(names = "--futures-intraday-60", description = "download data from intraday barchart futures")
  private boolean futuresIntraday60 = false;

  @Option(names = "--futures-intraday-30", description = "download data from intraday barchart futures")
  private boolean futuresIntraday30 = false;

  @Option(names = "--futures-intraday-15", description = "download data from intraday barchart futures")
  private boolean futuresIntraday15 = false;

  @Option(names = "--barchart", description = "download data from barchart")
  private boolean barchart = false;

  @Option(names = "--yahoo", description = "download data from yahoo")
  private boolean yahoo = false;

  @Option(names = "--investing", description = "download data from investing")
  private boolean investing = false;

  @Option(names = "--crypto-symbols", arity = "0..*", description = "custom symbols for crypto")
  private String[] cryptoSymbols = null;

  @Option(names = "--crypto-start", description = "range start for crypto data (yyyyMM)")
  private String cryptoStart;

  @Option(names = "--crypto-end", description = "range end for crypto data (yyyyMM)")
  private String cryptoEnd;

  @Option(names = "--coinapi", description = "download daily crypto data from CoinAPI")
  private boolean coinapi = false;

  // @Option(names = "--crypto-intraday-60", description = "download intrady 60
  // minutes crypto data")
  // private boolean cryptoIntraday60 = false;

  // @Option(names = "--crypto-intraday-30", description = "download intraday 30
  // minutes crypto data")
  // private boolean cryptoIntraday30 = false;

  // @Option(names = "--crypto-intraday-15", description = "download intraday 15
  // minutes crypto data")
  // private boolean cryptoIntraday15 = false;

  // @Option(names = "--cryptodata", description = "download data from
  // cryptodata")
  // private boolean cryptodata = false;

  @Option(names = "--download", description = "download operation")
  private boolean download = false;

  @Option(names = "--rename", description = "rename operation")
  private boolean rename = false;

  @Option(names = "--check", description = "check files operation")
  private boolean check = false;

  @ArgGroup(exclusive = true, multiplicity = "0..1")
  BarchartPageExclusive exclusive;

  static class BarchartPageExclusive {
    @Option(names = "--historical")
    boolean historical;
    @Option(names = "--interactive")
    boolean interactive;
  }

  private final String infoColor = Pretty.PaperLightGreen200;
  private final String errorColor = Pretty.PaperRed400;

  private final String introSeparator = ", ";

  private void defaultSetting() {

    if (this.exclusive == null) {
      this.exclusive = new BarchartPageExclusive();
      this.exclusive.historical = true;
      this.exclusive.interactive = false;
    }

    if (
      !this.futuresDaily && !this.futuresIntraday60 && !this.futuresIntraday30 && !this.futuresIntraday15
          && !this.barchart && !this.yahoo && !this.investing && !this.coinapi
      // && !this.cryptoIntraday60 && !this.cryptoIntraday30 && !this.cryptoIntraday15
    ) {
      this.futuresDaily = true;
      this.futuresIntraday60 = true;
      this.futuresIntraday30 = true;
      this.futuresIntraday15 = true;
      this.barchart = true;
      this.yahoo = true;
      this.investing = true;
    }

    if (!this.download && !this.rename && !this.check) {
      this.download = true;
      this.rename = true;
    }
  }

  private void info() {

    StringBuilder builder = new StringBuilder();

    if (this.futuresDaily || this.futuresIntraday60 || this.futuresIntraday30 || this.futuresIntraday15) {
      if (this.futuresStart == null || this.futuresEnd == null) {
        Pretty.colorPanic(this.errorColor, "futures range should be specified to download futures data");
      }

      Pretty.colorPrintln(this.infoColor, String.format("Futures Range Start: %s", this.futuresStart));
      Pretty.colorPrintln(this.infoColor, String.format("Futures Range End: %s", this.futuresEnd));

      if (this.futuresSymbols != null) {
        Pretty.colorPrintln(
            this.infoColor,
            String.format("Custom Futures Symbols: %s", String.join(this.introSeparator, this.futuresSymbols))
        );
      }

      if (this.exclusive.historical) {
        Pretty.colorPrintln(this.infoColor, "Barchart Page: Historical");
      } else if (this.exclusive.interactive) {
        Pretty.colorPrintln(this.infoColor, "Barchart Page: Interactive");
      } else {
        Pretty.colorPanic(this.errorColor, "please specify the barchart page source");
      }
    }

    if (this.futuresDaily) {
      builder.append(" FuturesDaily");
    }

    if (this.futuresIntraday60) {
      builder.append(" FuturesIntraday60");
    }

    if (this.futuresIntraday30) {
      builder.append(" FuturesIntraday30");
    }

    if (this.futuresIntraday15) {
      builder.append(" FuturesIntraday15");
    }

    if (this.barchart) {
      builder.append(" Barchart");
    }

    if (this.yahoo) {
      builder.append(" Yahoo");
    }

    if (this.investing) {
      builder.append(" Investing.com");
    }

    // if (this.cryptoDaily || this.cryptoIntraday60 || cryptoIntraday30 ||
    // this.cryptoIntraday15) {
    if (this.coinapi) {
      if (this.cryptoStart == null || this.cryptoEnd == null) {
        Pretty.colorPanic(this.errorColor, "crypto range should be specified to download crypto data");
      }

      Pretty.colorPrintln(this.infoColor, String.format("Crypto Range Start: %s", this.cryptoStart));
      Pretty.colorPrintln(this.infoColor, String.format("Crypto Range End: %s", this.cryptoEnd));

      if (this.cryptoSymbols != null && this.cryptoSymbols.length != 0) {
        Pretty.colorPrintln(
            this.infoColor,
            String.format("Custom Crypto Symbols: %s", String.join(this.introSeparator, this.cryptoSymbols))
        );
      }

      builder.append(" CoinAPI");
    }

    // if (this.cryptoDaily) {
    // builder.append(" CryptoDaily");
    // }

    // if (this.cryptoIntraday60) {
    // builder.append(" CryptoIntraday60");
    // }

    // if (this.cryptoIntraday30) {
    // builder.append(" CryptoIntraday30");
    // }

    // if (this.cryptoIntraday15) {
    // builder.append(" CryptoIntraday15");
    // }

    // if (this.cryptodata) {
    // builder.append(" Cryptodata");
    // }

    Pretty.colorPrintln(this.infoColor, String.format("Source:%s", builder.toString()));

    builder = new StringBuilder();

    if (this.download) {
      builder.append(" Download");
    }

    if (this.rename) {
      builder.append(" Rename");
    }

    if (this.check) {
      builder.append(" Check");
    }

    Pretty.colorPrintln(this.infoColor, String.format("Operation:%s", builder.toString()));
  }

  @Override
  public Integer call() {
    this.defaultSetting();
    this.info();

    List<Operator> operators = new ArrayList<>();

    if (this.futuresDaily || this.futuresIntraday60 || this.futuresIntraday30 || this.futuresIntraday15) {
      if (this.futuresDaily) {
        final BarchartFutures f = new BarchartFutures().fromHistorical()
            .setCustomRange(this.futuresStart, this.futuresEnd).setCustomSymbols(this.futuresSymbols);

        if (this.exclusive.interactive) {
          f.fromInteractive();
        }

        operators.add(f);
      }

      if (this.futuresIntraday60) {
        final BarchartFutures f = new BarchartFutures().fromHistorical().intraday60Minutes()
            .setCustomRange(this.futuresStart, this.futuresEnd).setCustomSymbols(this.futuresSymbols);

        if (this.exclusive.interactive) {
          f.fromInteractive();
        }

        operators.add(f);
      }

      if (this.futuresIntraday30) {
        final BarchartFutures f = new BarchartFutures().fromHistorical().intraday30Minutes()
            .setCustomRange(this.futuresStart, this.futuresEnd).setCustomSymbols(this.futuresSymbols);

        if (this.exclusive.interactive) {
          f.fromInteractive();
        }

        operators.add(f);
      }

      if (this.futuresIntraday15) {
        final BarchartFutures f = new BarchartFutures().fromHistorical().intraday15Minutes()
            .setCustomRange(this.futuresStart, this.futuresEnd).setCustomSymbols(this.futuresSymbols);

        if (this.exclusive.interactive) {
          f.fromInteractive();
        }

        operators.add(f);
      }
    }

    if (this.barchart) {
      final BarchartGeneral b = new BarchartGeneral().fromHistorical();

      if (this.exclusive.interactive) {
        b.fromInteractive();
      }

      operators.add(b);
    }

    if (this.yahoo) {
      operators.add(new Yahoo());
    }

    if (this.investing) {
      operators.add(new Investing());
    }

    // if (this.cryptoDaily || this.cryptoIntraday60 || this.cryptoIntraday30 ||
    // this.cryptoIntraday15) {
    if (this.coinapi) {
      final CoinAPI c = new CoinAPI().setCustomRange(this.cryptoStart, this.cryptoEnd)
          .setCustomSymbols(this.cryptoSymbols);

      operators.add(c);
    }

    // if (this.cryptoIntraday60) {
    // final CoinAPI c = new
    // CoinAPI().intraday60Minutes().setCustomRange(this.cryptoStart,
    // this.cryptoEnd)
    // .setCustomSymbols(this.cryptoSymbols);

    // operators.add(c);
    // }

    // if (this.cryptoIntraday30) {
    // final CoinAPI c = new
    // CoinAPI().intraday30Minutes().setCustomRange(this.cryptoStart,
    // this.cryptoEnd)
    // .setCustomSymbols(this.cryptoSymbols);

    // operators.add(c);
    // }

    // if (this.cryptoIntraday15) {
    // final CoinAPI c = new
    // CoinAPI().intraday15Minutes().setCustomRange(this.cryptoStart,
    // this.cryptoEnd)
    // .setCustomSymbols(this.cryptoSymbols);

    // operators.add(c);
    // }
    // }

    // if (this.cryptodata) {
    // operators.add(new CryptoData());
    // }

    operators.forEach((o) -> {
      o.greeting();

      if (this.download) {
        o.download();
      }

      if (this.rename) {
        o.rename();
      }

      if (this.check) {
        o.check();
      }

    });

    return 0;
  }

  public static void main(String[] args) {
    int status = new CommandLine(new App()).execute(args);
    System.exit(status);
  }
}
