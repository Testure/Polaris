package turing.mods.polaris.datagen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Scuffed way to make a shaped recipe that results in an {@link ItemStack}.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ShapedRecipeItemStackBuilder {
    private final ItemStack result;
    private List<String> pattern = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Advancement.Builder advancementBuilder = Advancement.Builder.builder();
    private String group;

    public ShapedRecipeItemStackBuilder(ItemStack stack) {
        this.result = stack;
    }

    /**
     * @see #serializeResult(ItemStack)
     */
    public static ShapedRecipeItemStackBuilder shapedRecipe(ItemStack stack) {
        return new ShapedRecipeItemStackBuilder(stack);
    }

    public static ShapedRecipeItemStackBuilder shapedRecipe(IItemProvider itemProvider, int count) {
        ItemStack stack = itemProvider.asItem().getDefaultInstance();
        stack.setCount(count);
        return shapedRecipe(stack);
    }

    public static ShapedRecipeItemStackBuilder shapedRecipe(IItemProvider itemProvider) {
        return shapedRecipe(itemProvider, 1);
    }

    public ShapedRecipeItemStackBuilder key(Character symbol, Ingredient ingredient) {
        if (this.key.containsKey(symbol)) throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        else if (symbol == ' ') throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    public ShapedRecipeItemStackBuilder key(Character symbol, IItemProvider itemProvider) {
        return key(symbol, Ingredient.fromItems(itemProvider));
    }

    public ShapedRecipeItemStackBuilder key(Character symbol, ITag<Item> tag) {
        return key(symbol, Ingredient.fromTag(tag));
    }

    public ShapedRecipeItemStackBuilder patternLine(String pattern) {
        if (!this.pattern.isEmpty() && pattern.length() != this.pattern.get(0).length()) throw new IllegalArgumentException("Pattern must be the same width on every line!");
        else {
            this.pattern.add(pattern);
            return this;
        }
    }

    public ShapedRecipeItemStackBuilder setGroup(String group) {
        this.group = group;
        return this;
    }

    public ShapedRecipeItemStackBuilder addCriterion(String name, ICriterionInstance criterion) {
        this.advancementBuilder.withCriterion(name, criterion);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        this.build(consumer, Registry.ITEM.getKey(this.result.getItem()));
    }

    public void build(Consumer<IFinishedRecipe> consumer, String save) {
        ResourceLocation key = Registry.ITEM.getKey(this.result.getItem());
        if ((new ResourceLocation(save)).equals(key)) throw new IllegalStateException("Shaped Recipe " + save + " should remove its 'save' argument");
        else this.build(consumer, new ResourceLocation(save));
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        this.validate(id);
        this.advancementBuilder.withParentId(new ResourceLocation("recipes/root")).withCriterion("has_the_recipe", RecipeUnlockedTrigger.create(id)).withRewards(AdvancementRewards.Builder.recipe(id)).withRequirementsStrategy(IRequirementsStrategy.OR);
        consumer.accept(new Result(id, this.result, this.group != null ? this.group : "", this.pattern, this.key, this.advancementBuilder, new ResourceLocation(id.getNamespace(), "recipes/" + this.result.getItem().getGroup().getPath() + "/" + id.getPath())));
    }

    private void validate(ResourceLocation id) {
        if (this.pattern.isEmpty()) throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
        else {
            Set<Character> characterSet = Sets.newHashSet(this.key.keySet());
            characterSet.remove(' ');

            for (String s : this.pattern) {
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (!this.key.containsKey(c) && c != ' ') throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c + "'");
                    characterSet.remove(c);
                }
            }

            if (!characterSet.isEmpty()) throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            else if (this.pattern.size() == 1 && this.pattern.get(0).length() == 1) throw new IllegalStateException("Shaped recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
            else if (this.advancementBuilder.getCriteria().isEmpty()) throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    /**
     * Turns an {@link ItemStack} into a json with the item, count and NBT data.
     * Enchantment data is automatically saved.
     * Other NBT data must have it's key added to a {@link StringNBT} and added to the "SerializeMe" {@link ListNBT}.
     */
    public static JsonObject serializeResult(ItemStack result) {
        JsonObject resultJson = new JsonObject();
        JsonObject nbtJson = new JsonObject();

        if (result.isEnchanted()) {
            JsonArray enchantments = new JsonArray();
            for (INBT nbt : result.getEnchantmentTagList()) {
                CompoundNBT enchantment = (CompoundNBT) nbt;
                JsonObject enchantmentJson = new JsonObject();
                enchantmentJson.addProperty("id", enchantment.getString("id"));
                enchantmentJson.addProperty("lvl", enchantment.getShort("lvl"));
                enchantments.add(enchantmentJson);
            }
            nbtJson.add("Enchantments", enchantments);
        }

        CompoundNBT tag = result.getOrCreateTag();

        if (tag.contains("SerializeMe", 9)) {
            for (INBT nbt : tag.getList("SerializeMe", 8)) {
                if (nbt instanceof StringNBT) {
                    StringNBT stringNBT = (StringNBT) nbt;
                    if (tag.contains(stringNBT.getString())) {
                        String key = stringNBT.getString();
                        for (int i = 1; i < 12; i++) {
                            if (tag.contains(key, i)) {
                                switch (i) {
                                    case 1:
                                        nbtJson.addProperty(key, tag.getBoolean(key));
                                        break;
                                    case 2:
                                        nbtJson.addProperty(key, tag.getShort(key));
                                        break;
                                    case 3:
                                        nbtJson.addProperty(key, tag.getInt(key));
                                        break;
                                    case 4:
                                        nbtJson.addProperty(key, tag.getLong(key));
                                        break;
                                    case 5:
                                        nbtJson.addProperty(key, tag.getFloat(key));
                                        break;
                                    case 6:
                                        nbtJson.addProperty(key, tag.getDouble(key));
                                        break;
                                    case 8:
                                        nbtJson.addProperty(key, tag.getString(key));
                                        break;
                                    case 11:
                                        JsonArray array = new JsonArray();
                                        for (int I : tag.getIntArray(key)) array.add(I);
                                        nbtJson.add(key, array);
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }

        resultJson.addProperty("item", Registry.ITEM.getKey(result.getItem()).toString());
        if (result.getCount() > 1) resultJson.addProperty("count", result.getCount());
        resultJson.add("nbt", nbtJson);
        return resultJson;
    }

    public class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack result;
        private final String group;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;
        private final Advancement.Builder advancementBuilder;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, ItemStack result, String group, List<String> pattern, Map<Character, Ingredient> key, Advancement.Builder advancementBuilder, ResourceLocation advancementId) {
            this.id = id;
            this.advancementId = advancementId;
            this.advancementBuilder = advancementBuilder;
            this.result = result;
            this.pattern = pattern;
            this.key = key;
            this.group = group;
        }

        @Override
        public void serialize(JsonObject json) {
            if (!this.group.isEmpty()) json.addProperty("group", this.group);

            JsonArray pattern = new JsonArray();
            JsonObject keys = new JsonObject();
            JsonObject result = serializeResult(this.result);

            for (String s : this.pattern) pattern.add(s);
            for (Map.Entry<Character, Ingredient> entry : this.key.entrySet()) keys.add(String.valueOf(entry.getKey()), entry.getValue().serialize());

            json.add("pattern", pattern);
            json.add("key", keys);
            json.add("result", result);
        }

        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer() {
            return IRecipeSerializer.CRAFTING_SHAPED;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return this.advancementBuilder.serialize();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return this.advancementId;
        }
    }
}
