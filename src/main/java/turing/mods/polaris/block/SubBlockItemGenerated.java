package turing.mods.polaris.block;

import mcp.MethodsReturnNonnullByDefault;
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
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SubBlockItemGenerated extends BlockItem {
    private final Supplier<Material> material;
    private final SubItem subItem;

    public SubBlockItemGenerated(SubBlockGenerated block, Supplier<Material> material) {
        super(block, new Properties().group(block.getSubItem() == SubItem.ORE ? Polaris.ORES : Polaris.MATERIALS));
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
    public String getTranslationKey() {
        return "block.polaris." + subItem.name().toLowerCase();
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getDisplayName(@Nonnull ItemStack stack) {
        return getName();
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public ITextComponent getName() {
        return new TranslationTextComponent(getTranslationKey(), new TranslationTextComponent("material.polaris." + getMaterial().getName()));
    }
}
