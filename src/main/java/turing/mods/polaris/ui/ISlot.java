package turing.mods.polaris.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public interface ISlot<C> extends IUIComponent {
    Vector2i getTexturePos();

    Vector2i getTextureSize();

    default Vector2i getSize() {
        return getTextureSize();
    }

    ResourceLocation getTexture();

    <c extends MachineContainer> C getContained(c container);

    @Override
    default void addTooltips(MachineScreen<? extends MachineContainer> screen, List<ITextComponent> tooltips, Vector2i screenSize, MatrixStack matrixStack, int x, int y) {
        addTooltips(tooltips, screen, screenSize, matrixStack, x, y);
    }

    default <T extends MachineScreen<? extends MachineContainer>> void addTooltips(List<ITextComponent> tooltips, T screen, Vector2i screenSize, MatrixStack matrixStack, int x, int y) {}

    @Override
    default void renderBackground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {
        render(screen, matrixStack, screenSize, new Vector2i(18, 18), helper);
    }

    @Override
    default void renderForeground(MachineScreen<? extends MachineContainer> screen, MatrixStack matrixStack, Vector2i screenSize, TextureHelper helper) {

    }

    default <T extends MachineScreen<? extends MachineContainer>> void render(T screen, MatrixStack matrixStack, Vector2i screenSize, Vector2i renderSize, TextureHelper helper) {
        if (helper.getCurrentTexture() == null || !helper.getCurrentTexture().equals(getTexture())) screen.getMinecraft().getTextureManager().bindTexture(getTexture());

        Vector2i pos = new Vector2i((screen.width - screenSize.x) / 2, (screen.height - screenSize.y) / 2).add(getPos());
        Vector2i texturePos = getTexturePos();
        Vector2i textureSize = getTextureSize();

        Screen.blit(matrixStack, pos.x, pos.y, texturePos.x, texturePos.y, renderSize.x, renderSize.y, textureSize.x, textureSize.y);
    }
}
