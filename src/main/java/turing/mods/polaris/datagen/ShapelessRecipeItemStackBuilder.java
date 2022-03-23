package turing.mods.polaris.datagen;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Consumer;

/**
 * Scuffed way to make a shapeless recipe that results in an {@link ItemStack}.
 * uses {@link ShapedRecipeItemStackBuilder#serializeResult(ItemStack)} to serialize the result.
 * @see ShapedRecipeItemStackBuilder#serializeResult(ItemStack)
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ShapelessRecipeItemStackBuilder {
    private final ItemStack result;
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    private String group;

    public ShapelessRecipeItemStackBuilder(ItemStack result) {
        this.result = result;
    }

    public static ShapelessRecipeItemStackBuilder shapelessRecipe(ItemStack result) {
        return new ShapelessRecipeItemStackBuilder(result);
    }

    public static ShapelessRecipeItemStackBuilder shapelessRecipe(IItemProvider result, int count) {
        ItemStack stack = result.asItem().getDefaultInstance();
        stack.setCount(count);
        return shapelessRecipe(stack);
    }

    public static ShapelessRecipeItemStackBuilder shapelessRecipe(IItemProvider result) {
        return shapelessRecipe(result, 1);
    }

    public ShapelessRecipeItemStackBuilder addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public ShapelessRecipeItemStackBuilder addIngredient(ItemStack stack) {
        return this.addIngredient(Ingredient.fromStacks(stack));
    }

    public ShapelessRecipeItemStackBuilder addIngredient(IItemProvider item, int count) {
        for (int i = 0; i < count; i++) this.addIngredient(Ingredient.fromItems(item));
        return this;
    }

    public ShapelessRecipeItemStackBuilder addIngredient(IItemProvider item) {
        return addIngredient(item, 1);
    }

    public ShapelessRecipeItemStackBuilder addIngredient(ITag<Item> tag) {
        return this.addIngredient(Ingredient.fromTag(tag));
    }

    public ShapelessRecipeItemStackBuilder setGroup(String group) {
        this.group = group;
        return this;
    }

    public ShapelessRecipeItemStackBuilder addCriterion(String name, ICriterionInstance criterion) {
        this.advancementBuilder.withCriterion(name, criterion);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        this.build(consumer, Registry.ITEM.getKey(this.result.getItem()));
    }

    public void build(Consumer<IFinishedRecipe> consumer, String save) {
        ResourceLocation resourceLocation = Registry.ITEM.getKey(this.result.getItem());
        if ((new ResourceLocation(save)).equals(resourceLocation)) throw new IllegalStateException("Shapeless Recipe " + save + " should remove its 'save' argument");
        else this.build(consumer, new ResourceLocation(save));
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        this.validate(id);
        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumer.accept(new Result(id, this.result, this.group != null ? this.group : "", this.ingredients, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getItem().getGroup().getPath() + "/" + id.getPath())));
    }

    private void validate(ResourceLocation id) {
        if (this.advancementBuilder.getCriteria().isEmpty()) throw new IllegalStateException("No way of obtaining recipe " + id);
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id, advancementId;
        private final Advancement.Builder advancementBuilder;
        private final String group;
        private final ItemStack result;
        private final List<Ingredient> ingredients;

        public Result(ResourceLocation id, ItemStack result, String group, List<Ingredient> ingredients, Advancement.Builder advancementBuilder, ResourceLocation advancementId) {
            this.id = id;
            this.advancementBuilder = advancementBuilder;
            this.advancementId = advancementId;
            this.result = result;
            this.ingredients = ingredients;
            this.group = group;
        }

        @Override
        public void serialize(JsonObject json) {
            if (!this.group.isEmpty()) json.addProperty("group", this.group);

            JsonArray ingredients = new JsonArray();
            JsonObject result = ShapedRecipeItemStackBuilder.serializeResult(this.result);

            for (Ingredient ingredient : this.ingredients) ingredients.add(ingredient.serialize());

            json.add("ingredients", ingredients);
            json.add("result", result);
        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return IRecipeSerializer.CRAFTING_SHAPELESS;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return advancementBuilder.serialize();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return advancementId;
        }
    }
}
