package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.enchantment.EnchantmentData;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ToolStats {
    protected int durability;
    protected int enchantability;
    protected float miningSpeed;
    protected float attackDamage;
    protected float attackSpeed;
    protected EnchantmentData[] enchantments;

    protected ToolStats(int durability, int enchantability, float miningSpeed, float attackDamage, float attackSpeed, EnchantmentData[] enchantments) {
        this.durability = durability;
        this.enchantability = enchantability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.enchantments = enchantments;
    }

    protected ToolStats(int durability, int enchantability, float miningSpeed, float attackDamage, float attackSpeed) {
        this(durability, enchantability, miningSpeed, attackDamage, attackSpeed, null);
    }

    public int getDurability() {
        return durability;
    }

    public int getEnchantability() {
        return enchantability;
    }

    public float getMiningSpeed() {
        return miningSpeed;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    @Nullable
    public EnchantmentData[] getDefaultEnchantments() {
        return enchantments;
    }

    public static class Builder {
        private int durability = 100;
        private int enchantability = 1;
        private float miningSpeed = 1.0F;
        private float attackDamage = 1.0F;
        private float attackSpeed = -2.0F;
        private EnchantmentData[] enchantments;

        protected Builder() {

        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder durability(int v) {
            this.durability = v;
            return this;
        }

        public Builder enchantability(int v) {
            this.enchantability = v;
            return this;
        }

        public Builder miningSpeed(float v) {
            this.miningSpeed = v;
            return this;
        }

        public Builder attackDamage(float v) {
            this.attackDamage = v;
            return this;
        }

        public Builder attackSpeed(float v) {
            this.attackSpeed = -v;
            return this;
        }

        public Builder withDefaultEnchantments(EnchantmentData... enchantments) {
            this.enchantments = enchantments;
            return this;
        }

        public ToolStats build() {
            return new ToolStats(this.durability, this.enchantability, this.miningSpeed, this.attackDamage, this.attackSpeed, this.enchantments);
        }
    }
}
