package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IMachineIngredientStack {
    IMachineIngredient getIngredient();

    int getCount();

    default boolean test(@Nullable ItemStack stack) {
        return getIngredient().test(stack);
    }

    default boolean test(@Nullable ItemStack stack, boolean countSensitive) {
        if (stack == null) return false;
        return countSensitive ? (stack.getCount() == getCount() && test(stack)) : test(stack);
    }
}
