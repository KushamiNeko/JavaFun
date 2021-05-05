package trading;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class TradingStatistic {

  private Stream<TradeRecord> allTrades, winningTrades, lossingTrades;
  private Stream<TradeRecord> longTrades, longWinningTrades, longLossingTrades;
  private Stream<TradeRecord> shortTrades, shortWinningTrades, shortLossingTrades;

  private double battingAvg, battingAvgL, battingAvgS;

  private double winningPLAvg, winningPLAvgL, winningPLAvgS;
  private double winningPLMax, winningPLMaxL, winningPLMaxS;
  private double winningPLMin, winningPLMinL, winningPLMinS;

  private double lossingPLAvg, lossingPLAvgL, lossingPLAvgS;
  private double lossingPLMax, lossingPLMaxL, lossingPLMaxS;
  private double lossingPLMin, lossingPLMinL, lossingPLMinS;

  private double winLossRatio, winLossRatioL, winLossRatioS;

  private double winningHoldAvg, winningHoldAvgL, winningHoldAvgS;
  // private double winningHoldMax, winningHoldMaxL, winningHoldMaxS;
  // private double winningHoldMin, winningHoldMinL, winningHoldMinS;

  private double lossingHoldAvg, lossingHoldAvgL, lossingHoldAvgS;
  // private double lossingHoldMax, lossingHoldMaxL, lossingHoldMaxS;
  // private double lossingHoldMin, lossingHoldMinL, lossingHoldMinS;

  private double expectedValue, expectedValueL, expectedValueS;

  public TradingStatistic(List<TradeRecord> records) {

    // this.allTrades = statements.stream().flatMap((statement) -> statement.getTradingRecords().stream());
    this.allTrades = records.stream();

    this.winningTrades = this.allTrades.filter((trade) -> trade.getTradePL() > 0.0);
    this.lossingTrades = this.allTrades.filter((trade) -> trade.getTradePL() < 0.0);

    this.longTrades = this.allTrades.filter((trade) -> trade.getTradeDirection() == TradeDirection.LONG);

    this.longWinningTrades = this.allTrades
        .filter((trade) -> trade.getTradeDirection() == TradeDirection.LONG && trade.getTradePL() > 0.0);

    this.longLossingTrades = this.allTrades
        .filter((trade) -> trade.getTradeDirection() == TradeDirection.LONG && trade.getTradePL() < 0.0);

    this.shortTrades = this.allTrades.filter((trade) -> trade.getTradeDirection() == TradeDirection.SHORT);

    this.shortWinningTrades = this.allTrades
        .filter((trade) -> trade.getTradeDirection() == TradeDirection.SHORT && trade.getTradePL() > 0.0);

    this.shortLossingTrades = this.allTrades
        .filter((trade) -> trade.getTradeDirection() == TradeDirection.SHORT && trade.getTradePL() < 0.0);

  }

  private void calculate() {

    this.battingAvg = (double) this.winningTrades.count() / (double) this.allTrades.count();
    this.battingAvgL = (double) this.longWinningTrades.count() / (double) this.longTrades.count();
    this.battingAvgS = (double) this.shortWinningTrades.count() / (double) this.shortTrades.count();

    this.winningPLAvg = this.winningTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::sum)
        / (double) this.winningTrades.count();

    this.winningPLAvgL = this.longWinningTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::sum)
        / (double) this.longWinningTrades.count();

    this.winningPLAvgS = this.shortWinningTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::sum)
        / (double) this.shortWinningTrades.count();

    this.winningPLMax = this.winningTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::max);
    this.winningPLMaxL = this.longWinningTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::max);
    this.winningPLMaxS = this.shortWinningTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::max);

    this.winningPLMin = this.winningTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::min);
    this.winningPLMinL = this.longWinningTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::min);
    this.winningPLMinS = this.shortWinningTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::min);

    this.lossingPLAvg = this.lossingTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::sum)
        / (double) this.lossingTrades.count();

    this.lossingPLAvgL = this.longLossingTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::sum)
        / (double) this.longLossingTrades.count();

    this.lossingPLAvgS = this.shortLossingTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::sum)
        / (double) this.shortLossingTrades.count();

    this.lossingPLMax = this.lossingTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::min);
    this.lossingPLMaxL = this.longLossingTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::min);
    this.lossingPLMaxS = this.shortLossingTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::min);

    this.lossingPLMin = this.lossingTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::max);
    this.lossingPLMinL = this.longLossingTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::max);
    this.lossingPLMinS = this.shortLossingTrades.map((trade) -> trade.getTradePL()).reduce(0.0, Double::max);

    this.winLossRatio = this.winningPLAvg / Math.abs(this.lossingPLAvg);
    this.winLossRatioL = this.winningPLAvgL / Math.abs(this.lossingPLAvgL);
    this.winLossRatioS = this.winningPLAvgS / Math.abs(this.lossingPLAvgS);

    this.winningHoldAvg = (double) this.winningTrades
        .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
        .reduce((long) 0, Long::sum) / (double) this.winningTrades.count();

    this.winningHoldAvgL = (double) this.longWinningTrades
        .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
        .reduce((long) 0, Long::sum) / (double) this.longWinningTrades.count();

    this.winningHoldAvgS = (double) this.shortWinningTrades
        .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
        .reduce((long) 0, Long::sum) / (double) this.shortWinningTrades.count();

    // this.winningHoldMax = (double) this.winningTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::max);

    // this.winningHoldMaxL = (double) this.longWinningTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::max);

    // this.winningHoldMaxS = (double) this.shortWinningTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::max);

    // this.winningHoldMin = (double) this.winningTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::min);

    // this.winningHoldMinL = (double) this.longWinningTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::min);

    // this.winningHoldMinS = (double) this.shortWinningTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::min);

    this.lossingHoldAvg = (double) this.lossingTrades
        .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
        .reduce((long) 0, Long::sum) / (double) this.lossingTrades.count();

    this.lossingHoldAvgL = (double) this.longLossingTrades
        .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
        .reduce((long) 0, Long::sum) / (double) this.longLossingTrades.count();

    this.lossingHoldAvgS = (double) this.shortLossingTrades
        .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
        .reduce((long) 0, Long::sum) / (double) this.shortLossingTrades.count();

    // this.lossingHoldMax = (double) this.lossingTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::max);

    // this.lossingHoldMaxL = (double) this.longLossingTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::max);

    // this.lossingHoldMaxS = (double) this.shortLossingTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::max);

    // this.lossingHoldMin = (double) this.lossingTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::min);

    // this.lossingHoldMinL = (double) this.longLossingTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::min);

    // this.lossingHoldMinS = (double) this.shortLossingTrades
    //     .map((trade) -> Duration.between(trade.getTradeClose(), trade.getTradeOpen()).toMinutes())
    //     .reduce((long) 0, Long::min);

    this.expectedValue = (this.winningPLAvg * this.battingAvg) + (this.lossingPLAvg * (1.0 - this.battingAvg));
    this.expectedValueL = (this.winningPLAvgL * this.battingAvgL) + (this.lossingPLAvgL * (1.0 - this.battingAvgL));
    this.expectedValueS = (this.winningPLAvgS * this.battingAvgS) + (this.lossingPLAvgS * (1.0 - this.battingAvgS));
  }

  public String toString() {
    this.calculate();

    final int fieldLength = 18;

    // final String fieldLayout = "%- 18s";
    final String fieldLayout = "%- " + fieldLength + "s";
    final String floatLayout = "%.3f";

    final int nFields = 12;
    final String formatRow = String.join(" | ", Collections.nCopies(nFields, fieldLayout)) + "\n";
    final String separator = String.join(" | ", Collections.nCopies(nFields, "-".repeat(fieldLength))) + "\n";

    StringBuilder builder = new StringBuilder();

    builder.append(
        String.format(
            formatRow,
            "",
            "Batting Avg. (%)",
            "Win PL Avg. ($|¥)",
            "Win PL Max ($|¥)",
            "Win PL Min ($|¥)",
            "Loss PL Avg. ($|¥)",
            "Loss PL Max ($|¥)",
            "Loss PL Min ($|¥)",
            "Win Loss Ratio",
            "Win Hold Avg. (H/D)",
            "Loss Hold Avg. (H/D)",
            "EV. ($|¥)"
        )
    );

    builder.append(separator);

    builder.append(
        String.format(
            formatRow,
            "ALL",
            String.format(floatLayout, this.battingAvg * 100.0),
            String.format(floatLayout, this.winningPLAvg),
            String.format(floatLayout, this.winningPLMax),
            String.format(floatLayout, this.winningPLMin),
            String.format(floatLayout, this.lossingPLAvg),
            String.format(floatLayout, this.lossingPLMax),
            String.format(floatLayout, this.lossingPLMin),
            String.format(floatLayout, this.winLossRatio),
            new StringBuilder().append(String.format(floatLayout, this.winningHoldAvg / 60.0)).append("/")
                .append(String.format(floatLayout, (this.winningHoldAvg / 60.0) / 24.0)).toString(),
            new StringBuilder().append(String.format(floatLayout, this.lossingHoldAvg / 60.0)).append("/")
                .append(String.format(floatLayout, (this.lossingHoldAvg / 60.0) / 24.0)).toString(),
            String.format(floatLayout, this.expectedValue)
        )
    );

    builder.append(separator);

    builder.append(
        String.format(
            formatRow,
            "LONG",
            String.format(floatLayout, this.battingAvgL * 100.0),
            String.format(floatLayout, this.winningPLAvgL),
            String.format(floatLayout, this.winningPLMaxL),
            String.format(floatLayout, this.winningPLMinL),
            String.format(floatLayout, this.lossingPLAvgL),
            String.format(floatLayout, this.lossingPLMaxL),
            String.format(floatLayout, this.lossingPLMinL),
            String.format(floatLayout, this.winLossRatioL),
            new StringBuilder().append(String.format(floatLayout, this.winningHoldAvgL / 60.0)).append("/")
                .append(String.format(floatLayout, (this.winningHoldAvgL / 60.0) / 24.0)).toString(),
            new StringBuilder().append(String.format(floatLayout, this.lossingHoldAvgL / 60.0)).append("/")
                .append(String.format(floatLayout, (this.lossingHoldAvgL / 60.0) / 24.0)).toString(),
            String.format(floatLayout, this.expectedValueL)
        )
    );

    builder.append(separator);

    builder.append(
        String.format(
            formatRow,
            "SHORT",
            String.format(floatLayout, this.battingAvgS * 100.0),
            String.format(floatLayout, this.winningPLAvgS),
            String.format(floatLayout, this.winningPLMaxS),
            String.format(floatLayout, this.winningPLMinS),
            String.format(floatLayout, this.lossingPLAvgS),
            String.format(floatLayout, this.lossingPLMaxS),
            String.format(floatLayout, this.lossingPLMinS),
            String.format(floatLayout, this.winLossRatioS),
            new StringBuilder().append(String.format(floatLayout, this.winningHoldAvgS / 60.0)).append("/")
                .append(String.format(floatLayout, (this.winningHoldAvgS / 60.0) / 24.0)).toString(),
            new StringBuilder().append(String.format(floatLayout, this.lossingHoldAvgS / 60.0)).append("/")
                .append(String.format(floatLayout, (this.lossingHoldAvgS / 60.0) / 24.0)).toString(),
            String.format(floatLayout, this.expectedValueS)
        )
    );

    return builder.toString();

  }
}
