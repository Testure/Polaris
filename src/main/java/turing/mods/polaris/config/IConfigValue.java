package turing.mods.polaris.config;

import javax.annotation.Nonnull;

public interface IConfigValue<T> {
    T get();

    default String serialize() {
        return get().toString();
    }

    default void deserialize(String str) {

    }

    @Nonnull
    String getName();

    @Nonnull
    IConfigCategory getCategory();
}
