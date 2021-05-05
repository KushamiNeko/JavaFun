package com.KushamiNeko.happy.pretty;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PrettyTest {

  private static Stream<Arguments> checkHexColorProvider() {
    return Stream.of(
        Arguments.of("aaa", false),
        Arguments.of("#aaa", false),
        Arguments.of("  ", false),
        Arguments.of(" aaaaaa", false),
        Arguments.of("gggggg", false),
        Arguments.of("#aaccbb", true),
        Arguments.of("000000", true),
        Arguments.of("#000000", true),
        Arguments.of("ffffff", true),
        Arguments.of("#ffffff", true),
        Arguments.of("#ff00ff", true),
        Arguments.of("#ffffff ", false),
        Arguments.of(" #ffffff", false),
        Arguments.of("00000", false),
        Arguments.of(" 000000", false),
        Arguments.of("#0000000", false),
        Arguments.of("0000000", false),
        Arguments.of("-1-1-1", false),
        Arguments.of("gggggg", false),
        Arguments.of("ffff0", false),
        Arguments.of("#ffff0", false),
        Arguments.of("@000000", false)
    );
  }

  @ParameterizedTest
  @MethodSource("checkHexColorProvider")
  public void testCheckHexColor(String input, boolean ok) {

    if (ok) {
      Pretty.checkHexColor(input);
    } else {
      Assertions.assertThrows(IllegalArgumentException.class, () -> {
        Pretty.checkHexColor(input);
      });
    }
  }

}
