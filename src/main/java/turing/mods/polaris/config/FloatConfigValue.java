package turing.mods.polaris.config;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FloatConfigValue extends NonNullConfigValue<Float> {
    public FloatConfigValue(IConfigCategory category, String name, Float value) {
        super(category, name, value);
    }
}
