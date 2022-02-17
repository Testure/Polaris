package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Tuple;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MachineRecipe implements IMachineRecipe {
    public IMachineRecipeIdentifier identity;
    private final List<IMachineIngredient> inputs;
    private final List<FluidStack> fluidInputs;
    private final List<ItemStack> outputs;
    private final List<FluidStack> fluidOutputs;
    private final List<Tuple<ItemStack, Integer>> chancedOutputs;
    private final int duration;
    private final int eut;
    @Nullable
    private final Integer circuitConfig;

    public MachineRecipe(IMachineRecipeIdentifier identifier, List<IMachineIngredient> inputs, List<FluidStack> fluidInputs, List<ItemStack> outputs, List<Tuple<ItemStack, Integer>> chancedOutputs, List<FluidStack> fluidOutputs, @Nullable Integer circuit, int duration, int eut) {
        this.identity = identifier;
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
    public List<IMachineIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<ItemStack> getOutputs() {
        return outputs;
    }

    @Override
    public List<FluidStack> getFluidInputs() {
        return fluidInputs;
    }

    @Override
    public List<FluidStack> getFluidOutputs() {
        return fluidOutputs;
    }

    @Override
    public List<Tuple<ItemStack, Integer>> getChancedOutputs() {
        return chancedOutputs;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    @Nullable
    public Integer getCircuitConfig() {
        return circuitConfig;
    }

    @Override
    public int getEUt() {
        return eut;
    }

    public static class MachineIngredient implements IMachineIngredient {
        private final List<ItemStack> items;
        private final List<IPromisedTag> tags;

        private MachineIngredient(List<ItemStack> items, List<IPromisedTag> tags) {
            this.items = items;
            this.tags = tags;
        }

        private boolean isResolved() {
            for (IPromisedTag tag : tags) {
                if (!tag.isResolved()) return false;
            }
            return true;
        }

        @Override
        public boolean test(@Nullable ItemStack stack) {
            if (stack == null) return false;
            assert this.isResolved();

            if (this.items.contains(stack)) return true;
            if (this.tags.size() > 0) {
                for (IPromisedTag tag : this.tags) {
                    if (tag.toIngredient().test(stack)) return true;
                }
            }

            return false;
        }

        @Override
        public ItemStack[] getItems() {
            assert this.isResolved();
            List<ItemStack> itemStacks = new ArrayList<>(this.items);

            for (IPromisedTag tag : this.tags) {
                itemStacks.addAll(Arrays.asList(tag.toIngredient().getItems()));
            }

            return itemStacks.toArray(new ItemStack[0]);
        }

        @Override
        public IMachineIngredient of(IItemProvider... providers) {
            List<ItemStack> items = new ArrayList<>();
            Arrays.stream(providers).forEach(provider -> items.add(new ItemStack(provider.asItem())));
            return new MachineIngredient(items, new ArrayList<>());
        }

        @Override
        public IMachineIngredient of(ItemStack... stacks) {
            return new MachineIngredient(Arrays.asList(stacks), new ArrayList<>());
        }

        @Override
        public IMachineIngredient of(IPromisedTag... tags) {
            return new MachineIngredient(new ArrayList<>(), Arrays.asList(tags));
        }

        @Override
        public IMachineIngredient fromIngredient(Ingredient ingredient) {
            return this.of(ingredient.getItems());
        }
    }
}
