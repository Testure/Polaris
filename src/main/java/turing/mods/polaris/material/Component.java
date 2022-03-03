package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.fluid.Fluid;
import turing.mods.polaris.registry.FluidRegistryObject;
import turing.mods.polaris.registry.MaterialRegistryObject;
import turing.mods.polaris.util.Either;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Component {
    protected final Supplier<MaterialRegistryObject> baseMaterial;
    protected Either<Supplier<Fluid>, Supplier<FluidRegistryObject<?, ?, ?, ?>>> fluidSupplier;
    protected final String chemicalName;
    protected boolean isCombination;
    @Nullable
    protected ComponentStack[] madeOf;

    public Component(String chemicalName, boolean isCombination, @Nullable Supplier<MaterialRegistryObject> baseMaterial, @Nullable Supplier<FluidRegistryObject<?, ?, ?, ?>> fluidSupplier, @Nullable ComponentStack... madeOf) {
        this.baseMaterial = baseMaterial != null ? baseMaterial : () -> null;
        this.chemicalName = chemicalName;
        this.isCombination = isCombination;
        this.fluidSupplier = new Either<>(null, fluidSupplier);
        this.madeOf = madeOf;
    }

    public Component(String chemicalName, boolean isCombination, @Nullable Supplier<Fluid> fluidSupplier) {
        this(chemicalName, isCombination, () -> null, null, (ComponentStack[]) null);
        this.fluidSupplier = new Either<>(fluidSupplier, null);
    }

    public Component(String chemicalName, boolean isCombination, @Nullable Supplier<Fluid> fluidSupplier, @Nullable ComponentStack... madeOf) {
        this(chemicalName, isCombination, () -> null, null, madeOf);
        this.fluidSupplier = new Either<>(fluidSupplier, null);
    }

    @Nullable
    public ComponentStack[] getMadeOf() {
        return madeOf;
    }

    @Nullable
    public Either<Supplier<Fluid>, Supplier<FluidRegistryObject<?,?,?,?>>> getFluid() {
        return fluidSupplier;
    }

    @Nullable
    public MaterialRegistryObject getBaseMaterial() {
        return baseMaterial.get();
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public boolean isCombination() {
        return isCombination;
    }
}
