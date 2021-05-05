package trading;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TradingStatisticTest {

  private static class Expected {

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

    public Expected(

    ) {

    }

  }

  private static Stream<Arguments> statementProvider() {
    return Stream.of(Arguments.of("""
                                  objective: trading

                                  futures balance: 20,000
                                  equity balance: 15,000
                                  crypto balance: 100

                                  account percentage risk per trade: 0.5%

                                  2021-03-03:  100.50  @  S
                                  2021-03-08:  120.75  @  S
                                  2021-03-09:  -21.25  @  S
                                  2021-03-16:  150.50  @  L1
                                  2021-03-31:  -50  @  L1
                                  """, new Expected(

    ), null));
  }

  @ParameterizedTest
  @MethodSource("statementProvider")
  public void testMonthlyStatementParser(String statement, Expected expected, Exception err) {
    if (err == null) {

      final MonthlyStatement ms = new MonthlyStatement(statement);

      // Assertions.assertEquals(ms.getObjective(), expected.objective);

      // Assertions.assertArrayEquals(
      //     ms.getAccounts().stream().map((element) -> element.getAccountType()).toArray(),
      //     expected.accounts.toArray()
      // );

      // Assertions.assertEquals(ms.getEndingBalance(), expected.endingBalance);

      // Assertions.assertEquals(ms.getAcountPercentageRisk(), expected.percentageRisk);

      // Assertions.assertArrayEquals(ms.getTradingRecords().toArray(), expected.records.toArray());

    } else {
      Assertions.assertThrows(err.getClass(), () -> {
        new MonthlyStatement(statement);
      });
    }

  }
}
