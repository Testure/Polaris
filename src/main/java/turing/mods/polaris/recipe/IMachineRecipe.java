package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 *
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IMachineRecipe {
    List<IMachineIngredient> getInputs();

    List<ItemStack> getOutputs();

    List<FluidStack> getFluidInputs();

    List<FluidStack> getFluidOutputs();

    List<Tuple<ItemStack, Integer>> getChancedOutputs();

    @Nullable
    default Integer getCircuitConfig() {
        return null;
    }

    default int getDuration() {
        return 20;
    }

    default int getEUt() {
        return 8;
    }

    default int getInputCount() {
        return getInputs().size() + getFluidInputs().size();
    }

    default int getOutputCount() {
        return getOutputs().size() + getChancedOutputs().size() + getFluidOutputs().size();
    }
}
