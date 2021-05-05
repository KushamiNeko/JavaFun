package neko.happy.operator;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import neko.fun.futures.ContractMonths;
import neko.fun.futures.MonthCode;
import neko.fun.utils.Validator;

public class CoinAPI extends Operator {

  private final String apiKey = System.getenv("COIN_API");

  private final String baseUrl = "https://rest.coinapi.io/v1/ohlcv/%s/%s/history?period_id=%s";
  // private final String baseUrl =
  // "https://rest.coinapi.io/v1/ohlcv/KRAKEN_SPOT_%s_%s/history?period_id=%s";

  private List<String> source = List.of(
      //
      "BTCUSD",
      "ETHUSD",
      "LTCUSD",
      "BCHUSD",
      "XRPUSD",
      "USDCUSD"
  /*
   * "DOTUSD", "LINKUSD", "EOSUSD", "ADAUSD", "XLMUSD", "TRXUSD", "UNIUSD"
   */
  // "BNBUSD",
  // "DOGEUSD",
  );

  // private List<String> source;

  // private CryptoWorker worker = new CryptoworkerDaily();

  private final ContractMonths months = ContractMonths.crypto();

  private String period = "1DAY";

  private String startDate = null;
  private String endDate = null;

  public CoinAPI() {
    super();
    // this.source = this.worker.getSource();
  }

  public CoinAPI(String srcDir, String dstDir) {
    super(srcDir, dstDir);
    // this.source = this.worker.getSource();
  }

  // public CoinAPI daily() {
  // this.period = "1DAY";
  // this.worker = new CryptoworkerDaily();
  // this.source = this.worker.getSource();
  // return this;
  // }

  // public CoinAPI intraday60Minutes() {
  // this.period = "1HRS";
  // this.worker = new CryptoWorker60Minutes();
  // this.source = this.worker.getSource();
  // return this;
  // }

  // public CoinAPI intraday30Minutes() {
  // this.period = "30MIN";
  // this.worker = new CryptoWorker30Minutes();
  // this.source = this.worker.getSource();
  // return this;
  // }

  // public CoinAPI intraday15Minutes() {
  // this.period = "15MIN";
  // this.worker = new CryptoWorker15Minutes();
  // this.source = this.worker.getSource();
  // return this;
  // }

  public CoinAPI setCustomRange(String startDate, String endDate) {
    if (startDate != null && endDate != null) {
      final String pattern = "^\\d{6}$";

      new Validator().critical().validateStringInputWithRegex(pattern, startDate);
      this.startDate = startDate;

      new Validator().critical().validateStringInputWithRegex(pattern, endDate);
      this.endDate = endDate;

    } else {
      throw new IllegalArgumentException("range start and range end should not be null");
    }

    return this;
  }

  // public CoinAPI setCustomSymbols(List<String> symbols) {
  public CoinAPI setCustomSymbols(String[] symbols) {
    if (symbols != null) {
      // this.source = symbols;
      this.source = Arrays.asList(symbols);
    }
    return this;
  }

  private String nextContract(String datetime) {
    final Pattern regex = Pattern.compile("^(\\d{4})(\\d{2})$");
    final Matcher match = regex.matcher(datetime);
    if (!match.find()) {
      throw new IllegalArgumentException(String.format("invalid year month input: %s", datetime));
    }

    final int year = Integer.parseInt(match.group(1));
    final int month = Integer.parseInt(match.group(2));

    final int idt = (year * 100) + month;

    for (int i = year; i <= year + 1; i++) {
      for (final char c : this.months.getMonths().toCharArray()) {
        final MonthCode mc = MonthCode.fromCode(String.valueOf(c));
        final int ic = (i * 100) + mc.getMonthValue();

        if (ic > idt) {
          return String.valueOf(ic);
        }
      }
    }

    throw new AssertionError(String.format("fail to find valid contract: %s", datetime));
  }

  private void process(BiConsumer<String, String> fun) {
    this.source.forEach((String symbol) -> {

      final int iStart = Integer.parseInt(this.startDate);
      final int iEnd = Integer.parseInt(this.endDate);

      final int startYear = iStart / 100;
      final int endYear = iEnd / 100;

      for (int i = startYear; i <= endYear; i++) {
        final int y = i;
        this.months.forEach((MonthCode mc) -> {

          final int ic = (y * 100) + mc.getMonthValue();

          if (ic >= iStart && ic <= iEnd) {
            final String start = String.valueOf(ic);
            fun.accept(symbol, start);
          }
        });
      }
    });

  }

  private String getURL(String symbol, String startDate) {

    final String endDate = this.nextContract(startDate);

    final String startTime = "00:00:00";
    final String endTime = "00:00:00";

    // final String period = "1DAY";

    final String start = String.format("%s-%s-01", startDate.substring(0, 4), startDate.substring(4, 6));
    final String end = String.format("%s-%s-01", endDate.substring(0, 4), endDate.substring(4, 6));

    final Pattern regex = Pattern.compile("^(\\w+?)(\\w{3})$");
    final Matcher match = regex.matcher(symbol);

    if (!match.find()) {
      throw new IllegalArgumentException(String.format("invalid symbol: %s", symbol));
    }

    final String crypto = match.group(1);
    final String base = match.group(2);

    final StringBuilder builder = new StringBuilder(
        String.format(this.baseUrl, crypto.toUpperCase(), base.toUpperCase(), this.period.toUpperCase())
    );

    builder.append(String.format("&time_start=%sT%s", start, startTime));
    builder.append(String.format("&time_end=%sT%s", end, endTime));
    // builder.append("&limit=100000");

    return builder.toString();
  }

  @Override
  public void greeting() {
    // this.greetingMessage(this.worker.getName());
    this.greetingMessage("CoinAPI");
  }

  @Override
  public void download() {

    this.process((String symbol, String start) -> {
      final File f = new File(Paths.get(this.dstDir, "coinapi", symbol.toLowerCase()).toString());
      if (!f.exists()) {
        throw new IllegalArgumentException(String.format("path does not exist: %s", f.getName()));
      }

      final String url = this.getURL(symbol, start);

      // final Path filePath = this.worker.getDstPath(this.dstDir, symbol, start);
      final Path filePath = this.getDstPath(symbol, start);

      final HttpClient client = HttpClient.newHttpClient();
      final HttpRequest request = HttpRequest.newBuilder(URI.create(url)).header("X-CoinAPI-Key", this.apiKey).build();

      try {
        final HttpResponse<Path> response = client.send(request, BodyHandlers.ofFile(filePath));

        final StringBuilder builder = new StringBuilder("\n");

        builder.append(String.format("source: %s", url));
        builder.append("\n");
        builder.append(String.format("destination: %s", response.body().toString()));
        // builder.append(String.format("destination: %s", filePath.toString()));

        this.downloadMessage(builder.toString());
        this.downloadCount += 1;
        this.checkProcessLimit();

      } catch (InterruptedException | IOException err) {
        this.error(err);
      }
    });

    this.downloadCompleted();
  }

  @Override
  public void rename() {
    this.renameCount = this.downloadCount;
    this.renameCompleted();
  }

  @Override
  public void check() {
    this.process((String symbol, String start) -> {
      this.checkOp(this.getDstPath(symbol, start).toString());
      // this.checkOp(this.worker.getDstPath(this.dstDir, symbol, start).toString());
    });

    this.checkCompleted();
  }

  Path getDstPath(String symbol, String start) {
    return Paths
        .get(this.dstDir, "coinapi", symbol.toLowerCase(), String.format("%s@%s.json", symbol.toLowerCase(), start));
  }

}
