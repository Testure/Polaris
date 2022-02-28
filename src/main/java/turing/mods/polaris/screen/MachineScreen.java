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
import net.minecraft.util.text.ITextComponent;
import turing.mods.polaris.container.MachineContainer;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MachineScreen<T extends MachineContainer> extends ContainerScreen<MachineContainer> implements IHasContainer<MachineContainer> {
    private final ResourceLocation gui;
    private final ITextComponent guiTitle;

    public MachineScreen(T container, PlayerInventory inv, ITextComponent name, ResourceLocation gui, ITextComponent guiTitle) {
        super(container, inv, name);
        this.gui = gui;
        this.guiTitle = guiTitle;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float pt) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, pt);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        drawString(matrixStack, Minecraft.getInstance().fontRenderer, guiTitle.getString(), 3, 3, 0xFFFFFF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float pt, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(gui);
        int rel1X = (this.width - this.getXSize()) / 2;
        int rel1Y = (this.height - this.getYSize()) / 2;

        this.blit(matrixStack, rel1X, rel1Y, 0, 0, this.getXSize(), this.getYSize());
    }
}
