package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import turing.mods.polaris.Voltages;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static turing.mods.polaris.Voltages.ULV;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IMachineRecipe {
    List<IMachineIngredientStack> getInputs();

    List<ItemStack> getOutputs();

    List<FluidStack> getFluidInputs();

    List<FluidStack> getFluidOutputs();

    List<ChancedItemStack> getChancedOutputs();

    default int getCircuitConfig() {
        return -1;
    }

    default int getDuration() {
        return 20;
    }

    default int getEUt() {
        return Voltages.VOLTAGES[ULV].energy;
    }

    default int getInputCount() {
        return getInputs().size() + getFluidInputs().size();
    }

    default int getOutputCount() {
        return getOutputs().size() + getChancedOutputs().size() + getFluidOutputs().size();
    }
}
