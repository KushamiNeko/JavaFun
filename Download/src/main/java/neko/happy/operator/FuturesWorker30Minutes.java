package neko.happy.operator;

import java.nio.file.Paths;
import java.util.List;

public class FuturesWorker30Minutes extends FuturesWorker {

  @Override
  String getName() {
    return "Futures Intraday 30 Minutes";
  }

  @Override
  List<String> getSource() {
    return List.of(
        //
        "zn",
        "zf",
        "zt",
        "zb",
         "gg",
         "hr",
        "e6",
        "j6"
    // "a6",
    // "b6",
    // "es"
    );
  }

  @Override
  String getDstPath(String dstDir, String code) {
    return Paths.get(dstDir, "continuous", String.format("%s@30m", code.substring(0, 2)), String.format("%s.csv", code))
        .toString();
  }
}
