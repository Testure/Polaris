package turing.mods.polaris.recipe;

import com.google.common.collect.ImmutableList;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.fluids.FluidStack;
import turing.mods.polaris.Polaris;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MachineRecipe implements IMachineRecipe {
    private final IMachineIngredientStack[] inputs;
    private final FluidStack[] fluidInputs;
    private final ItemStack[] outputs;
    private final FluidStack[] fluidOutputs;
    private final ChancedItemStack[] chancedOutputs;
    private final int duration;
    private final int eut;
    private final int circuitConfig;

    public MachineRecipe(IMachineIngredientStack[] inputs, FluidStack[] fluidInputs, ItemStack[] outputs, ChancedItemStack[] chancedOutputs, FluidStack[] fluidOutputs, int circuit, int duration, int eut) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.fluidInputs = fluidInputs;
        this.fluidOutputs = fluidOutputs;
        this.chancedOutputs = chancedOutputs;
        this.circuitConfig = circuit;
        this.duration = duration;
        this.eut = eut;
    }

    @Override
    public List<IMachineIngredientStack> getInputs() {
        return ImmutableList.copyOf(inputs);
    }

    @Override
    public List<ItemStack> getOutputs() {
        return ImmutableList.copyOf(outputs);
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return ImmutableList.copyOf(fluidInputs);
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return ImmutableList.copyOf(fluidOutputs);
    }

    @Override
    public List<ChancedItemStack> getChancedOutputs() {
        return ImmutableList.copyOf(chancedOutputs);
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getCircuitConfig() {
        return circuitConfig;
    }

    @Override
    public int getEUt() {
        return eut;
    }

    public static class MachineIngredient implements IMachineIngredient {
        private final ItemStack[] items;
        private final IPromisedTag[] tags;

        private MachineIngredient(ItemStack[] items, IPromisedTag[] tags) {
            this.items = items;
            this.tags = tags;
        }

        @Override
        public boolean isResolved() {
            for (IPromisedTag tag : tags) {
                if (!tag.isResolved()) return false;
            }
            return true;
        }

        @Override
        public boolean isEmpty() {
            for (ItemStack item : this.items)
                if (item != null) return false;
            for (IPromisedTag tag : this.tags)
                if (tag != null) return false;
            return true;
        }

        @Override
        public boolean test(@Nullable ItemStack stack) {
            if (stack == null) return false;
            if (!this.isResolved()) throw new IllegalStateException("Attempt to test on unresolved MachineIngredient");
            if (isEmpty()) return false;

            for (ItemStack itemStack : this.items)
                if (itemStack.isItemEqual(stack)) return true;
            for (IPromisedTag tag : this.tags)
                if (tag.toIngredient().test(stack)) return true;

            return false;
        }

        @Override
        public ItemStack[] getItems() {
            if (isEmpty()) return new ItemStack[0];
            if (!this.isResolved()) throw new IllegalStateException("Attempt to get items on unresolved MachineIngredient");

            List<ItemStack> list = new ArrayList<>(Arrays.asList(this.items));
            ItemStack[] tagStacks = Arrays.stream(this.tags).map((tag) -> tag.toTag().getAllElements().stream().map(ItemStack::new)).toArray(ItemStack[]::new);
            list.addAll(Arrays.asList(tagStacks));

            tagStacks = null;

            return list.toArray(new ItemStack[0]);
        }

        public static IMachineIngredient of(IItemProvider... providers) {
            ItemStack[] stacks = new ItemStack[providers.length];
            for (int i = 0; i < providers.length; i++) {
                stacks[i] = new ItemStack(providers[i].asItem(), 1);
            }
            return new MachineIngredient(stacks, new IPromisedTag[0]);
        }

        public static IMachineIngredient of(ItemStack... stacks) {
            return new MachineIngredient(stacks, new IPromisedTag[0]);
        }

        public static IMachineIngredient of(IPromisedTag... tags) {
            return new MachineIngredient(new ItemStack[0], tags);
        }

        public static IMachineIngredient fromIngredient(Ingredient ingredient) {
            return of(ingredient.getMatchingStacks());
        }
    }

    public static class MachineIngredientStack implements IMachineIngredientStack {
        private int count;
        private IMachineIngredient ingredient;

        public MachineIngredientStack(IMachineIngredient ingredient, int count) {
            this.ingredient = ingredient;
            this.count = count;
        }

        public MachineIngredientStack(IMachineIngredient ingredient) {
            this(ingredient, 1);
        }

        public static MachineIngredientStack fromItemStack(ItemStack stack) {
            return new MachineIngredientStack(MachineIngredient.of(stack), stack.getCount());
        }

        @Override
        public IMachineIngredient getIngredient() {
            return ingredient;
        }

        @Override
        public int getCount() {
            return count;
        }

        public IMachineIngredientStack setIngredient(IMachineIngredient ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        public IMachineIngredientStack setCount(int count) {
            this.count = count;
            return this;
        }
    }
}
