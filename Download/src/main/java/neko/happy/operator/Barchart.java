package neko.happy.operator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

abstract class Barchart extends Operator {

  final String symbolPatternFutures = "([\\w\\d]{5})";
  // final String symbolPatternForexOrIndex = "([\\$\\^][\\w]+)";
  final String symbolPatternForexOrIndex = "([\\$\\^]*[a-zA-Z0-9.]+)";

  BarchartPage barchartPage;

  Barchart() {
    super();
    this.barchartPage = BarchartPage.HISTORICAL;
  }

  Barchart(String srcDir, String dstDir) {
    super(srcDir, dstDir);
    this.barchartPage = BarchartPage.HISTORICAL;
  }

  String getPage(String symbol) throws UnsupportedEncodingException {
    return String.format(this.barchartPage.getPageURL(), URLEncoder.encode(symbol, StandardCharsets.UTF_8.toString()));
  }

  // public void fromHistorical() {
  //   this.barchartPage = BarchartPage.HISTORICAL;
  // }

  // public void fromInteractive() {
  //   this.barchartPage = BarchartPage.INTERACTIVE;
  // }
}
