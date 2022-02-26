package turing.mods.polaris.material;

import turing.mods.polaris.registry.MaterialRegistryObject;

import java.util.function.Supplier;

public class Component {
    protected final Supplier<MaterialRegistryObject> baseMaterial;
    protected final String chemicalName;
    protected boolean isCombination;

    public Component(String chemicalName, boolean isCombination, Supplier<MaterialRegistryObject> baseMaterial) {
        this.baseMaterial = baseMaterial;
        this.chemicalName = chemicalName;
        this.isCombination = isCombination;
    }

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
