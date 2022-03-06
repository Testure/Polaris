package turing.mods.polaris.ui;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.MathUtil;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class UIAnchorPointConstraint<T extends MachineScreen<? extends MachineContainer>> implements IComponentConstraint<T> {
    protected Vector2f anchorPoint;
    protected final Vector2i size;

    public UIAnchorPointConstraint(Vector2f anchorPoint, Vector2i componentSize) {
        this.anchorPoint = clampVector2f(anchorPoint);
        this.size = componentSize;
    }

    private static Vector2f clampVector2f(Vector2f input) {
        return MathUtil.isWithinBoundsF(input.x, 0.0F, 1.0F) && MathUtil.isWithinBoundsF(input.y, 0.0F, 1.0F) ? input : new Vector2f(MathHelper.clamp(input.x, 0.0F, 1.0F), MathHelper.clamp(input.y, 0.0F, 1.0F));
    }

    public Vector2f getAnchorPoint() {
        return anchorPoint;
    }

    public void setAnchorPoint(Vector2f anchorPoint) {
        this.anchorPoint = clampVector2f(anchorPoint);
    }

    @Override
    public Vector2i apply(T screen, Vector2i original, Vector2i screenSize) {
        return anchorPoint.equals(Vector2f.ZERO) ? original : new Vector2i(original.x + (int)((float)size.x * anchorPoint.x), original.y + (int)((float) size.y * anchorPoint.y));
    }
}
