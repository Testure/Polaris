package turing.mods.polaris.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.ui.ModularUI;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MachineScreen<T extends MachineContainer> extends ContainerScreen<MachineContainer> implements IHasContainer<MachineContainer> {
    protected final ModularUI ui;
    public final Vector2i screenSize;

    public MachineScreen(T container, PlayerInventory inv, ITextComponent name, ModularUI ui) {
        super(container, inv, new StringTextComponent(""));
        this.ui = ui;
        this.screenSize = new Vector2i(this.getXSize(), this.getYSize());
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float pt) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, pt);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderHoveredTooltip(MatrixStack matrixStack, int x, int y) {
        super.renderHoveredTooltip(matrixStack, x, y);
        ui.renderTooltip(this, matrixStack, screenSize, x, y);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        super.drawGuiContainerForegroundLayer(matrixStack, x, y);
        ui.renderForeground(this, matrixStack, screenSize, x ,y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        ui.renderBackground(this, matrixStack, screenSize, x, y);
    }
}
