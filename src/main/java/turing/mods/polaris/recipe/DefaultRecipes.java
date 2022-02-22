package turing.mods.polaris.recipe;

import net.minecraft.item.ItemStack;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.MaterialRegistry;

import java.util.Map;

import static turing.mods.polaris.Voltages.*;

public class DefaultRecipes {
    public static void clearRecipes() {
        for (RecipeMap recipeMap : Recipes.RECIPE_MAPS.values())
            for (Map.Entry<String, IMachineRecipe> entry : recipeMap.getRecipes().entrySet())
                if (entry.getKey().contains("default"))
                    recipeMap.removeRecipe(entry.getKey(), entry.getValue());
    }

    public static void addRecipes() {
        Recipes.COMPRESSOR.addRecipe("iron_plate_default", MachineRecipeBuilder.builder()
                .inputs(PromisedTag.of("ingots/iron"))
                .outputs(new ItemStack(MaterialRegistry.IRON.getItemFromSubItem(SubItem.PLATE), 1))
                .EUt(Voltages.getLossAdjusted(ULV))
                .duration(MaterialRegistry.IRON.get().mass)
        );
    }
}
