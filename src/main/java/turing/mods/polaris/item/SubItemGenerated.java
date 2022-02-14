package turing.mods.polaris.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.material.Material;
import turing.mods.polaris.material.SubItem;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class SubItemGenerated extends Item implements ITintedItem {
    protected final Supplier<Material> materialSupplier;
    protected final SubItem subItem;

    public SubItemGenerated(Supplier<Material> materialSupplier, SubItem subItem) {
        super(new Properties().tab(Polaris.MATERIALS));
        this.materialSupplier = materialSupplier;
        this.subItem = subItem;
    }

    public Material getMaterial() {
        return materialSupplier.get();
    }

    public SubItem getSubItem() {
        return subItem;
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        //TODO Translation overrides
        return new TranslationTextComponent("item.polaris." + subItem.name().toLowerCase(), new TranslationTextComponent("material.polaris." + getMaterial().getName()));
    }

    @Override
    public int getColor(@Nonnull ItemStack itemStack, int layer) {
        return getMaterial().getColor(itemStack, layer);
    }
}
