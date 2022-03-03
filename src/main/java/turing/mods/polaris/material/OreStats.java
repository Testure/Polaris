package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class OreStats {
    protected int smeltAmount;

    protected OreStats(int smeltAmount) {
        this.smeltAmount = smeltAmount;
    }

    public int getSmeltAmount() {
        return this.smeltAmount;
    }

    public static class Builder {
        protected int smeltAmount = 1;

        protected Builder() {

        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder setSmeltAmount(int v) {
            this.smeltAmount = v;
            return this;
        }

        public OreStats build() {
            return new OreStats(this.smeltAmount);
        }
    }
}
