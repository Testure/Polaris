package turing.mods.polaris.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import turing.mods.polaris.util.Lists;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class SingleItemRecipeIdentifier implements IMachineRecipeIdentifier {
    public final IMachineRecipe recipe;

    public SingleItemRecipeIdentifier(IMachineRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean test(List<IMachineIngredient> ingredients, @Nullable List<FluidStack> fluidInputs, @Nullable List<FluidStack> fluidOutputs, @Nullable List<ItemStack> outputs, @Nullable Integer duration, @Nullable Integer EUt, @Nullable Integer circuit) {
        if (Lists.compareLists(recipe.getInputs(), ingredients))
            if (outputs == null || (Lists.compareLists(recipe.getOutputs(), outputs)))
                if (outputs == null || Lists.chancedOutputsEqual(recipe.getChancedOutputs(), outputs))
                    if (duration == null || recipe.getDuration() == duration)
                        if (EUt == null || recipe.getEUt() == EUt)
                            return (circuit == null || circuit == 0) || Objects.equals(recipe.getCircuitConfig(), circuit);
        return false;
    }

    @Override
    public boolean test(List<ItemStack> outputs, @Nullable Integer duration, @Nullable Integer EUt, @Nullable Integer circuit) {
        return test(recipe.getInputs(), null, null, outputs, duration, EUt, circuit);
    }

    @Override
    public boolean test(List<IMachineIngredient> ingredients, @Nullable Integer EUt, @Nullable Integer circuit) {
        return test(ingredients, null, null, null, null, EUt, circuit);
    }

    @Override
    public boolean test(List<IMachineIngredient> ingredients, List<ItemStack> outputs, @Nullable Integer duration, @Nullable Integer EUt, @Nullable Integer circuit) {
        return test(ingredients, null, null, outputs, duration, EUt, circuit);
    }
}
