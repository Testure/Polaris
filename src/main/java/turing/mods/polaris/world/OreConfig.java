package turing.mods.polaris.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import turing.mods.configurator.api.Config;
import turing.mods.configurator.api.ConfigSerializer;
import turing.mods.polaris.Polaris;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OreConfig {
    public static final String CONFIG_DIR = "polaris/world_gen";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void writeVeins() {
        for (Map.Entry<String, VeinConfiguration> entry : OreGeneration.getOreVeins().entrySet()) {
            VeinConfiguration vein = entry.getValue();
            JsonObject json = vein.write();
            String dim = vein.filler.equals(OreGeneration.FillerBlockType.END) ? "end" : (vein.filler.equals(OreGeneration.FillerBlockType.NETHER) ? "nether" : "overworld");
            dim = dim.equals("overworld") && vein.filler.equals(OreGeneration.FillerBlockType.OW) ? dim : "";
            String folder = CONFIG_DIR + (!dim.isEmpty() ? "/" + dim : "");
            File file = new File("config/" + folder + "/" + entry.getKey() + ".json");

            if (file.exists()) continue;
            if (!ConfigSerializer.writeConfigJson(json, entry.getKey(), folder, Config.Type.UNCATEGORIZED)) Polaris.LOGGER.error("Failed to write ore vein!");
        }
    }

    public static Map<String, VeinConfiguration> readVeins() {
        Map<String, VeinConfiguration> veins = new HashMap<>();
        File config_dir = new File("config/" + CONFIG_DIR);
        File OW = new File(config_dir.getPath() + "/overworld");
        File NETHER = new File(config_dir.getPath() + "/nether");
        File END = new File(config_dir.getPath() + "/end");

        if (config_dir.exists()) {
            Consumer<File[]> readFrom = (files) -> {
                if (files != null) {
                    for (File file : files) {
                        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
                            if (!file.isDirectory() && !OreGeneration.getOreVeins().containsKey(file.getName()) && !veins.containsKey(file.getName())) {
                                VeinConfiguration vein = VeinConfiguration.read(GSON.fromJson(reader, JsonObject.class));
                                veins.put(file.getName().replaceFirst("\\.json", ""), vein);
                            }
                        } catch (IOException exception) {
                            Polaris.LOGGER.error(exception);
                        }
                    }
                }
            };

            readFrom.accept(config_dir.listFiles());
            if (OW.exists()) readFrom.accept(OW.listFiles());
            if (NETHER.exists()) readFrom.accept(NETHER.listFiles());
            if (END.exists()) readFrom.accept(END.listFiles());
        }

        return veins;
    }
}
