package turing.mods.polaris.ui;

import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.Vector2i;

public class UIPos {
    public final Vector2i pos;
    public final IComponentConstraint<?> constraint;

    public UIPos(Vector2i pos, IComponentConstraint<?> constraint) {
        this.pos = pos;
        this.constraint = constraint;
    }

    public UIPos(int x, int y, IComponentConstraint<?> constraint) {
        this(new Vector2i(x, y), constraint);
    }

    public UIPos(Vector2i pos) {
        this(pos, IComponentConstraint.NONE);
    }

    public UIPos(int x, int y) {
        this(new Vector2i(x, y));
    }

    public <T extends MachineScreen<? extends MachineContainer>> Vector2i getPos(T screen, Vector2i screenSize) {
        return ((IComponentConstraint<T>) constraint).apply(screen, pos, screenSize);
    }
}
