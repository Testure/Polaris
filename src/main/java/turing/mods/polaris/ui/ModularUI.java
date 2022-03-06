package turing.mods.polaris.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class ModularUI {
    public static final ResourceLocation DEFAULT_TEXTURE = Polaris.modLoc("textures/gui/modular/base.png");

    protected final ISlot<?>[] slots;
    protected final int itemSlotCount;
    protected final int fluidSlotCount;
    protected final IUIComponent[] components;
    protected final ResourceLocation baseTexture;
    protected final TextureHelper helper;

    public ModularUI(ResourceLocation base, SlotInfoProvider slots, IUIComponent[] components) {
        this.slots = slots.getSlots();
        this.components = components;
        this.baseTexture = base;
        this.helper = new TextureHelper(Minecraft.getInstance().getTextureManager());

        int i = 0;
        int f = 0;
        for (ISlot<?> slot : this.slots) {
            if (slot instanceof Slot.ItemSlot) i++;
            if (slot instanceof Slot.FluidSlot) f++;
        }
        this.itemSlotCount = i;
        this.fluidSlotCount = f;
    }

    protected ISlot<?> getSlot(int index) {
        return slots[index];
    }

    protected boolean isSlotSpecial(int index) {
        return getSlot(index).getTexture() != Slot.SLOTS_TEXTURE;
    }

    protected boolean hasSpecialSlot() {
        for (int i = 0; i < this.slots.length; i++) if (isSlotSpecial(i)) return true;
        return false;
    }

    protected int getSlotTankIndex(int slotIndex) {
        return slotIndex - itemSlotCount;
    }

    public void renderTooltip(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, int x, int y) {
        for (ISlot<?> slot : this.slots) {
            if (slot.isWithinComponentRegion(screen.width, screen.height, screenSize, x, y)) {
                List<ITextComponent> tooltips = new ArrayList<>();
                slot.addTooltips(screen, tooltips, screenSize, matrixStack, x, y);
                if (!tooltips.isEmpty()) {
                    screen.func_243308_b(matrixStack, tooltips, x, y);
                    return;
                }
            }
        }
        for (IUIComponent component : this.components) {
            if (component.isWithinComponentRegion(screen.width, screen.height, screenSize, x, y)) {
                List<ITextComponent> tooltips = new ArrayList<>();
                component.addTooltips(screen, tooltips, screenSize, matrixStack, x, y);
                if (!tooltips.isEmpty()) {
                    screen.func_243308_b(matrixStack, tooltips, x, y);
                    return;
                }
            }
        }
    }

    public void renderForeground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, int x, int y) {
        for (ISlot<?> slot : this.slots) slot.renderForeground(screen, matrixStack, screenSize, helper);
        for (IUIComponent component : this.components) component.renderForeground(screen, matrixStack, screenSize, this.helper);
    }

    public void renderBackground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, int x, int y) {
        Vector2i topLeft = new Vector2i((screen.width - screenSize.x) / 2, (screen.height - screenSize.y) / 2);

        this.helper.bindTexture(baseTexture);
        screen.blit(matrixStack, topLeft.x, topLeft.y, 0, 0, screenSize.x, screenSize.y);

        if (!hasSpecialSlot()) {
            this.helper.bindTexture(Slot.SLOTS_TEXTURE);
            for (ISlot<?> slot : this.slots) slot.renderBackground(screen, matrixStack, screenSize, this.helper);
        } else for (ISlot<?> slot : this.slots) slot.renderBackground(screen, matrixStack, screenSize, this.helper);

        for (IUIComponent component : this.components) component.renderBackground(screen, matrixStack, screenSize, this.helper);
    }
}
