package neko.happy.operator;

import java.nio.file.Paths;
import java.util.List;

public class FuturesWorker15Minutes extends FuturesWorker {

  @Override
  String getName() {
    return "Futures Intraday 15 Minutes";
  }

  @Override
  List<String> getSource() {
    return List.of(
        //
        "zn",
        "zf"
    // "zt",
    // "zb",
    // "gg"
    );
  }

  @Override
  String getDstPath(String dstDir, String code) {
    return Paths.get(dstDir, "continuous", String.format("%s@15m", code.substring(0, 2)), String.format("%s.csv", code))
        .toString();
  }
}
