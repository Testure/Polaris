package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import turing.mods.polaris.Polaris;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PromisedTag implements IPromisedTag {
    private final ResourceLocation tagLoc;
    @Nullable
    private Ingredient ingredient;
    @Nullable
    private ITag.INamedTag<Item> tag;
    private boolean resolved = false;

    private PromisedTag(String tagName, @Nullable String modId) {
        this.tagLoc = Polaris.modLoc(tagName, modId != null ? modId : "forge");
        Polaris.TAGS.add(this);
    }

    public static IPromisedTag of(ResourceLocation tagLoc) {
        return new PromisedTag(tagLoc.getPath(), tagLoc.getNamespace());
    }

    public static IPromisedTag of(String tagName) {
        return new PromisedTag(tagName, null);
    }

    public static IPromisedTag of(String tagName, String modId) {
        return new PromisedTag(tagName, modId);
    }

    @Override
    public ITag.INamedTag<Item> toTag() {
        if (!isResolved()) throw new IllegalStateException("Cannot use an unresolved tag.");
        if (tag == null) throw new NullPointerException("No ITag exists for this tag. This tag was probably resolved incorrectly.");
        return tag;
    }

    @Override
    public Ingredient toIngredient() {
        if (!isResolved()) throw new IllegalStateException("Cannot use an unresolved tag.");
        if (ingredient == null) throw new NullPointerException("No Ingredient exists for this tag. This tag was probably resolved incorrectly.");
        return ingredient;
    }

    @Override
    public boolean isResolved() {
        return resolved;
    }

    @Override
    public void resolve() {
        resolved = true;
        tag = ItemTags.makeWrapperTag(this.tagLoc.toString());
        ingredient = Ingredient.fromTag(toTag());
    }

    @Override
    public void unResolve() {
        resolved = false;
        tag = null;
        ingredient = null;
    }
}
