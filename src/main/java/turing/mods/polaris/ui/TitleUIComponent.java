package turing.mods.polaris.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class TitleUIComponent implements IUIComponent {
    public static final UIPaddingConstraint TITLE_PADDING = new UIPaddingConstraint(new Vector2i(5, 5), Vector2i.ZERO);

    private final String title;
    private final Vector2i pos = Vector2i.ZERO;
    private final Vector2i size;

    public TitleUIComponent(String titleKey) {
        this.title = I18n.format(titleKey);
        this.size = new Vector2i(Minecraft.getInstance().fontRenderer.getStringWidth(title), Minecraft.getInstance().fontRenderer.FONT_HEIGHT);
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
    public void renderBackground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {
    }

    @Override
    public void renderForeground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {
        Vector2i pos = TITLE_PADDING.apply(screen, getPos(), new Vector2i(screen.width, screen.height));
        Screen.drawString(matrixStack, Minecraft.getInstance().fontRenderer, title, pos.x, pos.y, 0xFFFFFF);
    }
}
