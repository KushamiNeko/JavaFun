package trading;

public enum TradeDirection {
  LONG, SHORT;

  public String toString() {
    return switch (this) {
      case LONG -> "LONG";
      case SHORT -> "SHORT";
      default -> throw new IllegalArgumentException("unknown trade operation");

    };
  }
}
