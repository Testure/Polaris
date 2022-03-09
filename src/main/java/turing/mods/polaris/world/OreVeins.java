package turing.mods.polaris.world;

import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.util.Vector2i;

public class OreVeins {
    /*public static final VeinConfiguration COPPER_VEIN = VeinBuilder.builder("copper_vein")
            .inDimension(0)
            .atYLevel(new Vector2i(30, 50))
            .defaultChances(5)
            .withOres(MaterialRegistry.COPPER, MaterialRegistry.IRON, MaterialRegistry.SILVER, MaterialRegistry.TIN)
            .build(OreGeneration::registerVein);*/

    public static void register() {
        OreConfig.writeVeins();
    }
}
