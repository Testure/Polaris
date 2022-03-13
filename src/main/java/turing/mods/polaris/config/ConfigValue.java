package turing.mods.polaris.config;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ConfigValue<T> implements IConfigValue<T> {
    protected final String name;
    protected final IConfigCategory category;
    protected T value;

    public ConfigValue(IConfigCategory category, String name, @Nullable T value) {
        this.category = category;
        this.name = name;
        this.value = value;
    }

    @Override
    @Nullable
    public T get() {
        return value;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public IConfigCategory getCategory() {
        return category;
    }
}
