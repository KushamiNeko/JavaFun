package com.KushamiNeko.happy.futures;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.KushamiNeko.happy.pretty.Utils;

public class ContractMonths {
  private final String allMonths = "fghjkmnquvxz";
  private final String oddMonths = "fhknux";
  private final String evenMonths = "gjmqvz";
  private final String financialMonths = "hmuz";
  private final String cryptoMonths = "fjnv";

  private String months = null;

  public ContractMonths() {
  }

  public ContractMonths(String symbol) {
    this.months = this.defaultContractMonths(symbol);
  }

  public ContractMonths all() {
    this.months = allMonths;
    return this;
  }

  public ContractMonths odd() {
    this.months = oddMonths;
    return this;
  }

  public ContractMonths even() {
    this.months = evenMonths;
    return this;
  }

  public ContractMonths financial() {
    this.months = financialMonths;
    return this;
  }

  public ContractMonths crypto() {
    this.months = cryptoMonths;
    return this;
  }

  String defaultContractMonths(String symbol) {
    switch (symbol) {
      case "cl":
        return allMonths;
      case "ng":
        return allMonths;
      case "gc":
        return evenMonths;
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
        return financialMonths;
    }
  }

  public String getMonths() {
    return months;
  }

  public void setCustomMonths(String months) {
    Utils.validateInput(months, "^[fghjkmnquvxz]+$");
    this.months = months;
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
