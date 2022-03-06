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
import java.util.function.IntFunction;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class CustomProgressBarComponent extends ProgressBarUIComponent {
    private final IntFunction<Integer> progressSupplier;

    public CustomProgressBarComponent(DualTextureData barTexture, Vector2i offPos, Vector2i onPos, IntFunction<Integer> progressSupplier) {
        super(barTexture, offPos, onPos);
        this.progressSupplier = progressSupplier;
    }

    @Override
    public void renderBackground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {
        if (helper.getCurrentTexture() == null || !helper.getCurrentTexture().equals(txt.getTxt())) helper.bindTexture(txt.getTxt());
        Vector2i topLeft = new Vector2i((screen.width - screenSize.x) / 2, (screen.height - screenSize.y) / 2);
        Vector2i pos = topLeft.copy().add(this.offPos);
        Vector2i onPos = topLeft.copy().add(this.onPos);
        Vector2i txtSize = txt.getSize();
        int progress = progressSupplier.apply(onSize.x);

        Screen.blit(matrixStack, pos.x, pos.y, offTxt.x, offTxt.y, this.txtSize.x, this.txtSize.y, txtSize.x, txtSize.y);
        Screen.blit(matrixStack, onPos.x, onPos.y, onTxt.x, onTxt.y, progress, onSize.y, txtSize.x, txtSize.y);
    }
}
