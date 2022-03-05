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
@FunctionalInterface
@OnlyIn(Dist.CLIENT)
public interface IComponentConstraint {
    <T extends MachineScreen<? extends MachineContainer>> Vector2i apply(T screen, Vector2i original, Vector2i screenSize);
}
