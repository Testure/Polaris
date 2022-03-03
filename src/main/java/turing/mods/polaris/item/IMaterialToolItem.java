package turing.mods.polaris.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.IItemTier;
import turing.mods.polaris.material.Material;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IMaterialToolItem extends IItemTier, ITintedItem, IHandheldItem, ILayeredItem {
    Material getMaterial();
}
