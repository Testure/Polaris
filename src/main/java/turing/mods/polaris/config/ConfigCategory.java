package turing.mods.polaris.config;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfigCategory implements IConfigCategory {
    private final String name;
    private final ConfigCategory category;
    private final List<IConfigValue<?>> values = NonNullList.create();
    private final List<IConfigCategory> subCategories = NonNullList.create();

    public ConfigCategory(String name, @Nullable ConfigCategory parent) {
        this.name = name;
        this.category = parent;
    }

    @Nullable
    public ConfigCategory getParent() {
        return category;
    }

    public void addSubCategory(IConfigCategory category) {
        subCategories.add(category);
    }

    public void addValue(IConfigValue<?> value) {
        values.add(value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    @Nullable
    public IConfigCategory getCategory() {
        return category;
    }

    @Override
    public IConfigCategory[] getSubCategories() {
        return subCategories.toArray(new IConfigCategory[0]);
    }

    @Override
    public IConfigValue<?>[] getValues() {
        return values.toArray(new IConfigValue[0]);
    }

    @Override
    public IConfigValue<?>[] getAllValues() {
        List<IConfigValue<?>> allValues = new ArrayList<>(values);
        for (IConfigCategory category : subCategories) allValues.addAll(Arrays.asList(category.getAllValues()));
        return allValues.toArray(new IConfigValue[0]);
    }
}
