package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;

import static turing.mods.polaris.registry.MaterialRegistry.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Materials {
    public static final MaterialRegistryObject IRON = MaterialBuilder.builder("iron")
            .color(0xFFC8C8C8)
            .ingot()
            .fluid(false, 1200)
            .component(Components.IRON)
            .addFlags(GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SMALL_GEAR, GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_SPRING)
            .mass(90)
            .toolStats(250, 1, 5.0F, 5.0F, 3.0F)
            .ore(1)
            .textureSet(TextureSet.METAL)
            .withExistingItems(IRON_EXISTING)
            .buildAndRegister();

    public static final MaterialRegistryObject GOLD = MaterialBuilder.builder("gold")
            .color(0xFFFFE650)
            .ingot()
            .fluid(false, 1200)
            .component(Components.GOLD)
            .addFlags(GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SCREW, GenerationFlags.NO_MORTAR)
            .mass(120)
            .toolStats(32, 5, 8.0F, 3.0F, 3.0F)
            .ore(1)
            .textureSet(TextureSet.SHINY_METAL)
            .withExistingItems(GOLD_EXISTING)
            .buildAndRegister();

    public static final MaterialRegistryObject MAGNETIC_IRON = MaterialBuilder.builder("magnetic_iron")
            .color(0xFFC8C8C8)
            .ingot()
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.NO_COMPRESSION)
            .mass(90)
            .component(Components.IRON)
            .magnetic(IRON)
            .textureSet(TextureSet.MAGNETIC)
            .buildAndRegister();

    public static final MaterialRegistryObject BANDED_IRON = MaterialBuilder.builder("banded_iron")
            .color(0xFF915A5A)
            .dust()
            .ore(1, SubItem.INGOT, IRON)
            .mass(40)
            .addFlags(GenerationFlags.NO_BLOCK)
            .components(new ComponentStack(Components.IRON, 2), new ComponentStack(Components.OXYGEN, 3))
            .textureSet(TextureSet.METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject BROWN_LIMONITE = MaterialBuilder.builder("brown_limonite")
            .color(0xFFC86400)
            .dust()
            .ore(1, SubItem.INGOT, IRON)
            .mass(40)
            .addFlags(GenerationFlags.NO_BLOCK)
            .components(new ComponentStack(Components.IRON), new ComponentStack(Components.HYDROGEN), new ComponentStack(Components.OXYGEN, 2))
            .textureSet(TextureSet.METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject YELLOW_LIMONITE = MaterialBuilder.builder("yellow_limonite")
            .color(0xFFC8C800)
            .dust()
            .ore(1, SubItem.INGOT, IRON)
            .mass(40)
            .addFlags(GenerationFlags.NO_BLOCK)
            .components(new ComponentStack(Components.IRON), new ComponentStack(Components.HYDROGEN), new ComponentStack(Components.OXYGEN, 2))
            .textureSet(TextureSet.METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject PYRITE = MaterialBuilder.builder("pyrite")
            .color(0xFF967828)
            .dust()
            .ore(1, SubItem.INGOT, IRON)
            .mass(40)
            .addFlags(GenerationFlags.NO_BLOCK)
            .components(new ComponentStack(Components.IRON), new ComponentStack(Components.SULFUR, 2))
            .textureSet(TextureSet.METAL)
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

    public static final MaterialRegistryObject CHALCOPYRITE = MaterialBuilder.builder("chalcopyrite")
            .color(0xFFA07828)
            .dust()
            .ore(1, SubItem.INGOT, COPPER)
            .mass(50)
            .textureSet(TextureSet.METAL)
            .addFlags(GenerationFlags.NO_BLOCK)
            .components(new ComponentStack(Components.COPPER), new ComponentStack(Components.IRON), new ComponentStack(Components.SULFUR, 2))
            .buildAndRegister();

    public static final MaterialRegistryObject MALACHITE = MaterialBuilder.builder("malachite")
            .color(0xFF055F05)
            .gem()
            .ore(1, SubItem.INGOT, COPPER)
            .mass(60)
            .textureSet(TextureSet.GEM)
            .addFlags(GenerationFlags.NO_PLATE, GenerationFlags.NO_ROD)
            .components(new ComponentStack(Components.COPPER, 2), new ComponentStack(Components.CARBON), new ComponentStack(Components.HYDROGEN, 2), new ComponentStack(Components.OXYGEN, 5))
            .buildAndRegister();

    public static final MaterialRegistryObject TIN = MaterialBuilder.builder("tin")
            .color(0xFFDCDCDC)
            .ingot()
            .fluid(false, 1200)
            .mass(70)
            .ore(1)
            .component(Components.TIN)
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR)
            .textureSet(TextureSet.METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject CASSITERITE = MaterialBuilder.builder("cassiterite")
            .color(0xFFDCDCDC)
            .dust()
            .ore(2, SubItem.INGOT, TIN)
            .textureSet(TextureSet.DULL_METAL)
            .addFlags(GenerationFlags.NO_BLOCK)
            .components(new ComponentStack(Components.TIN), new ComponentStack(Components.OXYGEN, 2))
            .buildAndRegister();

    public static final MaterialRegistryObject SILVER = MaterialBuilder.builder("silver")
            .color(0xFFDCDCFF)
            .ingot()
            .fluid(false, 1200)
            .mass(70)
            .ore(1)
            .component(Components.SILVER)
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR)
            .textureSet(TextureSet.SHINY_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject LEAD = MaterialBuilder.builder("lead")
            .color(0xFF8C648C)
            .ingot()
            .fluid(false, 1200)
            .mass(65)
            .ore(1)
            .component(Components.LEAD)
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR)
            .textureSet(TextureSet.DULL_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject GALENA = MaterialBuilder.builder("galena")
            .color(0xFF643C64)
            .dust()
            .ore(1, SubItem.INGOT, LEAD)
            .textureSet(TextureSet.DULL_METAL)
            .mass(50)
            .addFlags(GenerationFlags.NO_BLOCK)
            .components(Components.LEAD, Components.SULFUR)
            .buildAndRegister();

    public static final MaterialRegistryObject BRONZE = MaterialBuilder.builder("bronze")
            .color(0xFFFF8000)
            .ingot()
            .fluid(false, 1200)
            .mass(80)
            .components(new ComponentStack(Components.COPPER, 3), new ComponentStack(Components.TIN))
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SMALL_GEAR)
            .toolStats(130, 2, 4.0F, 3.5F, 3.0F)
            .textureSet(TextureSet.DULL_METAL_HARSH)
            .buildAndRegister();

    public static final MaterialRegistryObject ELECTRUM = MaterialBuilder.builder("electrum")
            .color(0xFFFFFF64)
            .ingot()
            .fluid(false, 1200)
            .mass(140)
            .components(new ComponentStack(Components.GOLD), new ComponentStack(Components.SILVER))
            .addFlags(GenerationFlags.GENERATE_SCREW, GenerationFlags.GENERATE_GEAR, GenerationFlags.GENERATE_SMALL_GEAR, GenerationFlags.NO_MORTAR)
            .toolStats(64, 8, 6.5F, 2.1F, 3.0F)
            .textureSet(TextureSet.SHINY_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject CHROME = MaterialBuilder.builder("chrome")
            .color(0xFFEAC4D8)
            .ingot()
            .fluid(false, 1400)
            .mass(130)
            .textureSet(TextureSet.SHINY_METAL)
            .component(Components.CHROME)
            .addFlags(GenerationFlags.NO_MORTAR_GRINDING, GenerationFlags.NO_DUST_SMELTING)
            .toolStats(512, 5, 5.5F, 6.0F, 3.0F)
            .buildAndRegister();

    public static final MaterialRegistryObject CHROMITE = MaterialBuilder.builder("chromite")
            .color(0xFF23140F)
            .dust()
            .ore(0)
            .mass(60)
            .textureSet(TextureSet.METAL)
            .addFlags(GenerationFlags.NO_BLOCK)
            .components(new ComponentStack(Components.IRON), new ComponentStack(Components.CHROME, 2), new ComponentStack(Components.OXYGEN, 4))
            .buildAndRegister();

    public static final MaterialRegistryObject SILICON = MaterialBuilder.builder("silicon")
            .color(0xFF3C3C50)
            .ingot()
            .fluid(false, 1200)
            .mass(80)
            .component(Components.SILICON)
            .addFlags(GenerationFlags.GENERATE_FOIL)
            .textureSet(TextureSet.DULL_METAL_HARSH)
            .buildAndRegister();

    public static final MaterialRegistryObject SULFUR = MaterialBuilder.builder("sulfur")
            .color(0xFFC8C800)
            .mass(40)
            .dust()
            .ore(0)
            .component(Components.SULFUR)
            .addFlags(GenerationFlags.NO_BLOCK)
            .textureSet(TextureSet.DULL_METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject RAW_RUBBER = MaterialBuilder.builder("raw_rubber")
            .color(0xFFCCC789)
            .dust()
            .mass(40)
            .component(Components.RUBBER)
            .textureSet(TextureSet.DULL_METAL)
            .addFlags(GenerationFlags.NO_BLOCK)
            .buildAndRegister();

    public static final MaterialRegistryObject RUBBER = MaterialBuilder.builder("rubber")
            .color(0xFF161616)
            .soft()
            .fluid(false, 1200)
            .mass(32)
            .textureSet(TextureSet.SHINY_METAL)
            .addFlags(GenerationFlags.IS_SOFT, GenerationFlags.NO_VANILLA_TOOLS)
            .component(Components.RUBBER)
            .toolStats(1200, 2, 3.0F, 1.0F, 3.0F)
            .buildAndRegister();

    public static final MaterialRegistryObject STEAM = MaterialBuilder.builder("steam")
            .color(0xFFAAAAAA)
            .mass(0)
            .fluid(true, 1300)
            .component(Components.WATER)
            .textureSet(TextureSet.METAL)
            .buildAndRegister();

    public static final MaterialRegistryObject OXYGEN = MaterialBuilder.builder("oxygen")
            .color(0xFF4CC3FF)
            .mass(0)
            .component(Components.OXYGEN)
            .textureSet(TextureSet.METAL)
            .fluid(true, 80)
            .buildAndRegister();

    public static final MaterialRegistryObject HYDROGEN = MaterialBuilder.builder("hydrogen")
            .color(0xFF0000B5)
            .mass(0)
            .component(Components.HYDROGEN)
            .textureSet(TextureSet.METAL)
            .fluid(true, 20)
            .buildAndRegister();

    public static void register() {}
}
