package trading;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MonthlyStatementTest {

  private static class ExpectedResult {
    String objective;
    List<String> accounts;
    double endingBalance;
    double percentageRisk;
    List<TradeRecord> records;

    public ExpectedResult(
        String objective,
        List<String> accounts,
        double endingBalance,
        double percentageRisk,
        List<TradeRecord> records
    ) {
      this.objective = objective;
      this.accounts = accounts;
      this.endingBalance = endingBalance;
      this.percentageRisk = percentageRisk;
      this.records = records;
    }

  }

  private static Stream<Arguments> statementProvider() {
    return Stream.of(
        Arguments.of(
            """
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
            """,
            new ExpectedResult(
                "trading",
                List.of("futures", "equity", "crypto"),
                35100.0,
                0.5,
                List.of(
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 3, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 3, 0, 0, 0),
                        TradeDirection.SHORT,
                        100.5
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 8, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 8, 0, 0, 0),
                        TradeDirection.SHORT,
                        120.75
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 9, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 9, 0, 0, 0),
                        TradeDirection.SHORT,
                        -21.25
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 16, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 16, 0, 0, 0),
                        TradeDirection.LONG,
                        150.5
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 31, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 31, 0, 0, 0),
                        TradeDirection.LONG,
                        -50.0
                    )
                )
            ),
            null
        ),
        Arguments.of(
            """
            objective: trading

            futures balance: 20,000

            account percentage risk per trade: 2.5%

            2021-03-03 ~ 2021-03-04:  100.50  @  S
            2021-03-08 ~ 2021-03-09:  120.75  @  S
            2021-03-09 ~ 2021-03-12:  -21.25  @  S
            2021-03-16~2021-03-17:  150.50  @  L1
            2021-03-31-2021-04-02:  -50  @  L1
            """,
            new ExpectedResult(
                "trading",
                List.of("futures"),
                20000.0,
                2.5,
                List.of(
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 3, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 4, 0, 0, 0),
                        TradeDirection.SHORT,
                        100.5
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 8, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 9, 0, 0, 0),
                        TradeDirection.SHORT,
                        120.75
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 9, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 12, 0, 0, 0),
                        TradeDirection.SHORT,
                        -21.25
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 16, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 17, 0, 0, 0),
                        TradeDirection.LONG,
                        150.5
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 31, 0, 0, 0),
                        LocalDateTime.of(2021, 4, 2, 0, 0, 0),
                        TradeDirection.LONG,
                        -50.0
                    )
                )
            ),
            null
        ),
        Arguments.of(
            """
            objective: trading

            futures balance: 20,000

            account percentage risk per trade: 2.5%

            2021-03-03 05:00 ~ 2021-03-04 07:30:  100.50  @  S
            2021-03-08 13:30 ~ 2021-03-09 15:30:  120.75  @  S
            2021-03-09 00:00 ~ 2021-03-12 02:30:  -21.25  @  S
            2021-03-16 23:30~2021-03-17 00:30:  150.50  @  L1
            2021-03-31 11:30-2021-04-02 14:30:  -50  @  L1
            """,
            new ExpectedResult(
                "trading",
                List.of("futures"),
                20000.0,
                2.5,
                List.of(
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 3, 5, 0, 0),
                        LocalDateTime.of(2021, 3, 4, 7, 30, 0),
                        TradeDirection.SHORT,
                        100.5
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 8, 13, 30, 0),
                        LocalDateTime.of(2021, 3, 9, 15, 30, 0),
                        TradeDirection.SHORT,
                        120.75
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 9, 0, 0, 0),
                        LocalDateTime.of(2021, 3, 12, 2, 30, 0),
                        TradeDirection.SHORT,
                        -21.25
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 16, 23, 30, 0),
                        LocalDateTime.of(2021, 3, 17, 0, 30, 0),
                        TradeDirection.LONG,
                        150.5
                    ),
                    new TradeRecord(
                        LocalDateTime.of(2021, 3, 31, 11, 30, 0),
                        LocalDateTime.of(2021, 4, 2, 14, 30, 0),
                        TradeDirection.LONG,
                        -50.0
                    )
                )
            ),
            null
        ),
        Arguments.of("""
                     futures balance: 20,000
                     equity balance: 15,000
                     crypto balance: 100

                     account percentage risk per trade: 0.5%

                     2021-03-03:  100.50  @  S
                     2021-03-08:  120.75  @  S
                     2021-03-09:  -21.25  @  S
                     2021-03-16:  150.50  @  L1
                     2021-03-31:  -50  @  L1
                     """, null, new IllegalArgumentException()),
        Arguments.of("""
                     objective: trading

                     account percentage risk per trade: 0.5%

                     2021-03-03:  100.50  @  S
                     2021-03-08:  120.75  @  S
                     2021-03-09:  -21.25  @  S
                     2021-03-16:  150.50  @  L1
                     2021-03-31:  -50  @  L1
                     """, null, new IllegalArgumentException()),
        Arguments.of("""
                     objective: trading

                     futures balance: 20,000
                     equity balance: 15,000
                     crypto balance: 100

                     2021-03-03:  100.50  @  S
                     2021-03-08:  120.75  @  S
                     2021-03-09:  -21.25  @  S
                     2021-03-16:  150.50  @  L1
                     2021-03-31:  -50  @  L1
                     """, null, new IllegalArgumentException()),
        Arguments.of("""
                     objective: trading

                     futures balance: 20,000
                     equity balance: 15,000
                     crypto balance: 100

                     account percentage risk per trade: 0.5%
                     """, null, new IllegalArgumentException())

    );
  }

  @ParameterizedTest
  @MethodSource("statementProvider")
  public void testMonthlyStatementParser(String statement, ExpectedResult result, Exception err) {
    if (err == null) {

      final MonthlyStatement ms = new MonthlyStatement(statement);

      Assertions.assertEquals(ms.getObjective(), result.objective);

      Assertions.assertArrayEquals(
          ms.getAccounts().stream().map((element) -> element.getAccountType()).toArray(),
          result.accounts.toArray()
      );

      Assertions.assertEquals(ms.getEndingBalance(), result.endingBalance);

      Assertions.assertEquals(ms.getAcountPercentageRisk(), result.percentageRisk);

      Assertions.assertArrayEquals(ms.getTradingRecords().toArray(), result.records.toArray());

    } else {
      Assertions.assertThrows(err.getClass(), () -> {
        new MonthlyStatement(statement);
      });
    }

  }
}
