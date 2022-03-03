package turing.mods.polaris.recipe;

import com.google.common.collect.ImmutableMap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.Voltages;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RecipeMap {
    public final String name;
    public final int maxInputs;
    public final int maxOutputs;
    public final int maxFluidInputs;
    public final int maxFluidOutputs;
    public final boolean hasChancedOutputs;
    public final boolean usesCircuit;
    public final boolean usesFluid;
    public final boolean outputsFluid;
    private final Map<String, IMachineRecipe> RECIPES = new HashMap<>();

    public RecipeMap(String name, int maxInputs, int maxOutputs, int maxFluidInputs, int maxFluidOutputs, boolean hasChancedOutputs, boolean usesCircuit) {
        this.name = name;
        this.maxInputs = maxInputs;
        this.maxOutputs = maxOutputs;
        this.maxFluidInputs = maxFluidInputs;
        this.maxFluidOutputs = maxFluidOutputs;
        this.hasChancedOutputs = hasChancedOutputs;
        this.usesCircuit = usesCircuit;
        this.usesFluid = maxFluidInputs > 0;
        this.outputsFluid = maxFluidOutputs > 0;
    }

    public RecipeMap(String name, int maxInputs, int maxOutputs, boolean hasChancedOutputs, boolean usesCircuit) {
        this(name, maxInputs, maxOutputs, 0, 0, hasChancedOutputs, usesCircuit);
    }

    public Map<String, IMachineRecipe> getRecipes() {
        return ImmutableMap.copyOf(RECIPES);
    }

    @Nullable
    public IMachineRecipe getByName(String recipeName) {
        return RECIPES.get(recipeName);
    }

    @Nullable
    public IMachineRecipe findRecipeByInputs(IMachineIngredient[] inputIngredients, @Nullable FluidStack[] inputFluids, int voltage, int duration, int circuit, boolean exactVoltage) {
        for (IMachineRecipe machineRecipe : RECIPES.values())
            if (duration <= 0 || machineRecipe.getDuration() == duration)
                if (voltage <= 0 || ((exactVoltage && machineRecipe.getEUt() == voltage) || machineRecipe.getEUt() <= Voltages.roundVoltage(voltage)))
                    if (circuit <= 0 || machineRecipe.getCircuitConfig() == circuit)
                        if (!usesFluid || (inputFluids != null && machineRecipe.getFluidInputs().containsAll(Arrays.asList(inputFluids))))
                            if (machineRecipe.getInputs().containsAll(Arrays.asList(inputIngredients)))
                                return machineRecipe;

        return null;
    }

    @Nullable
    public IMachineRecipe findRecipe(ItemStack[] inputs, @Nullable FluidStack[] inputFluids, int voltage, int duration, int circuit, boolean exactVoltage) {
        for (IMachineRecipe machineRecipe : RECIPES.values())
            if (duration <= 0 || machineRecipe.getDuration() == duration)
                if (voltage <= 0 || ((exactVoltage && machineRecipe.getEUt() == voltage) || machineRecipe.getEUt() <= Voltages.roundVoltage(voltage)))
                    if (circuit <= 0 || machineRecipe.getCircuitConfig() == circuit) {
                        boolean matchesFluid = !usesFluid || (inputFluids != null && inputFluids.length >= 1);
                        boolean matchesInputs = inputs.length >= 1;
                        if (usesFluid && inputFluids != null) {
                            for (int i = 0; i < inputFluids.length; i++) {
                                if (!machineRecipe.getFluidInputs().get(i).containsFluid(inputFluids[i])) matchesFluid = false;
                            }
                        }
                        for (int i = 0; i < inputs.length; i++) {
                            if (!machineRecipe.getInputs().get(i).test(inputs[i])) matchesInputs = false;
                        }
                        if (!matchesFluid || !matchesInputs) continue;
                        return machineRecipe;
                    }
        return null;
    }

    public IMachineRecipe addRecipe(String name, IMachineRecipe recipe) {
        IMachineRecipe toAdd = null;
        if (recipe.getInputCount() <= maxInputs + maxFluidInputs)
            if (recipe.getOutputCount() <= maxOutputs + maxFluidOutputs)
                if (recipe.getCircuitConfig() > 0 == usesCircuit)
                    if (recipe.getFluidInputs().size() > 0 == usesFluid)
                        if (recipe.getFluidOutputs().size() > 0 == outputsFluid)
                            if (recipe.getChancedOutputs().size() > 0 == hasChancedOutputs)
                                toAdd = recipe;
        if (toAdd != null) RECIPES.put(name, toAdd);
        else Polaris.LOGGER.warn(String.format("RECIPE { %s } WAS NOT ADDED, DOES NOT MATCH THE RECIPE_MAP DESCRIPTION", name));
        return recipe;
    }

    public IMachineRecipe addRecipe(String name, MachineRecipeBuilder builder) {
        return addRecipe(name, builder.build());
    }

    public boolean removeRecipe(String name, IMachineRecipe recipe) {
        return RECIPES.remove(name, recipe);
    }
}
