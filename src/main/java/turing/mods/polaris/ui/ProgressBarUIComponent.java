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
    protected final TextureData txt;
    protected final Vector2i offTxt;
    protected final Vector2i onTxt;
    protected final Vector2i offPos;
    protected final Vector2i onPos;
    protected final Vector2i txtSize;
    protected final Vector2i onSize;

    public ProgressBarUIComponent(TextureData barTexture, Vector2i offPos, Vector2i onPos, Vector2i offTxtPos, Vector2i offTxtSize, Vector2i onTxtPos, Vector2i onSize) {
        this.txt = barTexture;
        this.offPos = offPos;
        this.onPos = onPos;
        this.offTxt = offTxtPos;
        this.onTxt = onTxtPos;
        this.txtSize = offTxtSize;
        this.onSize = onSize;
    }

    public ProgressBarUIComponent(DualTextureData barTexture, Vector2i offPos, Vector2i onPos) {
        this(barTexture, offPos, onPos, barTexture.getFirstPos(), barTexture.getFirstSize(), barTexture.getSecondPos(), barTexture.getSecondSize());
    }

    @Override
    public Vector2i getPos() {
        return offPos;
    }

    @Override
    public Vector2i getSize() {
        return txtSize;
    }

    @Override
    public void renderBackground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {
        if (helper.getCurrentTexture() == null || !helper.getCurrentTexture().equals(txt.getTxt())) helper.bindTexture(txt.getTxt());
        Vector2i topLeft = new Vector2i((screen.width - screenSize.x) / 2, (screen.height - screenSize.y) / 2);
        Vector2i pos = topLeft.copy().add(this.offPos);
        Vector2i onPos = topLeft.copy().add(this.onPos);
        Vector2i txtSize = txt.getSize();
        int progress = screen.getContainer().getProgress(onSize.x);

        Screen.blit(matrixStack, pos.x, pos.y, offTxt.x, offTxt.y, this.txtSize.x, this.txtSize.y, txtSize.x, txtSize.y);
        Screen.blit(matrixStack, onPos.x, onPos.y, onTxt.x, onTxt.y, progress, onSize.y, txtSize.x, txtSize.y);
    }

    @Override
    public void renderForeground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {

    }
}
