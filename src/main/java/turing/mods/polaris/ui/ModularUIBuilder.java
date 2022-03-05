package turing.mods.polaris.ui;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
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

    public ModularUIBuilder itemSlot(Vector2i pos) {
        if (hasAddedFluid) throw new IllegalStateException("Attempt to add item slot after adding fluid slot. item slots must be created first.");
        slots.add(new Slot.ItemSlot(slots.size(), pos));
        return this;
    }

    public ModularUIBuilder itemSlot(int x, int y) {
        return itemSlot(new Vector2i(x, y));
    }

    public ModularUIBuilder fluidSlot(Vector2i pos) {
        hasAddedFluid = true;
        slots.add(new Slot.FluidSlot(slots.size(), pos));
        return this;
    }

    public ModularUIBuilder fluidSlot(int x, int y) {
        return fluidSlot(new Vector2i(x, y));
    }

    public ModularUIBuilder addComponent(IUIComponent component) {
        components.add(component);
        return this;
    }

    public ModularUI build(Consumer<ModularUI> consumer) {
        ModularUI ui = new ModularUI(base, new SlotInfoProvider(slots.toArray(new ISlot[0])), components.toArray(new IUIComponent[0]));
        consumer.accept(ui);
        return ui;
    }
}
