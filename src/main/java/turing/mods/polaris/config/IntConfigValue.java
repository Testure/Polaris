package turing.mods.polaris.config;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class IntConfigValue extends NonNullConfigValue<Integer> {
    public IntConfigValue(IConfigCategory category, String name, Integer value) {
        super(category, name, value);
    }
}
