package turing.mods.polaris.material;

import net.minecraft.item.Items;
import turing.mods.polaris.registry.MaterialRegistryObject;

/**
 * PLEASE FOR THE LOVE OF GOD DEFINE MATERIALS HERE
 */
public class PolarisMaterials {
    public static final MaterialRegistryObject IRON = MaterialBuilder.builder("iron")
            .color(0xFFC1C1C1)
            .ingot()
            .fluid(false, 1200)
            .addFlags(GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SMALL_GEAR, GenerationFlags.GENERATE_SCREW)
            .mass(120)
            .toolStats(100, 1.0F, 2.0F, 2.0F)
            .ore(1)
            .textureSet(TextureSet.METAL)
            .withExistingItems(Items.IRON_INGOT, Items.IRON_BLOCK, Items.IRON_AXE, Items.IRON_NUGGET, Items.IRON_HOE, Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_SWORD)
            .buildAndRegister();

    public static final MaterialRegistryObject COPPER = MaterialBuilder.builder("copper")
            .color(0xFFFF8A23)
            .ingot()
            .fluid(false, 1200)
            .mass(80)
            .ore(1)
            .textureSet(TextureSet.SHINY_METAL)
            .toolStats(100, 2.0F, 2.0F, 2.0F)
            .buildAndRegister();
}
