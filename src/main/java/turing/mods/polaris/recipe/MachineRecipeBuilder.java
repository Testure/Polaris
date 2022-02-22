package turing.mods.polaris.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import turing.mods.polaris.Voltages;

import javax.annotation.Nullable;

import java.util.function.Consumer;

import static turing.mods.polaris.Voltages.ULV;

public class MachineRecipeBuilder {
    protected int EUt = Voltages.VOLTAGES[ULV].energy;
    protected int duration = 100;
    protected int circuit = -1;
    protected IMachineIngredient[] inputs = new IMachineIngredient[0];
    protected ItemStack[] outputs = new ItemStack[0];
    protected ChancedItemStack[] chanceOutputs = new ChancedItemStack[0];
    protected FluidStack[] fluidInputs = new FluidStack[0];
    protected FluidStack[] fluidOutputs = new FluidStack[0];

    protected MachineRecipeBuilder() {

    }

    public static MachineRecipeBuilder builder() {
        return new MachineRecipeBuilder();
    }

    public MachineRecipeBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public MachineRecipeBuilder EUt(int EUt) {
        this.EUt = EUt;
        return this;
    }

    public MachineRecipeBuilder circuitConfig(int circuit) {
        this.circuit = circuit > 0 ? circuit : -1;
        return this;
    }

    public MachineRecipeBuilder inputs(IMachineIngredient... ingredients) {
        this.inputs = ingredients;
        return this;
    }

    public MachineRecipeBuilder inputs(ItemStack... stacks) {
        IMachineIngredient[] ingredients = new IMachineIngredient[stacks.length];
        for (int i = 0; i < stacks.length; i++) {
            ingredients[i] = MachineRecipe.MachineIngredient.of(stacks[i]);
        }
        return inputs(ingredients);
    }

    public MachineRecipeBuilder inputs(IPromisedTag... tags) {
        IMachineIngredient[] ingredients = new IMachineIngredient[tags.length];
        for (int i = 0; i < tags.length; i++) {
            ingredients[i] = MachineRecipe.MachineIngredient.of(tags[i]);
        }
        return inputs(ingredients);
    }

    public MachineRecipeBuilder outputs(ItemStack... stacks) {
        this.outputs = stacks;
        return this;
    }

    public MachineRecipeBuilder chancedOutputs(ChancedItemStack... stacks) {
        this.chanceOutputs = stacks;
        return this;
    }

    public MachineRecipeBuilder fluidInputs(FluidStack... fluids) {
        this.fluidInputs = fluids;
        return this;
    }

    public MachineRecipeBuilder fluidOutputs(FluidStack... fluids) {
        this.fluidOutputs = fluids;
        return this;
    }

    public IMachineRecipe build() {
        return new MachineRecipe(inputs, fluidInputs, outputs, chanceOutputs, fluidOutputs, circuit, duration, EUt);
    }

    public IMachineRecipe build(Consumer<IMachineRecipe> consumer) {
        IMachineRecipe recipe = build();
        consumer.accept(recipe);
        return recipe;
    }
}
