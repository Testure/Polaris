package turing.mods.polaris.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Like IItemColor but is implemented by the item itself, and as such exists on both client and server.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ITintedItem {
    default int getColor(ItemStack itemStack, int layer) {
        return 0xFFFFFFFF;
    }
}
