package neko.happy.operator;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CryptoWorker30Minutes extends CryptoWorker {

    @Override
    String getName() {
        return "Crypto Intrady 30 Minutes";
    }

    @Override
    Path getDstPath(String dstDir, String symbol, String contract) {
        return Paths.get(
                dstDir,
                "coinapi",
                String.format("%s@30m", symbol.toLowerCase()),
                String.format("%s@%s.json", symbol.toLowerCase(), contract)
        );
    }

}
