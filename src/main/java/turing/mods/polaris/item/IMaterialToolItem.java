package turing.mods.polaris.item;

public interface IMaterialToolItem extends ITintedItem, IHandheldItem, ILayeredItem {
    void getMaterial();

    int getDurability();

    float getEnchantability();

    float getAttackDamage();

    float getMiningSpeed();
}
