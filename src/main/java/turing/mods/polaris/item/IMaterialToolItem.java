package turing.mods.polaris.item;

import net.minecraft.item.IItemTier;
import turing.mods.polaris.material.Material;

public interface IMaterialToolItem extends IItemTier, ITintedItem, IHandheldItem, ILayeredItem {
    Material getMaterial();
}
