package turing.mods.polaris.config;

import javax.annotation.Nonnull;
import java.util.Objects;

public class NonNullConfigValue<T> extends ConfigValue<T> {
    public NonNullConfigValue(IConfigCategory category, String name, @Nonnull T value) {
        super(category, name, value);
    }

    @Nonnull
    @Override
    public T get() {
        return Objects.requireNonNull(super.get());
    }
}
