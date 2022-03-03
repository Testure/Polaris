package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ChancedItemStack {
    public final ItemStack stack;
    public final int chance;

    public ChancedItemStack(ItemStack stack, int chance) {
        this.stack = stack;
        this.chance = chance;
    }
}
