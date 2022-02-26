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
import net.minecraft.util.text.StringTextComponent;
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
        super(supplier, new Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ItemGroup.TAB_MISC));
        this.materialSupplier = materialSupplier;
    }

    @Override
    public ITextComponent getName(ItemStack stack) {
        return new TranslationTextComponent(getDescriptionId(stack), I18n.get(getFluid().getAttributes().getTranslationKey()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        if (materialSupplier.get() != null)
            if (materialSupplier.get().getFluidTooltips() != null) RenderUtil.addFluidTooltips(materialSupplier.get(), tooltips, true);
        super.appendHoverText(stack, world, tooltips, flag);
    }

    @Override
    public String getDescriptionId() {
        return "subItem.polaris.bucket";
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return getDescriptionId();
    }
}
