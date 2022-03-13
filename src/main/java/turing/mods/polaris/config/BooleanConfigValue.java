package turing.mods.polaris.config;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BooleanConfigValue extends NonNullConfigValue<Boolean> {
    public BooleanConfigValue(IConfigCategory category, String name, Boolean value) {
        super(category, name, value);
    }
}
