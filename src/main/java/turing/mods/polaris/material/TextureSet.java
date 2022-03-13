package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public enum TextureSet {
    METAL,
    DULL_METAL,
    DULL_METAL_HARSH,
    GEM,
    EMERALD("minecraft:block/emerald_ore"),
    COAL,
    QUARTZ,
    RUBY("minecraft:block/emerald_ore"),
    SHINY_METAL,
    MAGNETIC;

    private final String oreBackground;

    TextureSet(String oreBackground) {
        this.oreBackground = oreBackground;
    }

    TextureSet() {
        this("minecraft:block/iron_ore");
    }

    @Nonnull
    public String getOreBackground() {
        return oreBackground;
    }
}
