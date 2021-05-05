package com.KushamiNeko.happy.pretty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
  public static void validateInput(String input, String pattern) {
    final Pattern regex = Pattern.compile(pattern);
    final Matcher matcher = regex.matcher(input);

    if (!matcher.find()) {
      throw new IllegalArgumentException(String.format("invalid input '%s' for pattern '%s'", input, pattern));
    }
  }
}
