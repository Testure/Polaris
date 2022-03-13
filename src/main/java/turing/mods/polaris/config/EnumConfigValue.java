package turing.mods.polaris.config;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.function.Function;

@ParametersAreNonnullByDefault
public class EnumConfigValue<T extends Enum<?>> extends NonNullConfigValue<T> {
    private final Function<String, T> creator;

    public EnumConfigValue(IConfigCategory category, String name, Function<String, T> creator, T value) {
        super(category, name, value);
        this.creator = creator;
    }

    @Override
    public void deserialize(String str) {
        this.value = Objects.requireNonNull(creator.apply(str));
    }

    @Override
    public String serialize() {
        return get().name().toLowerCase();
    }
}
