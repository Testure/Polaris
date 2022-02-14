package turing.mods.polaris.item;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Like IItemColor but is implemented by the item itself, and as such exists on both client and server.
 */
public interface ITintedItem {
    default int getColor(@Nonnull ItemStack itemStack, int layer) {
        return 0xFFFFFFFF;
    }
}
