package turing.mods.polaris.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.util.Formatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CraftingItem extends Item implements IBasicModeledItem, ITintedItem {
    private final int color;
    private final Rarity rarity;
    private final List<ITextComponent> tooltips;

    public CraftingItem(String name, @Nullable Integer color, @Nullable Rarity rarity, @Nullable Integer maxStackSize, @Nullable List<ITextComponent> tooltips) {
        super(new Properties().stacksTo(maxStackSize != null ? maxStackSize : 64).tab(Polaris.MISC));
        this.color = color != null ? color : 0xFFFFFFFF;
        this.rarity = rarity;
        this.tooltips = tooltips != null ? tooltips : new ArrayList<>();
    }

    public CraftingItem(String name, String[] tooltips, @Nullable Integer color, @Nullable Rarity rarity, @Nullable Integer maxStackSize) {
        this(name, color, rarity, maxStackSize, Formatting.stringsToTextComponents(Arrays.asList(tooltips)));
    }

    public CraftingItem(String name, List<String> tooltips, @Nullable Integer color, @Nullable Rarity rarity, @Nullable Integer maxStackSize) {
        this(name, color, rarity, maxStackSize, Formatting.stringsToTranslatedComponents(tooltips));
    }

    public CraftingItem(String name, Map<String, List<String>> tooltips, @Nullable Integer color, @Nullable Rarity rarity, @Nullable Integer maxStackSize) {
        this(name, color, rarity, maxStackSize, Formatting.stringsToTranslatedComponents(tooltips));
    }

    public CraftingItem(String name) {
        this(name, 0xFFFFFFFF, null, null, null);
    }

    @Override
    public int getColor(@Nonnull ItemStack itemStack, int layer) {
        return color;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return rarity != null ? rarity : super.getRarity(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.addAll(this.tooltips);
        super.appendHoverText(stack, world, tooltips, flag);
    }
}
