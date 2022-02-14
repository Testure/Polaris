package turing.mods.polaris.registry;

import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import turing.mods.polaris.material.GenerationFlags;
import turing.mods.polaris.material.MaterialBuilder;
import turing.mods.polaris.material.TextureSet;

import java.util.Map;

public class MaterialRegistry {
    private static final MaterialDeferredRegister MATERIAL_DEFERRED_REGISTER = new MaterialDeferredRegister();

    public static final MaterialRegistryObject IRON = MATERIAL_DEFERRED_REGISTER.register("iron", MaterialBuilder.builder("iron")
            .color(0xFFE5E5E5)
            .ingot()
            .fluid(false, 1200)
            .addFlags(GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_ROD, GenerationFlags.GENERATE_SMALL_GEAR, GenerationFlags.GENERATE_SCREW)
            .mass(40)
            .toolStats(100, 1.0F, 2.0F, 2.0F)
            .ore("block/material_sets/metal/ore", null, 1)
            .textureSet(TextureSet.METAL)
            .withExistingItems(Items.IRON_INGOT, Items.IRON_BLOCK, Items.IRON_AXE, Items.IRON_NUGGET, Items.IRON_HOE, Items.IRON_PICKAXE, Items.IRON_SHOVEL, Items.IRON_SWORD)
    );

    public static void register(IEventBus bus) {
        MATERIAL_DEFERRED_REGISTER.register(bus);
    }

    public static Map<String, MaterialRegistryObject> getMaterials() {
        return MATERIAL_DEFERRED_REGISTER.getMaterials();
    }
}
