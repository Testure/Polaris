package turing.mods.polaris.config;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class StringConfigValue extends NonNullConfigValue<String> {
    public StringConfigValue(IConfigCategory category, String name, String value) {
        super(category, name, value);
    }
}
