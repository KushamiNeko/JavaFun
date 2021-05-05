package neko.happy.operator;

public enum BarchartPage {
  HISTORICAL, INTERACTIVE;

  private final String historicalPage = "https://www.barchart.com/futures/quotes/%s/historical-download";
  private final String interactivePage = "https://www.barchart.com/futures/quotes/%s/interactive-chart";

  private final String historicalPattern = "^%s_([^_-]+)(?:-[^_-]+)*_[^_-]+-[^_-]+-\\d{2}-\\d{2}-\\d{4}.csv$";
  private final String interactivePattern = "^%s_[^_]+_[^_]+_[^_]+_([^_]+)(?:_[^_]+)*_\\d{2}_\\d{2}_\\d{4}.csv$";

  String getPageURL() {
    switch (this) {
      case HISTORICAL:
        return this.historicalPage;
      case INTERACTIVE:
        return this.interactivePage;
      default:
        throw new IllegalArgumentException("invalid barchart page");
    }
  }

  String getFilePattern() {
    switch (this) {
      case HISTORICAL:
        return this.historicalPattern;
      case INTERACTIVE:
        return this.interactivePattern;
      default:
        throw new IllegalArgumentException("invalid barchart page");
    }
  }
}
