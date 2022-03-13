package turing.mods.polaris.config;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.Nullable;

@MethodsReturnNonnullByDefault
public interface IConfigCategory {
    String getName();

    @Nullable
    IConfigCategory getCategory();

    IConfigCategory[] getSubCategories();

    IConfigValue<?>[] getValues();

    IConfigValue<?>[] getAllValues();
}
