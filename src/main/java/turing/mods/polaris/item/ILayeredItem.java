package turing.mods.polaris.item;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Items that implement this can customize the textures used in their generated models.
 * stacked with IBasicModeledItem/IHandheldItem
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ILayeredItem {
    default String getLayer0(String itemName) {
        return "item/" + itemName;
    }

    default String getLayer1(String itemName) {
        return "item/" + itemName + "_overlay";
    }
}
