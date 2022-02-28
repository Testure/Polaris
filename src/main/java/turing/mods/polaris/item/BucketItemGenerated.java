package turing.mods.polaris.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import turing.mods.polaris.material.Material;
import turing.mods.polaris.util.RenderUtil;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BucketItemGenerated extends BucketItem {
    protected Supplier<Material> materialSupplier;

    public BucketItemGenerated(Supplier<? extends Fluid> supplier, @Nullable Supplier<Material> materialSupplier) {
        super(supplier, new Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC));
        this.materialSupplier = materialSupplier;
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent(getTranslationKey(), I18n.format(getFluid().getAttributes().getTranslationKey()));
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return getName();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        if (materialSupplier.get() != null)
            if (materialSupplier.get().getFluidTooltips() != null) RenderUtil.addFluidTooltips(materialSupplier.get(), tooltips, true);
        super.addInformation(stack, world, tooltips, flag);
    }

    @Override
    public String getTranslationKey() {
        return "subItem.polaris.bucket";
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return getTranslationKey();
    }
}
