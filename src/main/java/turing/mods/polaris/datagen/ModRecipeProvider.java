package turing.mods.polaris.datagen;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.CasingBlock;
import turing.mods.polaris.block.HullBlock;
import turing.mods.polaris.material.GenerationFlags;
import turing.mods.polaris.material.Materials;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.BlockRegistry;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;
import turing.mods.polaris.util.Consumer2;
import turing.mods.polaris.util.Consumer4;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        magneticRecipes(consumer);
        compactingRecipes(consumer);
        toolRecipes(consumer);
        manualWorkingRecipes(consumer);
        smeltingRecipes(consumer);

        manualDustMixing(consumer);
        casingRecipes(consumer);
        hullRecipes(consumer);
        mortarRecipes(consumer);
    }

    private void callBoth(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, Consumer2<Consumer<IFinishedRecipe>, MaterialRegistryObject> func1, Consumer2<Consumer<IFinishedRecipe>, MaterialRegistryObject> func2) {
        func1.accept(consumer, material);
        func2.accept(consumer, material);
    }

    @SafeVarargs
    private final void callAll(Consumer<IFinishedRecipe> consumer, MaterialRegistryObject material, Consumer2<Consumer<IFinishedRecipe>, MaterialRegistryObject>... functions) {
        for (Consumer2<Consumer<IFinishedRecipe>, MaterialRegistryObject> func : functions) func.accept(consumer, material);
    }

    private void hullRecipes(Consumer<IFinishedRecipe> consumer) {
        for (int i = 0; i < BlockRegistry.HULLS.length; i++) {
            HullBlock hull = BlockRegistry.HULLS[i].get();
            if (hull.getHullMaterial() != null)
                RecipeUtil.hullRecipe(consumer, hull, i, hull.getHullMaterial());
        }
    }

    private void casingRecipes(Consumer<IFinishedRecipe> consumer) {
        for (RegistryObject<CasingBlock> casingReg : BlockRegistry.CASINGS) {
            CasingBlock casing = casingReg.get();
            if (casing.getCasingMaterial() != null)
                RecipeUtil.casingRecipe(consumer, casing, casing.getCasingMaterial());
        }
    }

    private void mortarRecipes(Consumer<IFinishedRecipe> consumer) {
        Consumer4<Item, Item, Integer, ResourceLocation> mortarRecipe = (input, output, outputCount, name) -> ShapedRecipeBuilder.shapedRecipe(output, outputCount)
                .patternLine("#")
                .patternLine("m")
                .key('m', PolarisTags.Items.CRAFTING_TOOLS_MORTAR)
                .key('#', input)
                .addCriterion("obtain", InventoryChangeTrigger.Instance.forItems(input))
                .build(consumer, name);

        //bone meal
        mortarRecipe.accept(Items.BONE, Items.BONE_MEAL, 6, Polaris.modLoc("bone_meal_grinding"));

        //cobble -> gravel -> sand
        mortarRecipe.accept(Items.COBBLESTONE, Items.GRAVEL, 1, Polaris.modLoc("cobblestone_grinding"));
        mortarRecipe.accept(Items.GRAVEL, Items.SAND, 1, Polaris.modLoc("gravel_grinding"));
    }

    private void manualDustMixing(Consumer<IFinishedRecipe> consumer) {
        //bronze dust
        ShapelessRecipeBuilder.shapelessRecipe(Materials.BRONZE.getItemFromSubItem(SubItem.DUST))
                .addIngredient(Materials.COPPER.getItemFromSubItem(SubItem.DUST), 3)
                .addIngredient(Materials.TIN.getItemFromSubItem(SubItem.DUST))
                .addCriterion("copper", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(PolarisTags.Items.forge("dusts/copper")).build()))
                .addCriterion("tin", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(PolarisTags.Items.forge("dusts/tin")).build()))
                .build(consumer, Polaris.modLoc("bronze_dust_mixing"));

        //electrum dust
        ShapelessRecipeBuilder.shapelessRecipe(Materials.ELECTRUM.getItemFromSubItem(SubItem.DUST))
                .addIngredient(Materials.GOLD.getItemFromSubItem(SubItem.DUST))
                .addIngredient(Materials.SILVER.getItemFromSubItem(SubItem.DUST))
                .addCriterion("gold", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(PolarisTags.Items.forge("dusts/gold")).build()))
                .addCriterion("silver", InventoryChangeTrigger.Instance.forItems(ItemPredicate.Builder.create().tag(PolarisTags.Items.forge("dusts/silver")).build()))
                .build(consumer, Polaris.modLoc("electrum_dust_mixing"));
    }

    private void toolRecipes(Consumer<IFinishedRecipe> consumer) {
        MaterialRegistry.getMaterials().values().forEach(material -> {
            if (material.get().getToolStats() != null) {
                if (!material.get().getFlags().contains(GenerationFlags.NO_MORTAR) && material.hasSubItem(SubItem.MORTAR)) RecipeUtil.mortarRecipe(consumer, material);
                ToolRecipes.fileRecipe(consumer, material);
                if (material.hasSubItem(SubItem.HAMMER)) ToolRecipes.hammerRecipe(consumer, material);
                    else if (material.hasSubItem(SubItem.SOFT_HAMMER)) RecipeUtil.hammerRecipe(consumer, material);
                if (material.hasSubItem(SubItem.WRENCH)) RecipeUtil.wrenchRecipe(consumer, material);
                if (material.hasSubItem(SubItem.CROWBAR)) RecipeUtil.crowbarRecipe(consumer, material);
                ToolRecipes.screwdriverRecipe(consumer, material);
                ToolRecipes.sawRecipe(consumer, material);
                ToolRecipes.wireCutterRecipe(consumer, material);
                ToolRecipes.swordRecipe(consumer, material);
                ToolRecipes.pickaxeRecipe(consumer, material);
                ToolRecipes.shovelRecipe(consumer, material);
                ToolRecipes.axeRecipe(consumer, material);
                ToolRecipes.hoeRecipe(consumer, material);
            }
        });
        RecipeUtil.hoeRecipe(consumer, Materials.FLINT);
        RecipeUtil.axeRecipe(consumer, Materials.FLINT);
        RecipeUtil.pickaxeRecipe(consumer, Materials.FLINT);
        RecipeUtil.swordRecipe(consumer, Materials.FLINT);
        RecipeUtil.shovelRecipe(consumer, Materials.FLINT);
    }

    private void smeltingRecipes(Consumer<IFinishedRecipe> consumer) {
        MaterialRegistry.getMaterials().values().forEach(material -> {
            if (material.hasSubItem(SubItem.DUST) && !material.get().getFlags().contains(GenerationFlags.NO_DUST_SMELTING)) RecipeUtil.dustSmeltingRecipe(consumer, material);
            if (material.hasSubItem(SubItem.ORE) && !material.get().getFlags().contains(GenerationFlags.NO_ORE_SMELTING)) RecipeUtil.oreSmeltingRecipe(consumer, material);
        });
    }

    private void magneticRecipes(Consumer<IFinishedRecipe> consumer) {
        MaterialRegistry.getMaterials().values().forEach(material -> {
            if (material.get().getMagneticOf() != null) {
                for (SubItem subItem : material.get().getSubItems())
                    if (material.get().getMagneticOf().hasSubItem(subItem)) {
                        RecipeUtil.magneticBlast(consumer, material, subItem, material.getName() + "_" + subItem.name().toLowerCase());
                        if (!material.get().getFlags().contains(GenerationFlags.NO_MANUAL_POLARIZATION)) RecipeUtil.manualPolarizingRecipe(consumer, material, subItem);
                    }
            }
        });
    }

    private void manualWorkingRecipes(Consumer<IFinishedRecipe> consumer) {
        MaterialRegistry.getMaterials().values().forEach(material -> {
            if (material.hasSubItem(SubItem.PLATE)) RecipeUtil.manualPlateRecipe(consumer, material);
            if (material.hasSubItem(SubItem.BOLT)) RecipeUtil.manualBoltRecipe(consumer, material);
            if (material.hasSubItem(SubItem.ROD)) RecipeUtil.manualRodRecipe(consumer, material);
            if (material.hasSubItem(SubItem.SCREW)) RecipeUtil.manualScrewRecipe(consumer, material);
            if (material.hasSubItem(SubItem.GEAR)) RecipeUtil.manualGearRecipe(consumer, material);
            if (material.hasSubItem(SubItem.DUST) && !material.get().getFlags().contains(GenerationFlags.NO_MORTAR_GRINDING)) RecipeUtil.manualGrindingRecipe(consumer, material);
        });
    }

    private void compactingRecipes(Consumer<IFinishedRecipe> consumer) {
        MaterialRegistry.getMaterials().values().forEach(material -> {
            if (!material.get().getFlags().contains(GenerationFlags.NO_COMPRESSION)) {
                if (material.hasSubItem(SubItem.BLOCK) && (material.hasSubItem(SubItem.INGOT) || material.hasSubItem(SubItem.GEM))) {
                    RecipeUtil.compressingRecipe(consumer, material, material.hasSubItem(SubItem.INGOT) ? SubItem.INGOT : SubItem.GEM, SubItem.BLOCK, material.get().getFlags().contains(GenerationFlags.QUAD_COMPACTING), material.getName() + "_ingot_compacting");
                    RecipeUtil.decompressingRecipe(consumer, material, SubItem.BLOCK, material.hasSubItem(SubItem.INGOT) ? SubItem.INGOT : SubItem.GEM, 9, false, material.getName() + "_block_decompacting");
                } else if (material.hasSubItem(SubItem.BLOCK) && material.hasSubItem(SubItem.DUST)) {
                    int amount = material.get().getFlags().contains(GenerationFlags.QUAD_COMPACTING) ? 4 : 9;
                    RecipeUtil.compressingRecipe(consumer, material, SubItem.DUST, SubItem.BLOCK, material.get().getFlags().contains(GenerationFlags.QUAD_COMPACTING), material.getName() + "_dust_block_compacting");
                    RecipeUtil.decompressingRecipe(consumer, material, SubItem.BLOCK, SubItem.DUST, amount, false, material.getName() + "_block_decompacting");
                }
                if (material.hasSubItem(SubItem.NUGGET) && material.hasSubItem(SubItem.INGOT)) {
                    RecipeUtil.compressingRecipe(consumer, material, SubItem.NUGGET, SubItem.INGOT, material.getName() + "_nugget_compacting");
                    RecipeUtil.decompressingRecipe(consumer, material, SubItem.INGOT, SubItem.NUGGET, 9, false,material.getName() + "_ingot_decompacting");
                }
                if (material.hasSubItem(SubItem.DUST)) {
                    if (material.hasSubItem(SubItem.TINY_DUST)) {
                        RecipeUtil.compressingRecipe(consumer, material, SubItem.TINY_DUST, SubItem.DUST, material.getName() + "_dust_compacting", false);
                        RecipeUtil.decompressingRecipe(consumer, material, SubItem.DUST, SubItem.TINY_DUST, 9, false, material.getName() + "_dust_decompacting");
                    }
                    if (material.hasSubItem(SubItem.SMALL_DUST)) {
                        RecipeUtil.compressingRecipe(consumer, material, SubItem.SMALL_DUST, SubItem.DUST, true, material.getName() + "_dust_compacting_secondary", false);
                        RecipeUtil.decompressingRecipe(consumer, material, SubItem.DUST, SubItem.SMALL_DUST, 4, true, material.getName() + "_dust_decompacting");
                    }
                }
            }
        });
    }
}
