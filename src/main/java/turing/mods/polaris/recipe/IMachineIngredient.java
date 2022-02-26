package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IMachineIngredient {
    boolean test(@Nullable ItemStack stack);

    boolean isEmpty();

    boolean isResolved();
}
