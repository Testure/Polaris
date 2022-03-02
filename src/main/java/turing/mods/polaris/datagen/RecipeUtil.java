package turing.mods.polaris.datagen;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.Tags;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.material.GenerationFlags;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.BlockRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RecipeUtil {
    private static final ItemPredicate HAMMER_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_HAMMER).build();
    private static final ItemPredicate FILE_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_FILE).build();
    private static final ItemPredicate WRENCH_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_WRENCH).build();
    private static final ItemPredicate SAW_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_SAW).build();
    private static final ItemPredicate MORTAR_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_MORTAR).build();

    public static void magneticBlast(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem subItem, String name) {
        if (material.get().getMagneticOf() == null || !material.get().getMagneticOf().hasSubItem(subItem)) return;
        Item magneticItem = material.getItemFromSubItem(subItem);
        Item normalItem = material.get().getMagneticOf().getItemFromSubItem(subItem);

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(magneticItem), normalItem, 0.0F, 200)
                .addCriterion("obtain", InventoryChangeTrigger.Instance.forItems(magneticItem))
                .build(consumer, Polaris.modLoc(name + "_undo"));
    }

    public static void hammerRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.HAMMER) && !material.hasSubItem(SubItem.SOFT_HAMMER)) return;
        if (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.PLATE)) return;
        Item hammer = material.getItemFromSubItem(material.hasSubItem(SubItem.SOFT_HAMMER) ? SubItem.SOFT_HAMMER : SubItem.HAMMER);
        Item ingot = material.getItemFromSubItem(Objects.equals(material.get().getType(), "gem") ? SubItem.PLATE : SubItem.INGOT);

        ShapedRecipeBuilder.shapedRecipe(hammer)
                .patternLine(" ii")
                .patternLine("sii")
                .patternLine(" ii")
                .key('i', ingot)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .build(consumer, Polaris.modLoc(material.getName() + "_hammer"));
    }

    public static void fileRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.FILE) || !material.hasSubItem(SubItem.PLATE)) return;
        Item file = material.getItemFromSubItem(SubItem.FILE);
        Item plate = material.getItemFromSubItem(SubItem.PLATE);

        ShapedRecipeBuilder.shapedRecipe(file)
                .patternLine("i")
                .patternLine("i")
                .patternLine("s")
                .key('i', plate)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(plate))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_file"));
    }

    public static void wrenchRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.WRENCH)) return;
        if (!material.hasSubItem(SubItem.INGOT)) return;
        Item wrench = material.getItemFromSubItem(SubItem.WRENCH);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        ShapedRecipeBuilder.shapedRecipe(wrench)
                .patternLine("ihi")
                .patternLine("iii")
                .patternLine(" i ")
                .key('i', ingot)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_wrench"));
    }

    public static void screwdriverRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SCREWDRIVER)) return;
        if (!material.hasSubItem(SubItem.ROD)) return;
        Item screwdriver = material.getItemFromSubItem(SubItem.SCREWDRIVER);
        Item rod = material.getItemFromSubItem(SubItem.ROD);

        ShapedRecipeBuilder.shapedRecipe(screwdriver)
                .patternLine(" hr")
                .patternLine(" rf")
                .patternLine("s  ")
                .key('s', Tags.Items.RODS_WOODEN)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('r', rod)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(rod))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_screwdriver"));
    }

    public static void crowbarRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.CROWBAR)) return;
        if (!material.hasSubItem(SubItem.ROD)) return;
        Item crowbar = material.getItemFromSubItem(SubItem.CROWBAR);
        Item rod = material.getItemFromSubItem(SubItem.ROD);

        ShapedRecipeBuilder.shapedRecipe(crowbar)
                .patternLine("hbr")
                .patternLine("brb")
                .patternLine("rbf")
                .key('r', rod)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('b', Tags.Items.DYES_BLUE)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(rod))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_crowbar"));
    }

    public static void mortarRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.MORTAR) || !material.hasSubItem(SubItem.INGOT)) return;
        if (material.get().getFlags().contains(GenerationFlags.NO_MORTAR)) return;
        Item mortar = material.getItemFromSubItem(SubItem.MORTAR);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        ShapedRecipeBuilder.shapedRecipe(mortar)
                .patternLine(" i ")
                .patternLine("sis")
                .patternLine("sss")
                .key('i', ingot)
                .key('s', Tags.Items.STONE)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .build(consumer, Polaris.modLoc(material.getName() + "_mortar"));
    }

    public static void swordRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SWORD) || !material.hasSubItem(SubItem.INGOT)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.SWORD)))) return;
        Item sword = material.getItemFromSubItem(SubItem.SWORD);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        ShapedRecipeBuilder.shapedRecipe(sword)
                .patternLine("i")
                .patternLine("i")
                .patternLine("s")
                .key('i', ingot)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_sword"));
    }

    public static void shovelRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SHOVEL) || !material.hasSubItem(SubItem.INGOT)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.SHOVEL)))) return;
        Item shovel = material.getItemFromSubItem(SubItem.SHOVEL);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        ShapedRecipeBuilder.shapedRecipe(shovel)
                .patternLine("i")
                .patternLine("s")
                .patternLine("s")
                .key('i', ingot)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_shovel"));
    }

    public static void hoeRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.HOE) || !material.hasSubItem(SubItem.INGOT)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.HOE)))) return;
        Item hoe = material.getItemFromSubItem(SubItem.HOE);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        ShapedRecipeBuilder.shapedRecipe(hoe)
                .patternLine("ii")
                .patternLine(" s")
                .patternLine(" s")
                .key('i', ingot)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_hoe"));
    }

    public static void pickaxeRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.PICKAXE) || !material.hasSubItem(SubItem.INGOT)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.PICKAXE)))) return;
        Item pickaxe = material.getItemFromSubItem(SubItem.PICKAXE);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        ShapedRecipeBuilder.shapedRecipe(pickaxe)
                .patternLine("iii")
                .patternLine(" s ")
                .patternLine(" s ")
                .key('i', ingot)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_pickaxe"));
    }

    public static void axeRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.AXE) || !material.hasSubItem(SubItem.INGOT)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.AXE)))) return;
        Item axe = material.getItemFromSubItem(SubItem.AXE);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        ShapedRecipeBuilder.shapedRecipe(axe)
                .patternLine("ii ")
                .patternLine("is ")
                .patternLine(" s ")
                .key('i', ingot)
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_axe"));
    }

    public static void manualPlateRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.PLATE) || !material.hasSubItem(SubItem.INGOT)) return;
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);
        Item plate = material.getItemFromSubItem(SubItem.PLATE);

        ShapedRecipeBuilder.shapedRecipe(plate)
                .patternLine("h")
                .patternLine("i")
                .patternLine("i")
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('i', ingot)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_plate"));
    }

    public static void manualRodRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.INGOT) || !material.hasSubItem(SubItem.ROD)) return;
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);
        Item rod = material.getItemFromSubItem(SubItem.ROD);

        ShapedRecipeBuilder.shapedRecipe(rod)
                .patternLine("f ")
                .patternLine(" i")
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('i', ingot)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_rod"));
    }

    public static void manualGearRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.GEAR) || !material.hasSubItem(SubItem.ROD) || !material.hasSubItem(SubItem.PLATE)) return;
        Item plate = material.getItemFromSubItem(SubItem.PLATE);
        Item rod = material.getItemFromSubItem(SubItem.ROD);
        Item gear = material.getItemFromSubItem(SubItem.GEAR);

        ShapedRecipeBuilder.shapedRecipe(gear)
                .patternLine("rpr")
                .patternLine("pwp")
                .patternLine("rpr")
                .key('r', rod)
                .key('p', plate)
                .key('w', PolarisTags.Items.CRAFTING_TOOLS_WRENCH)
                .addCriterion("materials", InventoryChangeTrigger.Instance.forItems(plate, rod))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(WRENCH_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_gear"));
    }

    public static void manualBoltRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.BOLT) || !material.hasSubItem(SubItem.ROD)) return;
        Item rod = material.getItemFromSubItem(SubItem.ROD);
        Item bolt = material.getItemFromSubItem(SubItem.BOLT);

        ShapedRecipeBuilder.shapedRecipe(bolt, 2)
                .patternLine("s ")
                .patternLine(" r")
                .key('r', rod)
                .key('s', PolarisTags.Items.CRAFTING_TOOLS_SAW)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(rod))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(SAW_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_bolt"));
    }

    public static void manualScrewRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SCREW) || !material.hasSubItem(SubItem.BOLT)) return;
        Item screw = material.getItemFromSubItem(SubItem.SCREW);
        Item bolt = material.getItemFromSubItem(SubItem.BOLT);

        ShapedRecipeBuilder.shapedRecipe(screw)
                .patternLine("fb")
                .patternLine("b ")
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('b', bolt)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(bolt))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_screw"));
    }

    public static void manualGrindingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.DUST) || (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.GEM))) return;
        Item dust = material.getItemFromSubItem(SubItem.DUST);
        Item ingot = material.getItemFromSubItem(material.hasSubItem(SubItem.INGOT) ? SubItem.INGOT : SubItem.GEM);

        ShapedRecipeBuilder.shapedRecipe(dust)
                .patternLine("i")
                .patternLine("m")
                .key('i', ingot)
                .key('m', PolarisTags.Items.CRAFTING_TOOLS_MORTAR)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(MORTAR_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_dust_grinding"));
    }

    public static void dustSmeltingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.DUST) || !material.hasSubItem(SubItem.INGOT)) return;
        Item dust = material.getItemFromSubItem(SubItem.DUST);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(dust), ingot, 0.0F, 200)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(dust))
                .build(consumer, Polaris.modLoc(material.getName() + "_dust_smelting"));
    }

    public static void oreSmeltingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.ORE) || material.get().getOreStats() == null || (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.GEM))) return;
        if (material.get().getOreStats().getSmeltAmount() <= 0) return;
        Item ore = material.getItemFromSubItem(SubItem.ORE);
        Item ingot = material.getItemFromSubItem(material.hasSubItem(SubItem.INGOT) ? SubItem.INGOT : SubItem.GEM);
        Ingredient oreIngredient = Ingredient.fromItems(ore);
        ItemStack ingotStack = new ItemStack(ingot, Math.min(material.get().getOreStats().getSmeltAmount(), 64));
        ICriterionInstance criterion = InventoryChangeTrigger.Instance.forItems(ore);

        //TODO crushed ore
        CookingRecipeItemStackBuilder.smeltingRecipe(oreIngredient, ingotStack, 1.0F, 200)
                .addCriterion("ore", criterion)
                .build(consumer, Polaris.modLoc(material.getName() + "_ore_smelting"));
        CookingRecipeItemStackBuilder.blastingRecipe(oreIngredient, ingotStack, 1.0F, material.hasSubItem(SubItem.INGOT) ? 100 : 150)
                .addCriterion("ore", criterion)
                .build(consumer, Polaris.modLoc(material.getName() + "_ore_blasting"));
    }

    public static void manualPolarizingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem subItem) {
        if (material.get().getMagneticOf() == null || !material.hasSubItem(subItem) || !material.get().getMagneticOf().hasSubItem(subItem)) return;
        Item stable = material.get().getMagneticOf().getItemFromSubItem(subItem);
        Item magnetic = material.getItemFromSubItem(subItem);

        ShapelessRecipeBuilder.shapelessRecipe(magnetic)
                .addIngredient(Items.REDSTONE, 4)
                .addIngredient(stable)
                .addCriterion("redstone", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE))
                .build(consumer, material.getName() + "_" + subItem.name().toLowerCase() + "_polarizing");
    }

    public static void compressingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem from, SubItem to, String name) {
        if (!material.hasSubItem(from) || !material.hasSubItem(to)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(to)))) return;
        Item fromItem = material.getItemFromSubItem(from);
        Item toItem = material.getItemFromSubItem(to);

        ShapedRecipeBuilder.shapedRecipe(toItem)
                .patternLine("iii")
                .patternLine("iii")
                .patternLine("iii")
                .key('i', fromItem)
                .addCriterion("from", InventoryChangeTrigger.Instance.forItems(fromItem))
                .addCriterion("to", InventoryChangeTrigger.Instance.forItems(toItem))
                .build(consumer, Polaris.modLoc(name));
    }

    public static void compressingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem from, SubItem to, boolean useFour, String name) {
        if (!useFour) { compressingRecipe(consumer, material, from, to, name); return; }
        Item fromItem = material.getItemFromSubItem(from);
        Item toItem = material.getItemFromSubItem(to);

        ShapedRecipeBuilder.shapedRecipe(toItem)
                .patternLine("ii")
                .patternLine("ii")
                .key('i', fromItem)
                .addCriterion("from", InventoryChangeTrigger.Instance.forItems(fromItem))
                .addCriterion("to", InventoryChangeTrigger.Instance.forItems(toItem))
                .build(consumer, Polaris.modLoc(name));
    }

    public static void decompressingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem from, SubItem to, int amount, boolean isSecondary, String name) {
        if (!material.hasSubItem(from) || !material.hasSubItem(to)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(to)))) return;
        name = isSecondary ? name + "_secondary" : name;
        Item fromItem = material.getItemFromSubItem(from);
        Item toItem = material.getItemFromSubItem(to);

        ShapedRecipeBuilder.shapedRecipe(toItem, MathHelper.clamp(isSecondary ? (amount * 2) : amount, 1, 64))
                .patternLine("i" + (isSecondary ? "i" : " "))
                .key('i', fromItem)
                .addCriterion("from", InventoryChangeTrigger.Instance.forItems(fromItem))
                .addCriterion("to", InventoryChangeTrigger.Instance.forItems(toItem))
                .build(consumer, Polaris.modLoc(name));
    }

    public static void casingRecipe(Consumer<IFinishedRecipe> consumer, Block casing, MaterialRegistryObject casingMaterial) {
        if (!casingMaterial.hasSubItem(SubItem.PLATE)) return;
        Item casingItem = casing.asItem();
        Item plate = casingMaterial.getItemFromSubItem(SubItem.PLATE);

        ShapedRecipeBuilder.shapedRecipe(casingItem)
                .patternLine("iii")
                .patternLine("iwi")
                .patternLine("iii")
                .key('w', PolarisTags.Items.CRAFTING_TOOLS_WRENCH)
                .key('i', plate)
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(WRENCH_PREDICATE))
                .build(consumer, Polaris.modLoc(casingItem.getRegistryName().getPath()));
    }

    public static void hullRecipe(Consumer<IFinishedRecipe> consumer, Block hull, int tier, MaterialRegistryObject hullMaterial) {
        if (!hullMaterial.hasSubItem(SubItem.CABLE_SINGLE)) return;
        Item hullItem = hull.asItem();
        Item cable = hullMaterial.getItemFromSubItem(SubItem.CABLE_SINGLE);

        ShapedRecipeBuilder.shapedRecipe(hullItem)
                .patternLine("cCc")
                .key('c', cable)
                .key('C', BlockRegistry.CASINGS[tier].get().asItem())
                .addCriterion("casing", InventoryChangeTrigger.Instance.forItems(BlockRegistry.CASINGS[tier].get().asItem()))
                .build(consumer, Polaris.modLoc(hullItem.getRegistryName().getPath()));
    }
}
