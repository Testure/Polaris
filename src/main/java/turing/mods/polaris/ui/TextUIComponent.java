package turing.mods.polaris.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class TextUIComponent implements IUIComponent {
    private final ITextComponent text;
    private final Vector2i pos;
    private final Vector2i size;
    private final int color;

    public TextUIComponent(ITextComponent text, Vector2i pos, int color) {
        this.text = text;
        this.pos = pos;
        this.color = color;
        this.size = new Vector2i(Minecraft.getInstance().fontRenderer.getStringWidth(text.getString()), Minecraft.getInstance().fontRenderer.FONT_HEIGHT);
    }

    public TextUIComponent(ITextComponent text, Vector2i pos) {
        this(text, pos, 0xFFFFFF);
    }

    public TextUIComponent(ITextComponent text, Vector2i pos, TextFormatting color) {
        this(text, pos, color.isColor() && color.getColor() != null ? color.getColor() : 0xFFFFFF);
    }

    @Override
    public Vector2i getPos() {
        return pos;
    }

    @Override
    public Vector2i getSize() {
        return size;
    }

    @Override
    public void renderForeground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {
    }

    @Override
    public void renderBackground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {
        Vector2i topLeft = new Vector2i((screen.width - screenSize.x) / 2, (screen.height - screenSize.y) / 2);
        Screen.drawString(matrixStack, Minecraft.getInstance().fontRenderer, text, topLeft.x + pos.x, topLeft.y + pos.y, color);
    }
}
