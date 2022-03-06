package turing.mods.polaris.ui;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class UIPaddingConstraint<T extends MachineScreen<? extends MachineContainer>> implements IComponentConstraint<T> {
    private final Vector2i paddingTopLeft;
    private final Vector2i paddingBottomRight;

    public UIPaddingConstraint(Vector2i paddingTopLeft, Vector2i paddingBottomRight) {
        this.paddingTopLeft = paddingTopLeft;
        this.paddingBottomRight = paddingBottomRight;
    }

    @Override
    public Vector2i apply(T screen, Vector2i original, Vector2i screenSize) {
        Vector2i topLeft = new Vector2i((screen.width - screenSize.x) / 2, (screen.height - screenSize.y) / 2);
        Vector2i newOriginal = original.copy();

        if (original.x < (topLeft.x + paddingTopLeft.x)) newOriginal.x = (topLeft.x + paddingTopLeft.x);
        else if (original.x > (topLeft.x + (screenSize.x - paddingBottomRight.x))) newOriginal.x = (topLeft.x + (screenSize.x - paddingBottomRight.x));
        if (original.y < (topLeft.y + paddingTopLeft.y)) newOriginal.y = (topLeft.y + paddingTopLeft.y);
        else if (original.y > (topLeft.y + (screenSize.y - paddingBottomRight.y))) newOriginal.y = (topLeft.y + (screenSize.y - paddingBottomRight.y));

        if (!newOriginal.equals(original)) return newOriginal;
        return original;
    }
}
