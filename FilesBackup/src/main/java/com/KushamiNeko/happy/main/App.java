package com.KushamiNeko.happy.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import com.KushamiNeko.happy.operator.CoinAPI;
import com.KushamiNeko.happy.operator.CryptoData;
import com.KushamiNeko.happy.operator.Forex;
import com.KushamiNeko.happy.operator.Futures;
import com.KushamiNeko.happy.operator.Investing;
import com.KushamiNeko.happy.operator.Operator;
import com.KushamiNeko.happy.operator.Yahoo;
import com.KushamiNeko.happy.pretty.Pretty;

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

  @Option(names = "--futures", description = "disable data from barchart futures")
  private boolean futures = false;

  @Option(names = "--barchart", description = "disable data from barchart")
  private boolean barchart = false;

  @Option(names = "--yahoo", description = "disable data from yahoo")
  private boolean yahoo = false;

  @Option(names = "--investing", description = "disable data from investing")
  private boolean investing = false;

  @Option(names = "--crypto-symbols", arity = "0..*", description = "custom symbols for crypto")
  private String[] cryptoSymbols = null;

  @Option(names = "--crypto-start", description = "range start for crypto data (yyyyMM)")
  private String cryptoStart;

  @Option(names = "--crypto-end", description = "range end for crypto data (yyyyMM)")
  private String cryptoEnd;

  @Option(names = "--coinapi", description = "disable data from coinapi")
  private boolean coinapi = false;

  @Option(names = "--cryptodata", description = "enable data from cryptodata")
  private boolean cryptodata = false;

  @Option(names = "--download", description = "disable download operation")
  private boolean download = false;

  @Option(names = "--rename", description = "disable rename operation")
  private boolean rename = false;

  @Option(names = "--check", description = "enable check files")
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
    if (!this.futures && !this.barchart && !this.yahoo && !this.investing && !this.coinapi) {
      this.futures = true;
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

    if (this.exclusive == null) {
      this.exclusive = new BarchartPageExclusive();
      this.exclusive.historical = true;
      this.exclusive.interactive = false;
    }

    StringBuilder builder = new StringBuilder();

    if (this.futures) {
      if (this.futuresStart == null || this.futuresEnd == null) {
        Pretty.colorPrintln(this.errorColor, "futures range should be specified to download futures data");
        System.exit(1);
      }

      Pretty.colorPrintln(this.infoColor, String.format("Futures Range Start: %s", this.futuresStart));
      Pretty.colorPrintln(this.infoColor, String.format("Futures Range End: %s", this.futuresEnd));

      builder.append(" Futures");

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
      }
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

    if (this.coinapi) {
      if (this.cryptoStart == null || this.cryptoEnd == null) {
        Pretty.colorPrintln(this.errorColor, "crypto range should be specified to download crypto data");
        System.exit(1);
      }

      Pretty.colorPrintln(this.infoColor, String.format("Crypto Range Start: %s", this.cryptoStart));
      Pretty.colorPrintln(this.infoColor, String.format("Crypto Range End: %s", this.cryptoEnd));

      if (this.cryptoSymbols != null && this.cryptoSymbols.length != 0) {
        Pretty.colorPrintln(
            this.infoColor,
            String.format("custom crypto symbols: %s", String.join(this.introSeparator, this.cryptoSymbols))
        );
      }

      builder.append(" CoinAPI");
    }

    if (this.cryptodata) {
      builder.append(" Cryptodata");
    }

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

    if (this.futures) {
      try {
        final Futures f = new Futures();
        f.setCustomRange(this.futuresStart, this.futuresEnd);
        if (this.futuresSymbols != null) {
          f.setCustomSymbols(Arrays.asList(this.futuresSymbols));
        }

        operators.add(f);

      } catch (IllegalArgumentException err) {
        Pretty.colorPrintln(this.errorColor, err.getMessage());
        System.exit(1);
      }

    }

    if (this.barchart) {
      operators.add(new Forex());
    }

    if (this.yahoo) {
      operators.add(new Yahoo());
    }

    if (this.investing) {
      operators.add(new Investing());
    }

    if (this.coinapi) {
      final CoinAPI c = new CoinAPI();
      c.setCustomRange(this.cryptoStart, this.cryptoEnd);
      if (this.cryptoSymbols != null) {
        c.setCustomSymbols(Arrays.asList(this.cryptoSymbols));
      }

      operators.add(c);
    }

    if (this.cryptodata) {
      operators.add(new CryptoData());
    }

    operators.forEach((o) -> {

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
    int exitCode = new CommandLine(new App()).execute(args);
    System.exit(exitCode);
  }
}
