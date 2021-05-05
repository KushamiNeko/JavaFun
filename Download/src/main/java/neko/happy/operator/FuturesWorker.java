package neko.happy.operator;

import java.util.List;

abstract class FuturesWorker {
  abstract String getName();

  abstract List<String> getSource();

  abstract String getDstPath(String dstDir, String code);
}
