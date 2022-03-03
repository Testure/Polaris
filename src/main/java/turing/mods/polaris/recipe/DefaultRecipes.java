package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class DefaultRecipes {
    public static void clearRecipes() {
        for (RecipeMap recipeMap : Recipes.RECIPE_MAPS.values())
            for (Map.Entry<String, IMachineRecipe> entry : recipeMap.getRecipes().entrySet())
                if (entry.getKey().contains("default"))
                    recipeMap.removeRecipe(entry.getKey(), entry.getValue());
    }

    public static void addRecipes() {
        CompressorRecipes.add();
    }
}
