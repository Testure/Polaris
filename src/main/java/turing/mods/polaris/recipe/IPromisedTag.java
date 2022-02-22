package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 *
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IPromisedTag {
    ITag.INamedTag<Item> toTag();

    Ingredient toIngredient();

    boolean isResolved();

    void resolve();

    void unResolve();
}
