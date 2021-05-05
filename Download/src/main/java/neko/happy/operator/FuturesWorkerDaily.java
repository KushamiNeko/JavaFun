package neko.happy.operator;

import java.nio.file.Paths;
import java.util.List;

class FuturesWorkerDaily extends FuturesWorker {

  @Override
  String getName() {
    return "Futures Daily";
  }

  @Override
  List<String> getSource() {
    return List.of(
        "es",
        "nq",
        "qr",
        "ym",
        "np",
        "fx",
        "zn",
        "zf",
        "zt",
        "zb",
        "ge",
        "tj",
        "gg",
        "hr",
        "hf",
        "gx",
        "fn",
        "dx",
        "e6",
        "j6",
        "b6",
        "a6",
        "d6",
        "s6",
        "n6"
        /* "gc",
        // "si",
        "cl",
        // "ng",
        "zs",
        "zc",
        "zw" */
    );
  }

  @Override
  String getDstPath(String dstDir, String code) {
    return Paths.get(dstDir, "continuous", code.substring(0, 2), String.format("%s.csv", code)).toString();
  }
}
