package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 *
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IMachineRecipeIdentifier {
    boolean test(List<IMachineIngredient> ingredients, @Nullable List<FluidStack> fluidInputs, @Nullable List<FluidStack> fluidOutputs, @Nullable List<ItemStack> outputs, @Nullable Integer duration, @Nullable Integer EUt, @Nullable Integer circuit);

    boolean test(List<ItemStack> outputs, @Nullable Integer duration, @Nullable Integer EUt, @Nullable Integer circuit);

    boolean test(List<IMachineIngredient> ingredients, @Nullable Integer EUt, @Nullable Integer circuit);

    boolean test(List<IMachineIngredient> ingredients, List<ItemStack> outputs, @Nullable Integer duration, @Nullable Integer EUt, @Nullable Integer circuit);
}
