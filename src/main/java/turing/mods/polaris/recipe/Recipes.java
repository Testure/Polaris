package turing.mods.polaris.recipe;

import java.util.HashMap;
import java.util.Map;

public class Recipes {
    public static final Map<String, RecipeMap> RECIPE_MAPS = new HashMap<>();

    public static final RecipeMap COMPRESSOR = newMap("compressor", new RecipeMap("compressor", 1, 1, false, false));

    private static RecipeMap newMap(String name, RecipeMap recipeMap) {
        RECIPE_MAPS.put(name, recipeMap);
        return recipeMap;
    }

    public static void initialize() {

    }
}
