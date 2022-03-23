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

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RecipeUtil {
    private static final ItemPredicate HAMMER_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_HAMMER).build();
    private static final ItemPredicate FILE_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_FILE).build();
    private static final ItemPredicate WRENCH_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_WRENCH).build();
    private static final ItemPredicate SAW_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_SAW).build();
    private static final ItemPredicate MORTAR_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_MORTAR).build();
    private static final ItemPredicate SCREWDRIVER_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_SCREWDRIVER).build();

    public static void magneticBlast(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem subItem, String name) {
        if (material.get().getMagneticOf() == null || !material.get().getMagneticOf().hasSubItem(subItem)) return;
        Item magneticItem = material.getItemFromSubItem(subItem);
        Item normalItem = material.get().getMagneticOf().getItemFromSubItem(subItem);

        CookingRecipeBuilder.blastingRecipe(Ingredient.fromTag(PolarisTags.Items.forge(subItem.name().toLowerCase() + "s/" + material.getName())), normalItem, 0.0F, 200)
                .addCriterion("obtain", InventoryChangeTrigger.Instance.forItems(magneticItem))
                .build(consumer, Polaris.modLoc(name + "_undo"));
    }

    public static void wireCutterRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.WIRE_CUTTER) || !material.hasSubItem(SubItem.ROD) || !material.hasSubItem(SubItem.SCREW) || !material.hasSubItem(SubItem.PLATE)) return;
        ItemStack wireCutter = ToolRecipes.getCraftedToolStack(material, SubItem.WIRE_CUTTER);

        ShapedRecipeItemStackBuilder.shapedRecipe(wireCutter)
                .patternLine("pfp")
                .patternLine("hpS")
                .patternLine("rsr")
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('S', PolarisTags.Items.CRAFTING_TOOLS_SCREWDRIVER)
                .key('p', PolarisTags.Items.forge("plates/" + material.getName()))
                .key('s', PolarisTags.Items.forge("screws/" + material.getName()))
                .key('r', PolarisTags.Items.forge("rods/" + material.getName()))
                .addCriterion("tools", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE, FILE_PREDICATE, SCREWDRIVER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_wire_cutter"));
    }

    public static void hammerRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.HAMMER) && !material.hasSubItem(SubItem.SOFT_HAMMER)) return;
        if (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.PLATE)) return;
        ItemStack hammer = ToolRecipes.getCraftedToolStack(material, material.hasSubItem(SubItem.SOFT_HAMMER) ? SubItem.SOFT_HAMMER : SubItem.HAMMER);
        Item ingot = material.getItemFromSubItem(Objects.equals(material.get().getType(), "gem") ? SubItem.PLATE : SubItem.INGOT);

        ShapedRecipeItemStackBuilder.shapedRecipe(hammer)
                .patternLine(" ii")
                .patternLine("sii")
                .patternLine(" ii")
                .key('i', PolarisTags.Items.forge((Objects.equals(material.get().getType(), "gem") ? "plate" : "ingot") + "s/" + material.getName()))
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .build(consumer, Polaris.modLoc(material.getName() + "_hammer"));
    }

    public static void hammerRecipeHead(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.HAMMER) && material.hasSubItem(SubItem.SOFT_HAMMER)) { hammerRecipe(consumer, material); return; }
        if (!material.hasSubItem(SubItem.HAMMER_HEAD)) return;
        ItemStack hammer = ToolRecipes.getCraftedToolStack(material, SubItem.HAMMER);
        Item hammerHead = material.getItemFromSubItem(SubItem.HAMMER_HEAD);

        ShapelessRecipeItemStackBuilder.shapelessRecipe(hammer)
                .addIngredient(hammerHead)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addCriterion("head", InventoryChangeTrigger.Instance.forItems(hammerHead))
                .build(consumer, Polaris.modLoc(material.getName() + "_hammer_from_head"));
    }

    public static void fileRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.FILE) || !material.hasSubItem(SubItem.PLATE)) return;
        ItemStack file = ToolRecipes.getCraftedToolStack(material, SubItem.FILE);
        Item plate = material.getItemFromSubItem(SubItem.PLATE);

        ShapedRecipeItemStackBuilder.shapedRecipe(file)
                .patternLine("i")
                .patternLine("i")
                .patternLine("s")
                .key('i', PolarisTags.Items.forge("plates/" + material.getName()))
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(plate))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_file"));
    }

    public static void fileRecipeHead(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.FILE) || !material.hasSubItem(SubItem.FILE_HEAD)) return;
        ItemStack file = ToolRecipes.getCraftedToolStack(material, SubItem.FILE);
        Item fileHead = material.getItemFromSubItem(SubItem.FILE_HEAD);

        ShapelessRecipeItemStackBuilder.shapelessRecipe(file)
                .addIngredient(fileHead)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_file_from_head"));
    }

    public static void sawRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SAW) || !material.hasSubItem(SubItem.SAW_HEAD)) return;
        ItemStack saw = ToolRecipes.getCraftedToolStack(material, SubItem.SAW);
        Item sawHead = material.getItemFromSubItem(SubItem.SAW_HEAD);

        ShapelessRecipeItemStackBuilder.shapelessRecipe(saw)
                .addIngredient(sawHead)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_saw"));
    }

    public static void wrenchRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.WRENCH)) return;
        if (!material.hasSubItem(SubItem.INGOT)) return;
        ItemStack wrench = ToolRecipes.getCraftedToolStack(material, SubItem.WRENCH);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        ShapedRecipeItemStackBuilder.shapedRecipe(wrench)
                .patternLine("ihi")
                .patternLine("iii")
                .patternLine(" i ")
                .key('i', PolarisTags.Items.forge("ingots/" + material.getName()))
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_wrench"));
    }

    public static void screwdriverRecipeHead(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SCREWDRIVER) || !material.hasSubItem(SubItem.SCREWDRIVER_HEAD)) return;
        if (!material.hasSubItem(SubItem.ROD)) return;
        ItemStack screwdriver = ToolRecipes.getCraftedToolStack(material, SubItem.SCREWDRIVER);
        Item screwdriverHead = material.getItemFromSubItem(SubItem.SCREWDRIVER_HEAD);
        Item rod = material.getItemFromSubItem(SubItem.ROD);

        ShapelessRecipeItemStackBuilder.shapelessRecipe(screwdriver)
                .addIngredient(screwdriverHead)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(rod))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_screwdriver_from_head"));
    }

    public static void screwdriverRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SCREWDRIVER)) return;
        if (!material.hasSubItem(SubItem.ROD)) return;
        ItemStack screwdriver = ToolRecipes.getCraftedToolStack(material, SubItem.SCREWDRIVER);
        Item rod = material.getItemFromSubItem(SubItem.ROD);

        ShapedRecipeItemStackBuilder.shapedRecipe(screwdriver)
                .patternLine(" hr")
                .patternLine(" rf")
                .patternLine("s  ")
                .key('s', Tags.Items.RODS_WOODEN)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('r', PolarisTags.Items.forge("rods/" + material.getName()))
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(rod))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_screwdriver"));
    }

    public static void crowbarRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.CROWBAR)) return;
        if (!material.hasSubItem(SubItem.ROD)) return;
        ItemStack crowbar = ToolRecipes.getCraftedToolStack(material, SubItem.CROWBAR);
        Item rod = material.getItemFromSubItem(SubItem.ROD);

        ShapedRecipeItemStackBuilder.shapedRecipe(crowbar)
                .patternLine("hbr")
                .patternLine("brb")
                .patternLine("rbf")
                .key('r', PolarisTags.Items.forge("rods/" + material.getName()))
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('b', Tags.Items.DYES_BLUE)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(rod))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_crowbar"));
    }

    public static void mortarRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.MORTAR) || (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.GEM))) return;
        if (material.get().getFlags().contains(GenerationFlags.NO_MORTAR)) return;
        ItemStack mortar = ToolRecipes.getCraftedToolStack(material, SubItem.MORTAR);
        Item ingot = material.hasSubItem(SubItem.INGOT) ? material.getItemFromSubItem(SubItem.INGOT) : material.getItemFromSubItem(SubItem.GEM);

        ShapedRecipeItemStackBuilder.shapedRecipe(mortar)
                .patternLine(" i ")
                .patternLine("sis")
                .patternLine("sss")
                .key('i', PolarisTags.Items.forge((material.hasSubItem(SubItem.GEM) ? "gem" : "ingot") + "s/" + material.getName()))
                .key('s', Tags.Items.STONE)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .build(consumer, Polaris.modLoc(material.getName() + "_mortar"));
    }

    public static void swordRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SWORD) || (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.GEM))) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.SWORD)))) return;
        ItemStack sword = ToolRecipes.getCraftedToolStack(material, SubItem.SWORD);

        ShapedRecipeItemStackBuilder.shapedRecipe(sword)
                .patternLine("i")
                .patternLine("i")
                .patternLine("s")
                .key('i', PolarisTags.Items.forge((material.hasSubItem(SubItem.GEM) ? "gem" : "ingot") + "s/" + material.getName()))
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_sword"));
    }

    public static void swordRecipeHead(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SWORD) || !material.hasSubItem(SubItem.SWORD_HEAD)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.SWORD_HEAD)))) return;
        ItemStack sword = ToolRecipes.getCraftedToolStack(material, SubItem.SWORD);
        Item swordHead = material.getItemFromSubItem(SubItem.SWORD_HEAD);

        ShapelessRecipeItemStackBuilder.shapelessRecipe(sword)
                .addIngredient(swordHead)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_sword_from_head"));
    }

    public static void shovelRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SHOVEL) || (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.GEM))) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.SHOVEL)))) return;
        ItemStack shovel = ToolRecipes.getCraftedToolStack(material, SubItem.SHOVEL);

        ShapedRecipeItemStackBuilder.shapedRecipe(shovel)
                .patternLine("i")
                .patternLine("s")
                .patternLine("s")
                .key('i', PolarisTags.Items.forge((material.hasSubItem(SubItem.GEM) ? "gem" : "ingot") + "s/" + material.getName()))
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_shovel"));
    }

    public static void shovelRecipeHead(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.SHOVEL) || !material.hasSubItem(SubItem.SHOVEL_HEAD)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.SHOVEL_HEAD)))) return;
        ItemStack shovel = ToolRecipes.getCraftedToolStack(material, SubItem.SHOVEL);
        Item shovelHead = material.getItemFromSubItem(SubItem.SHOVEL_HEAD);

        ShapelessRecipeItemStackBuilder.shapelessRecipe(shovel)
                .addIngredient(shovelHead)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_shovel_from_head"));
    }

    public static void hoeRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.HOE) || (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.GEM))) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.HOE)))) return;
        ItemStack hoe = ToolRecipes.getCraftedToolStack(material, SubItem.HOE);

        ShapedRecipeItemStackBuilder.shapedRecipe(hoe)
                .patternLine("ii")
                .patternLine(" s")
                .patternLine(" s")
                .key('i', PolarisTags.Items.forge((material.hasSubItem(SubItem.GEM) ? "gem" : "ingot") + "s/" + material.getName()))
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_hoe"));
    }

    public static void hoeRecipeHead(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.HOE) || !material.hasSubItem(SubItem.HOE_HEAD)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.HOE_HEAD)))) return;
        ItemStack hoe = ToolRecipes.getCraftedToolStack(material, SubItem.HOE);
        Item hoeHead = material.getItemFromSubItem(SubItem.HOE_HEAD);

        ShapelessRecipeItemStackBuilder.shapelessRecipe(hoe)
                .addIngredient(hoeHead)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_hoe_from_head"));
    }

    public static void pickaxeRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.PICKAXE) || (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.GEM))) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.PICKAXE)))) return;
        ItemStack pickaxe = ToolRecipes.getCraftedToolStack(material, SubItem.PICKAXE);

        ShapedRecipeItemStackBuilder.shapedRecipe(pickaxe)
                .patternLine("iii")
                .patternLine(" s ")
                .patternLine(" s ")
                .key('i', PolarisTags.Items.forge((material.hasSubItem(SubItem.GEM) ? "gem" : "ingot") + "s/" + material.getName()))
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_pickaxe"));
    }

    public static void pickaxeRecipeHead(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.PICKAXE) || !material.hasSubItem(SubItem.PICKAXE_HEAD)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.PICKAXE_HEAD)))) return;
        ItemStack pickaxe = ToolRecipes.getCraftedToolStack(material, SubItem.PICKAXE);
        Item pickaxeHead = material.getItemFromSubItem(SubItem.PICKAXE_HEAD);

        ShapelessRecipeItemStackBuilder.shapelessRecipe(pickaxe)
                .addIngredient(pickaxeHead)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_pickaxe_from_head"));
    }

    public static void axeRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.AXE) || (!material.hasSubItem(SubItem.INGOT) && !material.hasSubItem(SubItem.GEM))) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.AXE)))) return;
        ItemStack axe = ToolRecipes.getCraftedToolStack(material, SubItem.AXE);

        ShapedRecipeItemStackBuilder.shapedRecipe(axe)
                .patternLine("ii ")
                .patternLine("is ")
                .patternLine(" s ")
                .key('i', PolarisTags.Items.forge((material.hasSubItem(SubItem.GEM) ? "gem" : "ingot") + "s/" + material.getName()))
                .key('s', Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_axe"));
    }

    public static void axeRecipeHead(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.AXE) || !material.hasSubItem(SubItem.AXE_HEAD)) return;
        if (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(SubItem.AXE_HEAD)))) return;
        ItemStack axe = ToolRecipes.getCraftedToolStack(material, SubItem.AXE);
        Item axeHead = material.getItemFromSubItem(SubItem.AXE_HEAD);

        ShapelessRecipeItemStackBuilder.shapelessRecipe(axe)
                .addIngredient(axeHead)
                .addIngredient(Tags.Items.RODS_WOODEN)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .addCriterion("file", InventoryChangeTrigger.Instance.forItems(FILE_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_axe_from_head"));
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
                .key('i', PolarisTags.Items.forge("ingots/" + material.getName()))
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
                .key('i', PolarisTags.Items.forge("ingots/" + material.getName()))
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
                .key('r', PolarisTags.Items.forge("rods/" + material.getName()))
                .key('p', PolarisTags.Items.forge("plates/" + material.getName()))
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
                .key('r', PolarisTags.Items.forge("rods/" + material.getName()))
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
                .key('b', PolarisTags.Items.forge("bolts/" + material.getName()))
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
                .key('i', PolarisTags.Items.forge((material.hasSubItem(SubItem.INGOT) ? "ingot" : "gem") + "s/" + material.getName()))
                .key('m', PolarisTags.Items.CRAFTING_TOOLS_MORTAR)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(ingot))
                .addCriterion("tool", InventoryChangeTrigger.Instance.forItems(MORTAR_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_dust_grinding"));
    }

    public static void dustSmeltingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.DUST) || !material.hasSubItem(SubItem.INGOT)) return;
        Item dust = material.getItemFromSubItem(SubItem.DUST);
        Item ingot = material.getItemFromSubItem(SubItem.INGOT);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromTag(PolarisTags.Items.forge("dusts/" + material.getName())), ingot, 0.0F, 200)
                .addCriterion("material", InventoryChangeTrigger.Instance.forItems(dust))
                .build(consumer, Polaris.modLoc(material.getName() + "_dust_smelting"));
    }

    public static void oreSmeltingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.ORE) || material.get().getOreStats() == null) return;
        if (material.get().getOreStats().getSmeltAmount() <= 0) return;
        SubItem smeltItem = material.get().getOreStats().getCustomSubItem();
        MaterialRegistryObject customMaterial = material.get().getOreStats().getCustomMaterial();
        MaterialRegistryObject smeltMaterial = customMaterial != null ? customMaterial : material;
        Item ore = material.getItemFromSubItem(SubItem.ORE);
        Item ingot = smeltMaterial.getItemFromSubItem(smeltItem != null && smeltMaterial.hasSubItem(smeltItem) ? smeltItem : (smeltMaterial.hasSubItem(SubItem.INGOT) ? SubItem.INGOT : SubItem.GEM));
        Ingredient oreIngredient = Ingredient.fromTag(PolarisTags.Items.forge("ores/" + material.getName()));
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
        Item magnetic = material.getItemFromSubItem(subItem);

        ShapelessRecipeBuilder.shapelessRecipe(magnetic)
                .addIngredient(Items.REDSTONE, 4)
                .addIngredient(Ingredient.fromTag(PolarisTags.Items.forge(subItem.name().toLowerCase() + "s/" + material.get().getMagneticOf().getName())))
                .addCriterion("redstone", InventoryChangeTrigger.Instance.forItems(Items.REDSTONE))
                .build(consumer, material.getName() + "_" + subItem.name().toLowerCase() + "_polarizing");
    }

    public static void compressingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem from, SubItem to, String name, boolean checkForExisting) {
        if (!material.hasSubItem(from) || !material.hasSubItem(to)) return;
        if (checkForExisting && (material.get().existingItems != null && (material.get().existingItems.containsValue(material.getItemFromSubItem(to))))) return;
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

    public static void compressingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem from, SubItem to, String name) {
        compressingRecipe(consumer, material, from, to, name, true);
    }

    public static void compressingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem from, SubItem to, boolean useFour, String name, boolean checkForExisting) {
        if (!useFour) { compressingRecipe(consumer, material, from, to, name, checkForExisting); return; }
        if (checkForExisting && (material.get().existingItems != null && material.get().existingItems.containsValue(material.getItemFromSubItem(to)))) return;
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

    public static void compressingRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, SubItem from, SubItem to, boolean useFour, String name) {
        compressingRecipe(consumer, material, from, to, useFour, name, true);
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

        ShapedRecipeBuilder.shapedRecipe(casingItem)
                .patternLine("iii")
                .patternLine("iwi")
                .patternLine("iii")
                .key('w', PolarisTags.Items.CRAFTING_TOOLS_WRENCH)
                .key('i', PolarisTags.Items.forge("plates/" + casingMaterial.getName()))
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
