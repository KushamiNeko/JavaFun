package neko.fun.futures;

public enum ContractFormat {
  BARCHART("^(\\w{2})(\\w)(\\d{2})$"), MYCRYPTO("^(\\w{3})(\\w{3})@(\\d{6})$");

  private final String pattern;

  ContractFormat(String pattern) {
    this.pattern = pattern;
  }

  String getPattern() {
    return this.pattern;
  }
}
