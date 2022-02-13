package turing.mods.polaris.item;

/**
 * Items that implement this can customize the textures used in their generated models.
 * stacked with IBasicModeledItem/IHandheldItem
 */
public interface ILayeredItem {
    default String getLayer0(String itemName) {
        return "item/" + itemName;
    }

    default String getLayer1(String itemName) {
        return "item/" + itemName + "_overlay";
    }
}
