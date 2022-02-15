package turing.mods.polaris.registry;

import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import turing.mods.polaris.material.GenerationFlags;
import turing.mods.polaris.material.MaterialBuilder;
import turing.mods.polaris.material.TextureSet;

import java.util.Map;

public class MaterialRegistry {
    private static final MaterialDeferredRegister MATERIAL_DEFERRED_REGISTER = new MaterialDeferredRegister();

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

    public static MaterialRegistryObject register(String name, MaterialBuilder builder) {
        return MATERIAL_DEFERRED_REGISTER.register(name, builder);
    }

    public static void register(IEventBus bus) {
        MATERIAL_DEFERRED_REGISTER.register(bus);
    }

    public static Map<String, MaterialRegistryObject> getMaterials() {
        return MATERIAL_DEFERRED_REGISTER.getMaterials();
    }
}
