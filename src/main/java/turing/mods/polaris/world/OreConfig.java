package turing.mods.polaris.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import turing.mods.polaris.Polaris;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OreConfig {
    public static final File CONFIG_DIR = new File("config/polaris/world_gen");
    private static final File OW = new File("config/polaris/world_gen/overworld");
    private static final File NETHER = new File("config/polaris/world_gen/nether");
    private static final File END = new File("config/polaris/world_gen/end");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void init() {
        if (!CONFIG_DIR.exists() && !CONFIG_DIR.mkdirs() && !CONFIG_DIR.mkdir()) Polaris.LOGGER.error(String.format("Failed to create config folder at %s", CONFIG_DIR.getAbsolutePath()));
    }

    private static boolean initFolder(File dir) {
        if (!dir.exists() && !dir.mkdir()){
            Polaris.LOGGER.error(String.format("Could not make folder at %s", dir.getAbsolutePath()));
            return false;
        }
        return true;
    }

    public static void writeVeins() {
        for (Map.Entry<String, VeinConfiguration> entry : OreGeneration.getOreVeins().entrySet()) {
            VeinConfiguration vein = entry.getValue();
            File parent = CONFIG_DIR;
            if (vein.filler.equals(OreGeneration.FillerBlockType.OW) && initFolder(OW)) parent = OW;
            else if (vein.filler.equals(OreGeneration.FillerBlockType.NETHER) && initFolder(NETHER)) parent = NETHER;
            else if (vein.filler.equals(OreGeneration.FillerBlockType.END) && initFolder(END)) parent = END;

            JsonObject json = vein.write();
            String name = entry.getKey();
            String jsonS = GSON.toJson(json);
            String fileName = parent.getPath() + "/" + name + ".json";
            Path jsonPath = Paths.get(fileName);
            if (jsonPath.toFile().exists()) continue;
            if (parent == CONFIG_DIR) {
                if (OW.exists() && Paths.get(OW.getPath() + "/" + name + ".json").toFile().exists()) continue;
                if (NETHER.exists() && Paths.get(NETHER.getPath() + "/" + name + ".json").toFile().exists()) continue;
                if (END.exists() && Paths.get(END.getPath() + "/" + name + ".json").toFile().exists()) continue;
            }

            try (BufferedWriter writer = Files.newBufferedWriter(jsonPath)) {
                writer.write(jsonS);
            } catch (IOException exception) {
                Polaris.LOGGER.error(exception);
            }
        }
    }

    public static Map<String, VeinConfiguration> readVeins() {
        Map<String, VeinConfiguration> veins = new HashMap<>();

        if (CONFIG_DIR.exists()) {
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

            readFrom.accept(CONFIG_DIR.listFiles());
            if (OW.exists()) readFrom.accept(OW.listFiles());
            if (NETHER.exists()) readFrom.accept(NETHER.listFiles());
            if (END.exists()) readFrom.accept(END.listFiles());
        }

        return veins;
    }
}
