package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

/**
 *
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IMachineIngredient {
    default boolean test(@Nullable ItemStack stack) {
        if (stack == null) return false;
        return Arrays.asList(getItems()).contains(stack);
    }

    ItemStack[] getItems();

    default boolean isEmpty() {
        return getItems()[0] != null;
    }

    IMachineIngredient of(IItemProvider... providers);

    IMachineIngredient of(ItemStack... stacks);

    IMachineIngredient of(IPromisedTag... tags);

    IMachineIngredient fromIngredient(Ingredient ingredient);
}
