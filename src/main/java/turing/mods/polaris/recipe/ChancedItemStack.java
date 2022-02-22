package turing.mods.polaris.recipe;

import net.minecraft.item.ItemStack;

public class ChancedItemStack {
    public final ItemStack stack;
    public final int chance;

    public ChancedItemStack(ItemStack stack, int chance) {
        this.stack = stack;
        this.chance = chance;
    }
}
