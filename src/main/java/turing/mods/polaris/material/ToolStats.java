package turing.mods.polaris.material;

public class ToolStats {
    protected int durability;
    protected float enchantability;
    protected float miningSpeed;
    protected float attackDamage;

    protected ToolStats(int durability, float enchantability, float miningSpeed, float attackDamage) {
        this.durability = durability;
        this.enchantability = enchantability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
    }

    public int getDurability() {
        return durability;
    }

    public float getEnchantability() {
        return enchantability;
    }

    public float getMiningSpeed() {
        return miningSpeed;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public static class Builder {
        private int durability = 100;
        private float enchantability = 1.0F;
        private float miningSpeed = 1.0F;
        private float attackDamage = 1.0F;

        protected Builder() {

        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder durability(int v) {
            this.durability = v;
            return this;
        }

        public Builder enchantability(float v) {
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

        public ToolStats build() {
            return new ToolStats(this.durability, this.enchantability, this.miningSpeed, this.attackDamage);
        }
    }
}
