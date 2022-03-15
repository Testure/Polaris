package turing.mods.polaris.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Configuration {
    protected static final List<Config> configs = new ArrayList<>();

    public static Config register(Config config) {
        configs.add(config);
        return config;
    }

    public static Config register(Supplier<Config> config) {
        Config c = config.get();
        configs.add(c);
        return c;
    }

    public static void register() {
        for (Config config : configs) {
            File file = ConfigSerializer.getConfigFile(config);
            if (!file.exists()) ConfigSerializer.writeConfig(config);
            else ConfigSerializer.readConfig(config);
        }
    }
}
