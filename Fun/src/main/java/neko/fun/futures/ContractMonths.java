package neko.fun.futures;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import neko.fun.utils.Validator;

public class ContractMonths {
  private static final String allMonths = "fghjkmnquvxz";
  private static final String oddMonths = "fhknux";
  private static final String evenMonths = "gjmqvz";
  private static final String financialMonths = "hmuz";
  private static final String cryptoMonths = "fjnv";

  private final String months;

  public static ContractMonths all() {
    return new ContractMonths(ContractMonths.allMonths);
  }

  public static ContractMonths odd() {
    return new ContractMonths(ContractMonths.oddMonths);
  }

  public static ContractMonths even() {
    return new ContractMonths(ContractMonths.evenMonths);
  }

  public static ContractMonths financial() {
    return new ContractMonths(ContractMonths.financialMonths);
  }

  public static ContractMonths crypto() {
    return new ContractMonths(ContractMonths.cryptoMonths);
  }

  public static ContractMonths custom(String months) {
    new Validator().validateStringInputWithRegex("^[fghjkmnquvxz]+$", months);
    return new ContractMonths(months);
  }

  public static ContractMonths fromSymbol(String symbol) {
    return new ContractMonths(ContractMonths.defaultContractMonths(symbol));
  }

  private static String defaultContractMonths(String symbol) {
    switch (symbol) {
      case "cl":
        return ContractMonths.allMonths;
      case "ng":
        return ContractMonths.allMonths;
      case "gc":
        return ContractMonths.evenMonths;
      case "si":
        return "hknuz";
      case "hg":
        return "hknuz";
      case "zs":
        return "fhknqux";
      case "zc":
        return "hknuz";
      case "zw":
        return "hknuz";
      default:
        return ContractMonths.financialMonths;
    }
  }

  private ContractMonths(String months) {
    this.months = months;
  }

  public String getMonths() {
    return months;
  }

  public void forEach(Consumer<MonthCode> fun) {
    if (this.months == null) {
      throw new IllegalArgumentException("contract months should be set before calling forEach");
    }

    for (char c : this.months.toCharArray()) {
      final MonthCode mc = MonthCode.fromCode(String.valueOf(c));
      fun.accept(mc);
    }
  }

  public void forEachReverse(Consumer<MonthCode> fun) {
    if (this.months == null) {
      throw new IllegalArgumentException("contract months should be set before calling forEach");
    }

    final List<Character> monthsReverse = this.months.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    Collections.reverse(monthsReverse);

    for (char c : monthsReverse) {
      final MonthCode mc = MonthCode.fromCode(String.valueOf(c));
      fun.accept(mc);
    }
  }
}
