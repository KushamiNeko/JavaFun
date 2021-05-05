package neko.fun.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

  private boolean critical = false;

  // public static Validator withException() {
  //   return new Validator(false);
  // }

  // public static Validator critical() {
  //   return new Validator(true);
  // }

  public Validator() {
    // this.critical = false;
  }

  public Validator general() {
    this.critical = false;
    return this;
  }

  public Validator critical() {
    this.critical = true;
    return this;
  }

  // private Validator(boolean critical) {
  //   this.critical = critical;
  // }

  public Matcher validateStringInputWithRegex(String pattern, String input) {
    final Pattern regex = Pattern.compile(pattern);
    final Matcher matcher = regex.matcher(input);

    if (!matcher.find()) {
      final String msg = String.format("invalid input '%s' for pattern '%s'", input, pattern);

      if (this.critical) {
        this.exitWithMessage(msg);
        return null;
      } else {
        throw new IllegalArgumentException(msg);
      }
    } else {
      return matcher;
    }
  }

  private void exitWithMessage(String message) {
    final String color = Pretty.PaperRed500;
    Pretty.colorPrintln(color, message);
    Pretty.colorPrintln(color, "program shut down due to critical error...");
    System.exit(1);
    // Pretty.colorPanic(Pretty.PaperRed500, message);
  }
}
