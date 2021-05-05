package com.KushamiNeko.happy.futures;

public enum MonthCode {
  JANUARY("f"),
  FEBRUARY("g"),
  MARCH("h"),
  APRIL("j"),
  MAY("k"),
  JUNE("m"),
  JULY("n"),
  AUGUST("q"),
  SEPTEMBER("u"),
  OCTOBER("v"),
  NOVEMBER("x"),
  DECEMBER("z");

  public static MonthCode fromCode(String code) {
    switch (code) {
      case "f":
        return JANUARY;
      case "g":
        return FEBRUARY;
      case "h":
        return MARCH;
      case "j":
        return APRIL;
      case "k":
        return MAY;
      case "m":
        return JUNE;
      case "n":
        return JULY;
      case "q":
        return AUGUST;
      case "u":
        return SEPTEMBER;
      case "v":
        return OCTOBER;
      case "x":
        return NOVEMBER;
      case "z":
        return DECEMBER;
      default:
        throw new IllegalArgumentException(String.format("invalid futures month code: %s", code));
    }
  }

  public static MonthCode fromMonthValue(int month) {
    if (month < 1 || month > 12) {
      throw new IllegalArgumentException(String.format("invalid month: %s", month));
    }

    return MonthCode.values()[month - 1];

    // switch (month) {
    // case 1:
    // return JANUARY;
    // case 2:
    // return FEBRUARY;
    // case 3:
    // return MARCH;
    // case 4:
    // return APRIL;
    // case 5:
    // return MAY;
    // case 6:
    // return JUNE;
    // case 7:
    // return JULY;
    // case 8:
    // return AUGUST;
    // case 9:
    // return SEPTEMBER;
    // case 10:
    // return OCTOBER;
    // case 11:
    // return NOVEMBER;
    // case 12:
    // return DECEMBER;
    // default:
    // throw new IllegalArgumentException(String.format("invalid month: %s" ,
    // month));
    // }
  }

  private final String code;

  private MonthCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
    // switch (this) {
    // case JANUARY:
    // return "f";
    // case FEBRUARY:
    // return "g";
    // case MARCH:
    // return "h";
    // case APRIL:
    // return "j";
    // case MAY:
    // return "k";
    // case JUNE:
    // return "m";
    // case JULY:
    // return "n";
    // case AUGUST:
    // return "q";
    // case SEPTEMBER:
    // return "u";
    // case OCTOBER:
    // return "v";
    // case NOVEMBER:
    // return "x";
    // case DECEMBER:
    // return "z";
    // default:
    // throw new IllegalArgumentException("unknown futures month code");
    // }
  }

  public int getMonthValue() {
    return this.ordinal() + 1;
  }

}
