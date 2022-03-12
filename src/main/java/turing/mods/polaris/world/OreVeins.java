package turing.mods.polaris.world;

import turing.mods.polaris.material.Materials;
import turing.mods.polaris.util.Vector2i;

public class OreVeins {
    public static final VeinConfiguration BANDED_IRON_VEIN = VeinBuilder.builder("banded_iron_vein")
            .withChance(3)
            .inDimension(0)
            .atYLevel(new Vector2i(10, 40))
            .defaultChances(4)
            .withOres(Materials.BROWN_LIMONITE, Materials.YELLOW_LIMONITE, Materials.MALACHITE, Materials.BANDED_IRON)
            .build(OreGeneration::registerVein);

    public static final VeinConfiguration TIN_VEIN = VeinBuilder.builder("tin_vein")
            .withChance(5)
            .inDimension(0)
            .atYLevel(new Vector2i(40, 60))
            .defaultChances(4)
            .withOres(Materials.TIN, Materials.TIN, Materials.TIN, Materials.CASSITERITE)
            .build(OreGeneration::registerVein);

    public static final VeinConfiguration COPPER_VEIN = VeinBuilder.builder("copper_vein")
            .withChance(5)
            .inDimension(0)
            .atYLevel(new Vector2i(5, 60))
            .defaultChances(4)
            .withOres(Materials.CHALCOPYRITE, Materials.IRON, Materials.COPPER, Materials.PYRITE)
            .build(OreGeneration::registerVein);

    public static void register() {
        OreConfig.writeVeins();
    }
}
