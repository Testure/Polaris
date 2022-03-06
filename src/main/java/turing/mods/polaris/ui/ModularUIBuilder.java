package turing.mods.polaris.ui;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class ModularUIBuilder {
    private final ResourceLocation base;
    private final List<ISlot<?>> slots = new ArrayList<>();
    private boolean hasAddedFluid;
    private List<IUIComponent> components = new ArrayList<>();

    private ModularUIBuilder(ResourceLocation base) {
        this.base = base;
    }

    public static ModularUIBuilder builder(@Nullable ResourceLocation baseTexture) {
        return new ModularUIBuilder(baseTexture != null ? baseTexture : ModularUI.DEFAULT_TEXTURE);
    }

    public static ModularUIBuilder builder() {
        return builder(null);
    }

    public ModularUIBuilder itemSlot(UIPos pos) {
        if (hasAddedFluid) throw new IllegalStateException("Attempt to add item slot after adding fluid slot. item slots must be created first.");
        slots.add(new Slot.ItemSlot(slots.size(), pos));
        return this;
    }

    public ModularUIBuilder itemSlot(int x, int y) {
        return itemSlot(new UIPos(x, y));
    }

    public ModularUIBuilder fluidSlot(UIPos pos) {
        hasAddedFluid = true;
        slots.add(new Slot.FluidSlot(slots.size(), pos));
        return this;
    }

    public ModularUIBuilder fluidSlot(int x, int y) {
        return fluidSlot(new UIPos(x, y));
    }

    public ModularUIBuilder addComponent(IUIComponent component) {
        components.add(component);
        return this;
    }

    public ModularUIBuilder addComponents(IUIComponent... components) {
        this.components.addAll(Arrays.asList(components));
        return this;
    }

    public ModularUIBuilder addTitle(String key) {
        return addComponent(new TitleUIComponent(key));
    }

    public ModularUIBuilder addText(ITextComponent text, UIPos pos) {
        return addComponent(new TextUIComponent(text, pos));
    }

    public ModularUIBuilder addText(String text, UIPos pos, int color) {
        return addComponent(new TextUIComponent(new StringTextComponent(text), pos, color));
    }

    public ModularUIBuilder addText(String text, UIPos pos, TextFormatting color) {
        return addComponent(new TextUIComponent(new StringTextComponent(text), pos, color));
    }

    public ModularUIBuilder addTranslatedText(String text, UIPos pos, int color) {
        return addComponent(new TextUIComponent(new TranslationTextComponent(text), pos, color));
    }

    public ModularUIBuilder addTranslatedText(String text, UIPos pos, TextFormatting color) {
        return addComponent(new TextUIComponent(new TranslationTextComponent(text), pos, color));
    }

    public ModularUIBuilder addProgressBar(DualTextureData texture, UIPos offPos, UIPos onPos) {
        return addComponent(new ProgressBarUIComponent(texture, offPos, onPos));
    }

    public ModularUIBuilder addProgressBar(DualTextureData texture, UIPos pos) {
        return addComponent(new ProgressBarUIComponent(texture, pos, pos));
    }

    public ModularUIBuilder addProgressBar(DualTextureData texture, UIPos offPos, UIPos onPos, boolean vertical) {
        return addComponent(!vertical ? new ProgressBarUIComponent(texture, offPos, onPos) : new VerticalProgressBarComponent(texture, offPos, onPos));
    }

    public ModularUI build(Consumer<ModularUI> consumer) {
        ModularUI ui = new ModularUI(base, new SlotInfoProvider(slots.toArray(new ISlot[0])), components.toArray(new IUIComponent[0]));
        consumer.accept(ui);
        return ui;
    }
}
