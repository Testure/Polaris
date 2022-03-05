package turing.mods.polaris.ui;

import turing.mods.polaris.util.Vector2i;

public class ContainerSlots {
    private final Vector2i[] slots;

    public ContainerSlots(Vector2i... slots) {
        this.slots = new Vector2i[slots.length];
        System.arraycopy(slots, 0, this.slots, 0, slots.length);
    }

    public Vector2i[] getSlots() {
        return this.slots;
    }
}
