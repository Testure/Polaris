package turing.mods.polaris.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.Vector2i;

import java.util.List;

public interface IUIComponent {
    Vector2i getPos();

    Vector2i getSize();

    default boolean isWithinComponentRegion(int width, int height, Vector2i screenSize, double x, double y) {
        Vector2i renderSize = getSize();
        x = x - (double) (width - screenSize.x) / 2;
        y = y - (double) (height - screenSize.y) / 2;

        return x >= (double)(getPos().x - 1) && x < (double)(getPos().x + renderSize.x + 1) && y >= (double)(getPos().y - 1) && y < (double)(getPos().y + renderSize.y + 1);
    }

    default void addTooltips(MachineScreen<? extends MachineContainer> screen, List<ITextComponent> tooltips, Vector2i screenSize, MatrixStack matrixStack, int x, int y) {}

    void renderBackground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper);

    void renderForeground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper);
}
