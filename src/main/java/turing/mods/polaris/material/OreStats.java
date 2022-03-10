package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class OreStats {
    protected int smeltAmount;
    protected SubItem customSubItem;
    protected MaterialRegistryObject customMaterial;

    protected OreStats(int smeltAmount, @Nullable SubItem customSubItem, @Nullable MaterialRegistryObject customMaterial) {
        this.smeltAmount = smeltAmount;
        this.customSubItem = customSubItem;
        this.customMaterial = customMaterial;
    }

    public int getSmeltAmount() {
        return this.smeltAmount;
    }

    @Nullable
    public SubItem getCustomSubItem() {
        return this.customSubItem;
    }

    @Nullable
    public MaterialRegistryObject getCustomMaterial() {
        return customMaterial;
    }

    public static class Builder {
        protected int smeltAmount = 1;
        protected SubItem customSubItem;
        protected MaterialRegistryObject customMaterial;

        protected Builder() {

        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder setSmeltAmount(int v) {
            this.smeltAmount = v;
            return this;
        }

        public Builder setCustomSubItem(@Nullable SubItem subItem) {
            this.customSubItem = subItem;
            return this;
        }

        public Builder setCustomMaterial(@Nullable MaterialRegistryObject material) {
            this.customMaterial = material;
            return this;
        }

        public OreStats build() {
            return new OreStats(this.smeltAmount, customSubItem, customMaterial);
        }
    }
}
