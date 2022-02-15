package turing.mods.polaris.block;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.material.Material;
import turing.mods.polaris.material.SubItem;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class SubBlockItemGenerated extends BlockItem {
    private final Supplier<Material> material;
    private final SubItem subItem;

    public SubBlockItemGenerated(SubBlockGenerated block, Supplier<Material> material) {
        super(block, new Properties().tab(block.getSubItem() == SubItem.ORE ? Polaris.ORES : Polaris.MATERIALS));
        this.material = material;
        this.subItem = block.getSubItem();
    }

    public SubItem getSubItem() {
        return subItem;
    }

    public Material getMaterial() {
        return material.get();
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return "block.polaris." + subItem.name().toLowerCase();
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getName(@Nonnull ItemStack stack) {
        return getDescription();
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getDescription() {
        return new TranslationTextComponent(getDescriptionId(), new TranslationTextComponent("material.polaris." + getMaterial().getName()));
    }
}
