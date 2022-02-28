package turing.mods.polaris.screen.compressor;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.container.compressor.CompressorContainer;
import turing.mods.polaris.screen.MachineScreen;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CompressorScreen extends MachineScreen<CompressorContainer> {
    public CompressorScreen(Container container, PlayerInventory inv, ITextComponent name) {
        super((CompressorContainer) container, inv, name, Polaris.modLoc("textures/gui/compressor.png"), new TranslationTextComponent("screen.polaris.compressor"));
    }

    @Override
    protected void renderHoveredTooltip(MatrixStack matrixStack, int x, int y) {
        super.renderHoveredTooltip(matrixStack, x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float pt, int x, int y) {
        super.drawGuiContainerBackgroundLayer(matrixStack, pt, x, y);

        int rel1X = (this.width - this.getXSize()) / 2;
        int rel1Y = (this.height - this.getYSize()) / 2;
        int progress = ((CompressorContainer) this.container).getProgress();

        this.blit(matrixStack, rel1X + 76, rel1Y + 34, 189, 0, progress, 3);
    }
}
