package turing.mods.polaris.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import turing.mods.polaris.Polaris;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfigSerializer {
    public static final File CONFIG_DIR = new File("config");
    protected static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static JsonObject serialize(ConfigBuilder.Config config) {
        JsonObject json = new JsonObject();

        return json;
    }

    public static void writeConfig(ConfigBuilder.Config config) {
        JsonObject json = serialize(config);
        File parentFolder = new File(config.type.getFolder(CONFIG_DIR.getPath() + "/" + config.parentFolder));

        if (!initFolder(parentFolder)) throw new NullPointerException("Could not write config!");

        String jsonString = GSON.toJson(json);
        String fileName = parentFolder.getPath() + "/" + config.name + ".json";
        Path path = Paths.get(fileName);

        if (path.toFile().exists()) throw new IllegalStateException(String.format("Config %s already exists!", fileName));
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(jsonString);
        } catch (IOException e) {
            Polaris.LOGGER.error(e);
        }
    }

    private static boolean initFolder(File dir) {
        if (!dir.exists() && !dir.mkdir()){
            Polaris.LOGGER.error(String.format("Could not make folder at %s", dir.getAbsolutePath()));
            return false;
        }
        return true;
    }
}
