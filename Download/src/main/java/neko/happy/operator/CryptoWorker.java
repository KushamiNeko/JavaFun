package neko.happy.operator;

import java.nio.file.Path;
import java.util.List;

abstract class CryptoWorker {

    abstract String getName();

    List<String> getSource() {
        return List.of(
                //
                "BTCUSD",
                "ETHUSD",
                "LTCUSD",
                "BCHUSD",
                "XRPUSD",
                "USDCUSD"
        );
    }

    abstract Path getDstPath(String dstDir, String symbol, String contract);
}
