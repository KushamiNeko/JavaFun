package neko.fun.futures;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MonthCodeTest {

  private static Stream<Arguments> monthCodeProvider() {
    return Stream.of(
        Arguments.of(MonthCode.JANUARY, "f", 1),
        Arguments.of(MonthCode.FEBRUARY, "g", 2),
        Arguments.of(MonthCode.MARCH, "h", 3),
        Arguments.of(MonthCode.APRIL, "j", 4),
        Arguments.of(MonthCode.MAY, "k", 5),
        Arguments.of(MonthCode.JUNE, "m", 6),
        Arguments.of(MonthCode.JULY, "n", 7),
        Arguments.of(MonthCode.AUGUST, "q", 8),
        Arguments.of(MonthCode.SEPTEMBER, "u", 9),
        Arguments.of(MonthCode.OCTOBER, "v", 10),
        Arguments.of(MonthCode.NOVEMBER, "x", 11),
        Arguments.of(MonthCode.DECEMBER, "z", 12)
    );
  }

  @ParameterizedTest
  @MethodSource("monthCodeProvider")
  public void testCodeAndMonth(MonthCode f, String monthCode, int month) {
    Assertions.assertEquals(f.getCode(), monthCode);
    Assertions.assertEquals(f.getMonthValue(), month);
  }

  @ParameterizedTest
  @MethodSource("monthCodeProvider")
  public void testStaticFactory(MonthCode f, String monthCode, int month) {

    MonthCode fc;

    fc = MonthCode.fromCode(monthCode);

    Assertions.assertEquals(fc, f);
    Assertions.assertEquals(fc.getCode(), monthCode);
    Assertions.assertEquals(fc.getMonthValue(), month);

    fc = MonthCode.fromMonthValue(month);

    Assertions.assertEquals(fc, f);
    Assertions.assertEquals(fc.getCode(), monthCode);
    Assertions.assertEquals(fc.getMonthValue(), month);
  }
}
