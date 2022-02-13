package turing.mods.polaris.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.function.Supplier;

public class BucketItemGenerated extends BucketItem {
    public BucketItemGenerated(Supplier<? extends Fluid> supplier) {
        super(supplier, new Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ItemGroup.TAB_MISC));
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        return new TranslationTextComponent("subItem.polaris.bucket", I18n.get(getFluid().getAttributes().getTranslationKey()));
    }
}
