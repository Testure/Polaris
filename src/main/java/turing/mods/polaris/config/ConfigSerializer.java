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

    public static JsonObject serialize(Object config) {
        JsonObject json = new JsonObject();

        return json;
    }

    public static void writeConfig(JsonObject json, String name, File folder) {
        if (!initFolder(folder)) throw new NullPointerException("Could not write config!");

        String jsonString = GSON.toJson(json);
        String fileName = folder.getPath() + "/" + name;
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
