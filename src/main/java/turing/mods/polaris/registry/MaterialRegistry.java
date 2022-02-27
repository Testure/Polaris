package turing.mods.polaris.registry;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Tuple;
import net.minecraftforge.eventbus.api.IEventBus;
import turing.mods.polaris.material.*;

import java.util.Map;

public class MaterialRegistry {
    private static final MaterialDeferredRegister MATERIAL_DEFERRED_REGISTER = new MaterialDeferredRegister();

    public static final Tuple<SubItem, Item>[] IRON_EXISTING = new Tuple[]{
            new Tuple<>(SubItem.INGOT, Items.IRON_INGOT),
            new Tuple<>(SubItem.BLOCK, Items.IRON_BLOCK),
            new Tuple<>(SubItem.AXE, Items.IRON_AXE),
            new Tuple<>(SubItem.NUGGET, Items.IRON_NUGGET),
            new Tuple<>(SubItem.HOE, Items.IRON_HOE),
            new Tuple<>(SubItem.PICKAXE, Items.IRON_PICKAXE),
            new Tuple<>(SubItem.SHOVEL, Items.IRON_SHOVEL),
            new Tuple<>(SubItem.SWORD, Items.IRON_SWORD)
    };

    public static final Tuple<SubItem, Item>[] GOLD_EXISTING = new Tuple[]{
            new Tuple<>(SubItem.INGOT, Items.GOLD_INGOT),
            new Tuple<>(SubItem.BLOCK, Items.GOLD_BLOCK),
            new Tuple<>(SubItem.AXE, Items.GOLDEN_AXE),
            new Tuple<>(SubItem.NUGGET, Items.GOLD_NUGGET),
            new Tuple<>(SubItem.HOE, Items.GOLDEN_HOE),
            new Tuple<>(SubItem.PICKAXE, Items.GOLDEN_PICKAXE),
            new Tuple<>(SubItem.SHOVEL, Items.GOLDEN_SHOVEL),
            new Tuple<>(SubItem.SWORD, Items.GOLDEN_SWORD)
    };

    public static final MaterialRegistryObject IRON = MaterialBuilder.builder("iron")
            .color(0xC1C1C1)
            .ingot()
            .fluid(false, 1200)
            .component(Components.IRON)
            .addFlags(GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SMALL_GEAR, GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_SPRING)
            .mass(90)
            .toolStats(1000, 1, 2.0F, 2.0F, 3.0F)
            .ore(1)
            .textureSet(TextureSet.METAL)
            .withExistingItems(IRON_EXISTING)
            .buildAndRegister();

    public static final MaterialRegistryObject GOLD = MaterialBuilder.builder("gold")
            .color(0xFFFAF25E)
            .ingot()
            .fluid(false, 1200)
            .component(Components.GOLD)
            .addFlags(GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SCREW, GenerationFlags.NO_MORTAR)
            .mass(120)
            .toolStats(32, 5, 7.0F, 2.6F, 3.0F)
            .ore(1)
            .textureSet(TextureSet.SHINY_METAL)
            .withExistingItems(GOLD_EXISTING)
            .buildAndRegister();

    public static final MaterialRegistryObject MAGNETIC_IRON = MaterialBuilder.builder("magnetic_iron")
            .color(0xFFC1C1C1)
            .ingot()
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.NO_COMPRESSION)
            .mass(90)
            .component(Components.IRON)
            .magnetic(IRON)
            .textureSet(TextureSet.MAGNETIC)
            .buildAndRegister();

    public static final MaterialRegistryObject COPPER = MaterialBuilder.builder("copper")
            .color(0xFFFF8A23)
            .ingot()
            .fluid(false, 1200)
            .mass(64)
            .ore(1)
            .component(Components.COPPER)
            .addFlags(GenerationFlags.GENERATE_SPRING)
            .textureSet(TextureSet.SHINY_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject TIN = MaterialBuilder.builder("tin")
            .color(0xE3E4EA)
            .ingot()
            .fluid(false, 1200)
            .mass(70)
            .ore(1)
            .component(Components.TIN)
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR)
            .textureSet(TextureSet.DULL_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject SILVER = MaterialBuilder.builder("silver")
            .color(0xFFD0CEFF)
            .ingot()
            .fluid(false, 1200)
            .mass(70)
            .ore(1)
            .component(Components.SILVER)
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR)
            .textureSet(TextureSet.SHINY_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject LEAD = MaterialBuilder.builder("lead")
            .color(0x594570)
            .ingot()
            .fluid(false, 1200)
            .mass(65)
            .ore(1)
            .component(Components.LEAD)
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR)
            .textureSet(TextureSet.DULL_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject BRONZE = MaterialBuilder.builder("bronze")
            .color(0xFFE56622)
            .ingot()
            .fluid(false, 1200)
            .mass(80)
            .components(new ComponentStack(Components.COPPER, 3), new ComponentStack(Components.TIN))
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SMALL_GEAR)
            .toolStats(130, 2, 3.0F, 2.5F, 3.0F)
            .textureSet(TextureSet.DULL_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject ELECTRUM = MaterialBuilder.builder("electrum")
            .color(0xFFF6F660)
            .ingot()
            .fluid(false, 1200)
            .mass(140)
            .components(new ComponentStack(Components.GOLD), new ComponentStack(Components.SILVER))
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SMALL_GEAR, GenerationFlags.NO_MORTAR)
            .toolStats(64, 8, 6.0F, 2.7F, 3.0F)
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

    public static final MaterialRegistryObject OXYGEN = MaterialBuilder.builder("oxygen")
            .color(0xFF87CDFF)
            .mass(0)
            .components(new ComponentStack(Components.OXYGEN))
            .textureSet(TextureSet.METAL)
            .fluid(true, 1000)
            .buildAndRegister();

    public static final MaterialRegistryObject HYDROGEN = MaterialBuilder.builder("hydrogen")
            .color(0xFF7C9FFF)
            .mass(0)
            .components(new ComponentStack(Components.HYDROGEN))
            .textureSet(TextureSet.METAL)
            .fluid(true, 1000)
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
