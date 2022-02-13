package turing.mods.polaris.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public interface ITintedItem extends IItemColor {
    default int getColor(@Nonnull ItemStack itemStack, int layer) {
        return 0xFFFFFFFF;
    }
}
