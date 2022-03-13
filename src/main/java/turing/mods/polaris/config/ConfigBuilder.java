package turing.mods.polaris.config;

import joptsimple.internal.Classes;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ConfigBuilder {
    private static final NullPointerException DEFINE_ERROR = new NullPointerException("current category is null! did you push first????");

    protected final String parentFolder;
    protected final String name;
    protected final Type type;
    protected final List<IConfigCategory> categories = new ArrayList<>();
    protected ConfigCategory currentCategory;

    private ConfigBuilder(Type type, String parentFolder, String name) {
        this.type = type;
        this.name = name;
        this.parentFolder = parentFolder;
    }

    public ConfigBuilder push(String name) {
        if (currentCategory == null) currentCategory = new ConfigCategory(name, null);
        else currentCategory = new ConfigCategory(name, currentCategory);
        return this;
    }

    public ConfigBuilder comment(String comment) {
        if (currentCategory != null) {
            CommentConfigValue commentValue = new CommentConfigValue(currentCategory, comment);
            currentCategory.addValue(commentValue);
        }
        return this;
    }

    public StringConfigValue define(String name, @Nullable String defaultValue) {
        if (currentCategory == null) throw DEFINE_ERROR;
        StringConfigValue value = new StringConfigValue(currentCategory, name, defaultValue != null ? defaultValue : "");
        currentCategory.addValue(value);
        return value;
    }

    public BooleanConfigValue define(String name, boolean defaultValue) {
        if (currentCategory == null) throw DEFINE_ERROR;
        BooleanConfigValue value = new BooleanConfigValue(currentCategory, name, defaultValue);
        currentCategory.addValue(value);
        return value;
    }

    public <T extends Enum<T>> EnumConfigValue<T> define(String name, @Nonnull T defaultValue, @Nonnull Class<T> enumClass) {
        if (currentCategory == null) throw DEFINE_ERROR;
        EnumConfigValue<T> value = new EnumConfigValue<>(currentCategory, name, str -> T.valueOf(enumClass, str), defaultValue);
        currentCategory.addValue(value);
        return value;
    }

    public IntConfigValue defineInRange(String name, int defaultValue, int min, int max) {
        if (currentCategory == null) throw DEFINE_ERROR;
        defaultValue = MathHelper.clamp(defaultValue, min, max);
        IntConfigValue value = new IntConfigValue(currentCategory, name, defaultValue);
        currentCategory.addValue(value);
        return value;
    }

    public FloatConfigValue defineInRange(String name, float defaultValue, float min, float max) {
        if (currentCategory == null) throw DEFINE_ERROR;
        defaultValue = MathHelper.clamp(defaultValue, min, max);
        FloatConfigValue value = new FloatConfigValue(currentCategory, name, defaultValue);
        currentCategory.addValue(value);
        return value;
    }

    public <T> ConfigValue<T> define(String name, @Nullable T defaultValue) {
        if (currentCategory == null) throw DEFINE_ERROR;
        ConfigValue<T> value = new ConfigValue<>(currentCategory, name, defaultValue);
        currentCategory.addValue(value);
        return value;
    }

    public <T> NonNullConfigValue<T> defineNonNull(String name, @Nonnull T defaultValue) {
        if (currentCategory == null) throw DEFINE_ERROR;
        NonNullConfigValue<T> value = new NonNullConfigValue<>(currentCategory, name, Objects.requireNonNull(defaultValue));
        currentCategory.addValue(value);
        return value;
    }

    public void pop() {
        if (currentCategory != null) {
            if (currentCategory.getCategory() == null) currentCategory = null;
            else currentCategory = currentCategory.getParent();
        }
    }

    public static ConfigBuilder newBuilder(Type type, String name) {
        return newBuilder(type, name, "config");
    }

    public static ConfigBuilder newBuilder(Type type, String name, String parentFolder) {
        return new ConfigBuilder(type, parentFolder, name);
    }

    public Config build() {
        return new Config(parentFolder, type, name, categories.toArray(new IConfigCategory[0]));
    }

    public static class Config {
        public final String parentFolder;
        public final Type type;
        public final String name;
        public final IConfigCategory[] categories;

        protected Config(String parentFolder, Type type, String name, IConfigCategory[] categories) {
            this.name = name;
            this.type = type;
            this.parentFolder = parentFolder;
            this.categories = categories;
        }
    }

    public enum Type {
        SERVER,
        CLIENT,
        COMMON;

        public String getFolder(String parentFolder) {
            return parentFolder + "/" + this.name().toLowerCase();
        }
    }
}
