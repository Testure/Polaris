package turing.mods.polaris.screen.compressor;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.container.compressor.CompressorContainer;
import turing.mods.polaris.screen.MachineScreen;

public class CompressorScreen extends MachineScreen<CompressorContainer> {
    public CompressorScreen(Container container, PlayerInventory inv, ITextComponent name) {
        super((CompressorContainer) container, inv, name, Polaris.modLoc("textures/gui/compressor.png"), new TranslationTextComponent("screen.polaris.compressor"));
    }

    @Override
    protected void renderTooltip(MatrixStack matrixStack, int x, int y) {
        super.renderTooltip(matrixStack, x, y);

    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float pt, int x, int y) {
        super.renderBg(matrixStack, pt, x, y);
        int rel1X = (this.width - this.getXSize()) / 2;
        int rel1Y = (this.height - this.getYSize()) / 2;
    }
}
