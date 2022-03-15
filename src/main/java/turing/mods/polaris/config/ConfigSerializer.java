package turing.mods.polaris.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import turing.mods.polaris.Polaris;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.BufferedReader;
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

    public static JsonObject serialize(Config config) {
        JsonObject json = new JsonObject();

        for (ConfigCategory category : config.categories) writeCategory(json, category);

        return json;
    }

    private static void writeCategory(JsonObject parent, ConfigCategory category) {
        JsonObject categoryJSON = new JsonObject();

        for (ConfigValue<?> value : category.getValues()) {
            if (value instanceof IntegerConfigValue || value instanceof FloatConfigValue) categoryJSON.addProperty(value.name, (Number) value.get());
            else if (value instanceof BooleanConfigValue) categoryJSON.addProperty(value.name, (Boolean) value.get());
            else categoryJSON.addProperty(value.name, (String) value.get());
        }

        for (ConfigCategory category1 : category.subCategories) writeCategory(categoryJSON, category1);
        parent.add(category.name, categoryJSON);
    }

    public static void writeConfig(Config config) {
        JsonObject json = serialize(config);
        String name = config.name + ".json";
        String folderName = config.type.getFolder(CONFIG_DIR.getPath() + "/" + config.folder);
        writeConfigJson(json, name, new File(folderName));
    }

    public static void readConfig(Config config) {
        JsonObject json = readConfigJson(config.name + ".json", new File(config.type.getFolder(CONFIG_DIR.getPath() + "/" + config.folder)));
        if (json == null) throw new NullPointerException("Could not read config json!");

        for (ConfigCategory category : config.categories) readCategory(json, category);
    }

    private static void readCategory(JsonObject json, ConfigCategory category) {
        JsonObject categoryJSON = json.getAsJsonObject(category.name);
        if (categoryJSON == null) throw new NullPointerException();

        for (ConfigValue<?> value : category.getValues()) {
            if (value instanceof IntegerConfigValue) ((IntegerConfigValue) value).set(categoryJSON.get(value.name).getAsInt());
            else if (value instanceof FloatConfigValue) ((FloatConfigValue) value).set(categoryJSON.get(value.name).getAsFloat());
            else if (value instanceof BooleanConfigValue) ((BooleanConfigValue) value).set(categoryJSON.get(value.name).getAsBoolean());
            else ((ConfigValue<String>) value).set(categoryJSON.get(value.name).getAsString());
        }

        for (ConfigCategory category1 : category.getSubCategories()) readCategory(categoryJSON, category1);
    }

    @Nullable
    public static JsonObject readConfigJson(String name, File folder) {
        if (!folder.exists()) throw new NullPointerException("Attempt to read config from non-existent folder!");

        String fileName = folder.getPath() + "/" + name;
        Path path = Paths.get(fileName);

        if (!path.toFile().exists()) throw new NullPointerException("Attempt to read json from non-existent config file!");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            Polaris.LOGGER.error(e);
        }
        return null;
    }

    public static File getConfigFile(Config config) {
        Path path = Paths.get(config.type.getFolder(CONFIG_DIR.getPath() + "/" + config.folder) + "/" + config.name + ".json");
        return path.toFile();
    }

    public static void writeConfigJson(JsonObject json, String name, File folder) {
        if (!initFolder(folder)) throw new NullPointerException("Could not write config!");

        String fileName = folder.getPath() + "/" + name;
        Path path = Paths.get(fileName);

        if (path.toFile().exists()) throw new IllegalStateException(String.format("Config %s already exists!", fileName));
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(GSON.toJson(json));
        } catch (IOException e) {
            Polaris.LOGGER.error(e);
        }
    }

    private static boolean initFolder(File dir) {
        if (!dir.exists() && !dir.mkdirs()){
            Polaris.LOGGER.error(String.format("Could not make folder at %s", dir.getAbsolutePath()));
            return false;
        }
        return true;
    }
}
