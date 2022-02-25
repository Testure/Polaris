package turing.mods.polaris.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Tuple;
import net.minecraftforge.eventbus.api.IEventBus;
import turing.mods.polaris.material.GenerationFlags;
import turing.mods.polaris.material.MaterialBuilder;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.material.TextureSet;

import java.util.Map;

public class MaterialRegistry {
    private static final MaterialDeferredRegister MATERIAL_DEFERRED_REGISTER = new MaterialDeferredRegister();

    private static final Tuple<SubItem, Item>[] IRON_EXISTING = new Tuple[]{
            new Tuple<>(SubItem.INGOT, Items.IRON_INGOT),
            new Tuple<>(SubItem.BLOCK, Items.IRON_BLOCK),
            new Tuple<>(SubItem.AXE, Items.IRON_AXE),
            new Tuple<>(SubItem.NUGGET, Items.IRON_NUGGET),
            new Tuple<>(SubItem.HOE, Items.IRON_HOE),
            new Tuple<>(SubItem.PICKAXE, Items.IRON_PICKAXE),
            new Tuple<>(SubItem.SHOVEL, Items.IRON_SHOVEL),
            new Tuple<>(SubItem.SWORD, Items.IRON_SWORD)
    };

    public static final MaterialRegistryObject IRON = MaterialBuilder.builder("iron")
            .color(0xFFC1C1C1)
            .ingot()
            .fluid(false, 1200)
            .addFlags(GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SMALL_GEAR, GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_SPRING)
            .mass(90)
            .toolStats(1000, 1, 2.0F, 2.0F, 3.0F)
            .ore(1)
            .textureSet(TextureSet.METAL)
            .withExistingItems(IRON_EXISTING)
            .buildAndRegister();

    public static final MaterialRegistryObject MAGNETIC_IRON = MaterialBuilder.builder("magnetic_iron")
            .color(0xFFC1C1C1)
            .ingot()
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.NO_COMPRESSION)
            .mass(90)
            .magnetic()
            .textureSet(TextureSet.MAGNETIC)
            .buildAndRegister();

    public static final MaterialRegistryObject COPPER = MaterialBuilder.builder("copper")
            .color(0xFFFF8A23)
            .ingot()
            .fluid(false, 1200)
            .mass(64)
            .ore(1)
            .addFlags(GenerationFlags.GENERATE_SPRING)
            .textureSet(TextureSet.SHINY_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject RUBBER = MaterialBuilder.builder("rubber")
            .color(0xFF191919)
            .soft()
            .fluid(false, 1000)
            .mass(32)
            .textureSet(TextureSet.DULL_METAL)
            .addFlags(GenerationFlags.IS_SOFT, GenerationFlags.NO_VANILLA_TOOLS)
            .toolStats(1200, 2, 3.0F, 1.0F, 3.0F)
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
