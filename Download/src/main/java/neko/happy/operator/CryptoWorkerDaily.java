package neko.happy.operator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CryptoWorkerDaily extends CryptoWorker {

    @Override
    String getName() {
        return "Crypto Daily";
    }

    @Override
    Path getDstPath(String dstDir, String symbol, String contract) {
        return Paths.get(
                dstDir,
                "coinapi",
                symbol.toLowerCase(),
                String.format("%s@%s.json", symbol.toLowerCase(), contract)
        );
    }

}
