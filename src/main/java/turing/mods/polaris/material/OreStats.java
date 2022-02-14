package turing.mods.polaris.material;

import net.minecraft.util.ResourceLocation;
import turing.mods.polaris.Polaris;

import javax.annotation.Nullable;

public class OreStats {
    protected int smeltAmount;
    protected String texture;
    protected String textureModID;

    protected OreStats(String texture, String textureModID, int smeltAmount) {
        this.smeltAmount = smeltAmount;
        this.texture = texture;
        this.textureModID = textureModID;
    }

    public ResourceLocation getTexture() {
        return Polaris.modLoc(this.texture, this.textureModID);
    }

    public int getSmeltAmount() {
        return this.smeltAmount;
    }

    public static class Builder {
        protected String texture = "block/material_sets/metal/ore";
        protected String textureModID = "polaris";
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

        public Builder texture(String v, @Nullable String i) {
            this.texture = v;
            this.textureModID = i != null ? i : "polaris";
            return this;
        }

        public OreStats build() {
            return new OreStats(this.texture, this.textureModID, this.smeltAmount);
        }
    }
}
