package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FluidStats {
    protected boolean gas;
    public int temp;

    protected FluidStats(boolean gas, int temp) {
        this.gas = gas;
        this.temp = temp;
    }

    public boolean isGaseous() {
        return gas;
    }

    public static class Builder {
        private boolean gas = false;
        private int temp = 300;

        protected Builder() {

        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder isGaseous(boolean v) {
            this.gas = v;
            return this;
        }

        public Builder temperature(int v) {
            this.temp = v;
            return this;
        }

        public FluidStats build() {
            return new FluidStats(this.gas, this.temp);
        }
    }
}
