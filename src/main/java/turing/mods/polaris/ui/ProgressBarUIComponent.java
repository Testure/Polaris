package turing.mods.polaris.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class ProgressBarUIComponent implements IUIComponent {
    protected final DualTextureData txt;
    protected final UIPos offPos;
    protected final UIPos onPos;

    public ProgressBarUIComponent(DualTextureData barTexture, UIPos offPos, UIPos onPos) {
        this.txt = barTexture;
        this.offPos = offPos;
        this.onPos = onPos;
    }

    @Override
    public Vector2i getPos() {
        return offPos.pos;
    }

    @Override
    public Vector2i getSize() {
        return txt.getSize();
    }

    @Override
    public void renderBackground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {
        if (helper.getCurrentTexture() == null || !helper.getCurrentTexture().equals(txt.getTxt())) helper.bindTexture(txt.getTxt());
        Vector2i topLeft = new Vector2i((screen.width - screenSize.x) / 2, (screen.height - screenSize.y) / 2);
        Vector2i pos = topLeft.copy().add(this.offPos.getPos(screen, screenSize));
        Vector2i onPos = topLeft.copy().add(this.onPos.getPos(screen, screenSize));
        Vector2i txtSize = txt.getSize();
        int progress = screen.getContainer().getProgress(txt.getSecondSize().x);

        Screen.blit(matrixStack, pos.x, pos.y, txt.getFirstPos().x, txt.getFirstPos().y, txt.getFirstSize().x, txt.getFirstSize().y, txtSize.x, txtSize.y);
        Screen.blit(matrixStack, onPos.x, onPos.y, txt.getSecondPos().x, txt.getSecondPos().y, progress, txt.getSecondSize().y, txtSize.x, txtSize.y);
    }

    @Override
    public void renderForeground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {

    }
}
