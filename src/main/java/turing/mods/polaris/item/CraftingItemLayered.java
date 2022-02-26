package turing.mods.polaris.item;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.util.Formatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CraftingItemLayered extends CraftingItem implements ILayeredItem {
    private final String layer0;
    private final String layer1;
    private Integer layer0Color;
    private Integer layer1Color;

    public CraftingItemLayered(String name, @Nullable String layer0, @Nullable String layer1, @Nullable Integer color, @Nullable Rarity rarity, @Nullable Integer maxStackSize, @Nullable List<ITextComponent> tooltips) {
        super(name, color, rarity, maxStackSize, tooltips);
        this.layer0 = layer0;
        this.layer1 = layer1;
    }

    public CraftingItemLayered(String name, @Nullable String layer0, @Nullable String layer1, String[] tooltips, @Nullable Integer color, @Nullable Rarity rarity, @Nullable Integer maxStackSize) {
        this(name, layer0, layer1, color, rarity, maxStackSize, Formatting.stringsToTextComponents(Arrays.asList(tooltips)));
    }

    public CraftingItemLayered(String name, @Nullable String layer0, @Nullable String layer1, List<String> tooltips, @Nullable Integer color, @Nullable Rarity rarity, @Nullable Integer maxStackSize) {
        this(name, layer0, layer1, color, rarity, maxStackSize, Formatting.stringsToTranslatedComponents(tooltips));
    }

    public CraftingItemLayered(String name, @Nullable String layer0, @Nullable String layer1, Map<String, List<String>> tooltips, @Nullable Integer color, @Nullable Rarity rarity, @Nullable Integer maxStackSize) {
        this(name, layer0, layer1, color, rarity, maxStackSize, Formatting.stringsToTranslatedComponents(tooltips));
    }

    public CraftingItemLayered(String name, @Nullable String layer0, @Nullable String layer1) {
        this(name, layer0, layer1, 0xFFFFFFFF, null, null, null);
    }

    public CraftingItemLayered(String name, Tuple<String, Integer> layer0, Tuple<String, Integer> layer1, @Nullable Rarity rarity, @Nullable Integer maxStackSize, @Nullable List<ITextComponent> tooltips) {
        this(name, layer0.getA(), layer1.getA(), layer0.getB(), rarity, maxStackSize, tooltips);
        this.layer0Color = layer0.getB();
        this.layer1Color = layer1.getB();
    }

    @Override
    public String getLayer0(String itemName) {
        return layer0 != null ? layer0 : ILayeredItem.super.getLayer0(itemName);
    }

    @Override
    public String getLayer1(String itemName) {
        return layer1 != null ? layer1 : ILayeredItem.super.getLayer1(itemName);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public int getColor(@Nonnull ItemStack itemStack, int layer) {
        if (layer0Color != null && layer1Color != null) {
            return layer == 0 ? layer0Color : layer1Color;
        }
        return super.getColor(itemStack, layer);
    }
}
