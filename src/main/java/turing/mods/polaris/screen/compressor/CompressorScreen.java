package turing.mods.polaris.screen.compressor;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import turing.mods.polaris.client.ModularUIs;
import turing.mods.polaris.container.compressor.CompressorContainer;
import turing.mods.polaris.screen.MachineScreen;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CompressorScreen extends MachineScreen<CompressorContainer> {
    public CompressorScreen(Container container, PlayerInventory inv, ITextComponent name) {
        super((CompressorContainer) container, inv, name, ModularUIs.COMPRESSOR_UI);
    }
}

    /*@Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float pt, int x, int y) {
        if (this.minecraft == null) return;
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        int rel1X = (this.width - this.getXSize()) / 2;
        int rel1Y = (this.height - this.getYSize()) / 2;
        int progress = ((CompressorContainer) this.container).getProgress();

        this.minecraft.getTextureManager().bindTexture(Polaris.modLoc("textures/gui/modular/base.png"));
        this.blit(matrixStack, rel1X, rel1Y, 0, 0, this.getXSize(), this.getYSize() + 12);

        this.minecraft.getTextureManager().bindTexture(Polaris.modLoc("textures/gui/modular/slot.png"));
        blit(matrixStack, rel1X + 51, rel1Y + 26, 0, 0, 18, 18, 18, 18);
        blit(matrixStack, rel1X + 101, rel1Y + 26, 0, 0, 18, 18, 18, 18);

        this.minecraft.getTextureManager().bindTexture(Polaris.modLoc("textures/gui/modular/progress/compressor.png"));
        blit(matrixStack, rel1X + 76, rel1Y + 25, 0, 0, 19, 14, 19, 20);
        blit(matrixStack, rel1X + 76, rel1Y + 34, 0, 18, progress, 3, 19, 20);
    }*/
//}
