package trading;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonthlyStatement {

  // the objective of the statement
  // eg. trading or investing
  private String objective;

  /* 
      list all accounts used for specified objective
      eg. 
      objective: trading 
      balance: 
          1 futures account for main trading 
          1 equities account for holding money market ETF 
          1 crypto account for earning crypto interests 
  */
  private List<AccountBalance> accounts;

  // account percentage risk per trade
  private double percentageRisk;

  // trading pl records
  private List<TradeRecord> records;

  public MonthlyStatement(String statement) {
    this.parseObjective(statement);
    this.parseAccountBalance(statement);
    this.parseTradeRecords(statement);
  }

  public String getObjective() {
    return this.objective;
  }

  public List<AccountBalance> getAccounts() {
    return this.accounts;
  }

  public Double getEndingBalance() {
    return this.accounts.stream().map((element) -> element.getEndingBalance()).reduce(0.0, Double::sum);
  }

  public double getAcountPercentageRisk() {
    return this.percentageRisk;
  }

  public List<TradeRecord> getTradingRecords() {
    return this.records;
  }

  private void parseObjective(String statement) {
    final String pattern = "^\\s*objective:\\s*(\\w+)\\s*$";

    final Pattern regex = Pattern.compile(pattern, Pattern.MULTILINE);
    final Matcher match = regex.matcher(statement);

    if (!match.find()) {
      throw new IllegalArgumentException("missing statement objective");
    } else {
      this.objective = match.group(1).trim();
    }
  }

  private void parseAccountBalance(String statement) {
    final String pattern = "^\\s*(\\w+)\\s*balance:\\s*([0-9,.-]+)\\s*$";

    final Pattern regex = Pattern.compile(pattern, Pattern.MULTILINE);
    final Matcher match = regex.matcher(statement);

    final List<AccountBalance> accounts = new ArrayList<>();

    while (match.find()) {
      final String account = match.group(1).trim();
      final double balance = Double.parseDouble(match.group(2).replaceAll(",", ""));

      accounts.add(new AccountBalance(account, balance));
    }

    if (accounts.isEmpty()) {
      throw new IllegalArgumentException("missing balance records");
    }

    this.accounts = accounts;
  }

  private void parseTradeRecords(String statement) {
    final String datetimeInputPattern = "(\\d{4}[-]\\d{2}[-]\\d{2}(?:[\\s]\\d{2}:\\d{2})*)";

    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    String pattern;
    Pattern regex;
    Matcher match;

    pattern = "^\\s*account percentage risk per trade\\s*:\\s*([0-9.]+)\\s*%\\s*$";
    regex = Pattern.compile(pattern, Pattern.MULTILINE);

    match = regex.matcher(statement);

    if (!match.find()) {
      throw new IllegalArgumentException("missing trade records");
    }

    this.percentageRisk = Double.parseDouble(match.group(1));

    pattern = String.format(
        "^\\s*%s(?:\\s*[-~]\\s*%s)*\\s*:\\s*([0-9,.-]+)\\s*(?:@\\s*([LS])(\\d*))$",
        datetimeInputPattern,
        datetimeInputPattern
    );

    regex = Pattern.compile(pattern, Pattern.MULTILINE);
    match = regex.matcher(statement);

    final List<TradeRecord> trades = new ArrayList<>();

    while (match.find()) {

      final LocalDateTime tradeOpen, tradeClose;

      if (match.group(1).length() > 10) {
        tradeOpen = LocalDateTime.parse(match.group(1), dateTimeFormatter);
      } else {
        tradeOpen = LocalDate.parse(match.group(1), dateFormatter).atStartOfDay();
      }

      if (match.group(2) != null) {
        if (match.group(2).length() > 10) {
          tradeClose = LocalDateTime.parse(match.group(2), dateTimeFormatter);
        } else {
          tradeClose = LocalDate.parse(match.group(2), dateFormatter).atStartOfDay();
        }

        if (tradeClose.isBefore(tradeOpen)) {
          throw new IllegalArgumentException("trade close should be after trade open");
        }

      } else {
        tradeClose = tradeOpen;
      }

      final TradeDirection direction = switch (match.group(4)) {
        case "L" -> TradeDirection.LONG;
        case "S" -> TradeDirection.SHORT;
        default -> throw new IllegalArgumentException("unknown trade direction");
      };

      final double pl = Double.parseDouble(match.group(3).replaceAll(",", ""));

      trades.add(new TradeRecord(tradeOpen, tradeClose, direction, pl));
    }

    if (trades.isEmpty()) {
      throw new IllegalArgumentException("missing trade records");
    }

    this.records = trades;
  }
}
