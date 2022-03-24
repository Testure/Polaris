package turing.mods.polaris.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.Voltages;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MachinePart extends Item implements ITintedItem {
    private final int[] layerColors;
    public final int tier;
    private final String translationKey;

    public MachinePart(int[] layerColors, String translationKey, int tier) {
        super(new Properties().group(Polaris.MISC));
        this.layerColors = layerColors;
        this.tier = tier;
        this.translationKey = translationKey;
    }

    public static MachinePart[] forEachTier(int[] layerColors, String translationKey) {
        MachinePart[] parts = new MachinePart[Voltages.VOLTAGES.length];
        for (int i = 0; i < Voltages.VOLTAGES.length; i++) parts[i] = new MachinePart(layerColors, translationKey, i);
        return parts;
    }

    public static Supplier<MachinePart>[] toSuppliers(MachinePart[] machineParts) {
        Supplier<MachinePart>[] suppliers = new Supplier[machineParts.length];
        for (int i = 0; i < machineParts.length; i++) {
            int finalI = i;
            suppliers[i] = () -> machineParts[finalI];
        }
        return suppliers;
    }

    @Override
    public int getColor(ItemStack itemStack, int layer) {
        int layerColor = layer < layerColors.length ? layerColors[layer] : 0;
        return layerColor == 0 ? 0xFFFFFFFF : (layerColor == 1 ? Voltages.VOLTAGES[tier].partColor1 : Voltages.VOLTAGES[tier].partColor2);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return getName();
    }

    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent(translationKey, new TranslationTextComponent(Voltages.getVoltageTierTranslationKey(tier)));
    }
}
