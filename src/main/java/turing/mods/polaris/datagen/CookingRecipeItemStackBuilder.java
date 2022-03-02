package turing.mods.polaris.datagen;

import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

/**
 * Scuffed way to make a cooking recipe that results in a customized amount.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CookingRecipeItemStackBuilder {
    private final Ingredient ingredient;
    private final ItemStack result;
    private final float exp;
    private final int time;
    private final IRecipeSerializer<? extends AbstractCookingRecipe> serializer;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();

    private CookingRecipeItemStackBuilder(Ingredient ingredient, ItemStack result, float exp, int time, IRecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        this.ingredient = ingredient;
        this.result = result;
        this.exp = exp;
        this.time = time;
        this.serializer = serializer;
    }

    public static CookingRecipeItemStackBuilder cookingRecipe(Ingredient ingredient, ItemStack result, float experience, int cookingTime, IRecipeSerializer<? extends AbstractCookingRecipe> serializer) {
        return new CookingRecipeItemStackBuilder(ingredient, result, experience, cookingTime, serializer);
    }

    public static CookingRecipeItemStackBuilder smeltingRecipe(Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        return cookingRecipe(ingredient, result, experience, cookingTime, IRecipeSerializer.SMELTING);
    }

    public static CookingRecipeItemStackBuilder smeltingRecipe(Ingredient ingredient, IItemProvider result, int resultCount, float experience, int cookingTime) {
        return smeltingRecipe(ingredient, new ItemStack(result.asItem(), resultCount), experience, cookingTime);
    }

    public static CookingRecipeItemStackBuilder blastingRecipe(Ingredient ingredient, ItemStack result, float experience, int cookingTime) {
        return cookingRecipe(ingredient, result, experience, cookingTime, IRecipeSerializer.BLASTING);
    }

    public static CookingRecipeItemStackBuilder blastingRecipe(Ingredient ingredient, IItemProvider result, int resultCount, float experience, int cookingTime) {
        return blastingRecipe(ingredient, new ItemStack(result.asItem(), resultCount), experience, cookingTime);
    }

    public CookingRecipeItemStackBuilder addCriterion(String name, ICriterionInstance criterion) {
        this.advancementBuilder.withCriterion(name, criterion);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        this.validate(id);
        this.finalizeAdvancement(id);
        consumer.accept(new Result(id, this.ingredient, this.result, this.exp, this.time, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getItem().getGroup().getPath() + "/" + id.getPath()), this.advancementBuilder, this.serializer));
    }

    private void finalizeAdvancement(ResourceLocation id) {
        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
    }

    private void validate(ResourceLocation id) {
        if (this.advancementBuilder.getCriteria().isEmpty()) throw new IllegalStateException("No way of obtaining recipe " + id);
    }

    public static class Result implements IFinishedRecipe {
        private final Ingredient ingredient;
        private final ItemStack result;
        private final float exp;
        private final int time;
        private final IRecipeSerializer<? extends AbstractCookingRecipe> serializer;
        private final ResourceLocation id;
        private final ResourceLocation advancementId;
        private final Advancement.Builder advancementBuilder;

        public Result(ResourceLocation id, Ingredient ingredient, ItemStack result, float exp, int time, ResourceLocation advancementId, Advancement.Builder advancementBuilder, IRecipeSerializer<? extends AbstractCookingRecipe> serializer) {
            this.id = id;
            this.ingredient = ingredient;
            this.result = result;
            this.exp = exp;
            this.time = time;
            this.serializer = serializer;
            this.advancementBuilder = advancementBuilder;
            this.advancementId = advancementId;
        }


        @Override
        public void serialize(JsonObject json) {
            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", Registry.ITEM.getKey(result.getItem()).toString());
            resultObject.addProperty("count", result.getCount());

            json.add("ingredient", ingredient.serialize());
            json.add("result", resultObject);
            json.addProperty("experience", exp);
            json.addProperty("cookingtime", time);
        }

        @Override
        public ResourceLocation getID() {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return serializer;
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
