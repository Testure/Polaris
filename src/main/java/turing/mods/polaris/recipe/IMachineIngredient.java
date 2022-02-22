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
    boolean test(@Nullable ItemStack stack);

    boolean isEmpty();

    boolean isResolved();
}
