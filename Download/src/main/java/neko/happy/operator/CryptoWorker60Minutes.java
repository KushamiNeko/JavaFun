package neko.happy.operator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CryptoWorker60Minutes extends CryptoWorker {

    @Override
    String getName() {
        return "Crypto Intraday 60 Minutes";
    }

    @Override
    Path getDstPath(String dstDir, String symbol, String contract) {
        return Paths.get(
                dstDir,
                "coinapi",
                String.format("%s@60m", symbol.toLowerCase()),
                String.format("%s@%s.json", symbol.toLowerCase(), contract)
        );
    }

}
