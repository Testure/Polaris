package turing.mods.polaris.datagen;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Consumer;

import static turing.mods.polaris.material.SubItem.*;

public class ToolRecipes {
    private static final ItemPredicate HAMMER_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_HAMMER).build();
    private static final ItemPredicate FILE_PREDICATE = ItemPredicate.Builder.create().tag(PolarisTags.Items.CRAFTING_TOOLS_FILE).build();
    private static final InventoryChangeTrigger.Instance TOOLS = InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE, FILE_PREDICATE);

    public static void swordRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (material.hasSubItem(SWORD) && material.hasSubItem(SWORD_HEAD)) {
            RecipeUtil.swordRecipeHead(consumer, material);
            swordHeadRecipe(consumer, material);
        }
    }

    public static void axeRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (material.hasSubItem(AXE) && material.hasSubItem(AXE_HEAD)) {
            RecipeUtil.axeRecipeHead(consumer, material);
            axeHeadRecipe(consumer, material);
        }
    }

    public static void pickaxeRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (material.hasSubItem(PICKAXE) && material.hasSubItem(PICKAXE_HEAD)) {
            RecipeUtil.pickaxeRecipeHead(consumer, material);
            pickaxeHeadRecipe(consumer, material);
        }
    }

    public static void shovelRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (material.hasSubItem(SHOVEL) && material.hasSubItem(SHOVEL_HEAD)) {
            RecipeUtil.shovelRecipeHead(consumer, material);
            shovelHeadRecipe(consumer, material);
        }
    }

    public static void hoeRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (material.hasSubItem(HOE) && material.hasSubItem(HOE_HEAD)) {
            RecipeUtil.hoeRecipeHead(consumer, material);
            hoeHeadRecipe(consumer, material);
        }
    }

    public static void fileRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (material.hasSubItem(FILE) && material.hasSubItem(FILE_HEAD)) {
            RecipeUtil.fileRecipeHead(consumer, material);
            fileHeadRecipe(consumer, material);
        }
        RecipeUtil.fileRecipe(consumer, material);
    }

    public static void hammerRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (material.hasSubItem(HAMMER) && material.hasSubItem(HAMMER_HEAD)) {
            RecipeUtil.hammerRecipeHead(consumer, material);
            hammerHeadRecipe(consumer, material);
        }
        RecipeUtil.hammerRecipe(consumer, material);
    }

    public static void screwdriverRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (material.hasSubItem(SCREWDRIVER) && material.hasSubItem(SCREWDRIVER_HEAD)) {
            RecipeUtil.screwdriverRecipeHead(consumer, material);
            screwdriverHeadRecipe(consumer, material);
        }
        RecipeUtil.screwdriverRecipe(consumer, material);
    }

    public static void sawRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (material.hasSubItem(SAW) && material.hasSubItem(SAW_HEAD)) {
            RecipeUtil.sawRecipe(consumer, material);
            sawHeadRecipe(consumer, material);
        }
    }

    public static ItemStack getCraftedToolStack(MaterialRegistryObject material, SubItem tool) {
        if (!material.hasSubItem(tool)) throw new NullPointerException(String.format("material does not have tool %s!", tool.name()));
        if (material.get().getToolStats() == null || !tool.isTool()) return material.getItemFromSubItem(tool).getDefaultInstance();
        ItemStack stack = new ItemStack(material.getItemFromSubItem(tool));
        EnchantmentData[] enchantments = material.get().getToolStats().getDefaultEnchantments();

        if (enchantments != null) Arrays.stream(enchantments).forEach(enchantment -> stack.addEnchantment(enchantment.enchantment, enchantment.enchantmentLevel));

        return stack;
    }

    private static boolean canMakeHead(MaterialRegistryObject material) {
        return (material.get().getType().equals("gem") && material.hasSubItem(GEM)) || (material.hasSubItem(INGOT) && material.hasSubItem(PLATE));
    }

    private static boolean hasExistingSubItem(MaterialRegistryObject material, SubItem subItem) {
        return material.get().existingItems != null && material.get().existingItems.containsKey(subItem);
    }

    @Nullable
    private static Item getItem(MaterialRegistryObject material, SubItem subItem) {
        return material.hasSubItem(subItem) ? material.getItemFromSubItem(subItem) : null;
    }

    private static ITag.INamedTag<Item> tagOf(MaterialRegistryObject material, SubItem subItem) {
        return PolarisTags.Items.forge(subItem.name().toLowerCase() + "s/" + material.getName());
    }

    public static void hammerHeadRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!canMakeHead(material)) return;
        if (!material.hasSubItem(HAMMER_HEAD)) return;
        if (hasExistingSubItem(material, HAMMER_HEAD)) return;
        Item hammerHead = material.getItemFromSubItem(HAMMER_HEAD);
        Item gem = getItem(material, GEM);

        ShapedRecipeBuilder.shapedRecipe(hammerHead)
                .patternLine("pp ")
                .patternLine("pph")
                .patternLine("pp ")
                .key('p', gem != null ? tagOf(material, GEM) : tagOf(material, INGOT))
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .addCriterion("hammer", InventoryChangeTrigger.Instance.forItems(HAMMER_PREDICATE))
                .build(consumer, Polaris.modLoc(material.getName() + "_hammer_head"));
    }

    public static void screwdriverHeadRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!canMakeHead(material)) return;
        if (!material.hasSubItem(SCREWDRIVER_HEAD)) return;
        if (hasExistingSubItem(material, SCREWDRIVER_HEAD)) return;
        Item screwdriverHead = material.getItemFromSubItem(SCREWDRIVER_HEAD);

        ShapedRecipeBuilder.shapedRecipe(screwdriverHead)
                .patternLine("fr")
                .patternLine("rh")
                .key('r', tagOf(material, ROD))
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .addCriterion("tools", TOOLS)
                .build(consumer, Polaris.modLoc(material.getName() + "_screwdriver_head"));
    }

    public static void fileHeadRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!canMakeHead(material)) return;
        if (!material.hasSubItem(FILE_HEAD)) return;
        if (hasExistingSubItem(material, FILE_HEAD)) return;
        Item fileHead = material.getItemFromSubItem(FILE_HEAD);
        Item gem = getItem(material, GEM);

        ShapedRecipeBuilder.shapedRecipe(fileHead)
                .patternLine(" p ")
                .patternLine(" p ")
                .patternLine(" fh")
                .key('p', gem != null ? tagOf(material, GEM) : tagOf(material, INGOT))
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .addCriterion("tools", TOOLS)
                .build(consumer, Polaris.modLoc(material.getName() + "_file_head"));
    }

    public static void sawHeadRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!canMakeHead(material)) return;
        if (!material.hasSubItem(SAW_HEAD)) return;
        if (hasExistingSubItem(material, SAW_HEAD)) return;
        Item sawHead = material.getItemFromSubItem(SAW_HEAD);
        Item gem = getItem(material, GEM);

        ShapedRecipeBuilder.shapedRecipe(sawHead)
                .patternLine("pp")
                .patternLine("hf")
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('p', gem != null ? tagOf(material, GEM) : tagOf(material, PLATE))
                .addCriterion("tools", TOOLS)
                .build(consumer, Polaris.modLoc(material.getName() + "_saw_head"));
    }

    public static void swordHeadRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!canMakeHead(material)) return;
        if (!material.hasSubItem(SWORD_HEAD)) return;
        if (hasExistingSubItem(material, SWORD_HEAD)) return;
        Item swordHead = material.getItemFromSubItem(SWORD_HEAD);
        Item gem = getItem(material, GEM);

        ShapedRecipeBuilder.shapedRecipe(swordHead)
                .patternLine(" p ")
                .patternLine("hpf")
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('p', gem != null ? tagOf(material, GEM) : tagOf(material, PLATE))
                .addCriterion("tools", TOOLS)
                .build(consumer, Polaris.modLoc(material.getName() + "_sword_head"));
    }

    public static void axeHeadRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!canMakeHead(material)) return;
        if (!material.hasSubItem(AXE_HEAD)) return;
        if (hasExistingSubItem(material, AXE_HEAD)) return;
        Item axeHead = material.getItemFromSubItem(AXE_HEAD);
        Item gem = getItem(material, GEM);

        ShapedRecipeBuilder.shapedRecipe(axeHead)
                .patternLine("pih")
                .patternLine("p  ")
                .patternLine("f  ")
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('i', gem != null ? tagOf(material, GEM) : tagOf(material, INGOT))
                .key('p', gem != null ? tagOf(material, GEM) : tagOf(material, PLATE))
                .addCriterion("tools", TOOLS)
                .build(consumer, Polaris.modLoc(material.getName() + "_axe_head"));
    }

    public static void pickaxeHeadRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!canMakeHead(material)) return;
        if (!material.hasSubItem(PICKAXE_HEAD)) return;
        if (hasExistingSubItem(material, PICKAXE_HEAD)) return;
        Item pickaxeHead = material.getItemFromSubItem(PICKAXE_HEAD);
        Item gem = getItem(material, GEM);

        ShapedRecipeBuilder.shapedRecipe(pickaxeHead)
                .patternLine("pii")
                .patternLine("f h")
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('p', gem != null ? tagOf(material, GEM) : tagOf(material, PLATE))
                .key('i', gem != null ? tagOf(material, GEM) : tagOf(material, INGOT))
                .addCriterion("tools", TOOLS)
                .build(consumer, Polaris.modLoc(material.getName() + "_pickaxe_head"));
    }

    public static void shovelHeadRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!canMakeHead(material)) return;
        if (!material.hasSubItem(SHOVEL_HEAD)) return;
        if (hasExistingSubItem(material, SHOVEL_HEAD)) return;
        Item shovelHead = material.getItemFromSubItem(SHOVEL_HEAD);
        Item gem = getItem(material, GEM);

        ShapedRecipeBuilder.shapedRecipe(shovelHead)
                .patternLine("fph")
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('p', gem != null ? tagOf(material, GEM) : tagOf(material, PLATE))
                .addCriterion("tools", TOOLS)
                .build(consumer, Polaris.modLoc(material.getName() + "_shovel_head"));
    }

    public static void hoeHeadRecipe(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material) {
        if (!canMakeHead(material)) return;
        if (!material.hasSubItem(HOE_HEAD)) return;
        if (hasExistingSubItem(material, HOE_HEAD)) return;
        Item hoeHead = material.getItemFromSubItem(HOE_HEAD);
        Item gem = getItem(material, GEM);

        ShapedRecipeBuilder.shapedRecipe(hoeHead)
                .patternLine("pih")
                .patternLine("f  ")
                .key('f', PolarisTags.Items.CRAFTING_TOOLS_FILE)
                .key('h', PolarisTags.Items.CRAFTING_TOOLS_HAMMER)
                .key('p', gem != null ? tagOf(material, GEM) : tagOf(material, PLATE))
                .key('i', gem != null ? tagOf(material, GEM) : tagOf(material, INGOT))
                .addCriterion("tools", TOOLS)
                .build(consumer, Polaris.modLoc(material.getName() + "_hoe_head"));
    }
}
