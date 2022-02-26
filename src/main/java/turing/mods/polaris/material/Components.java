package turing.mods.polaris.material;

import turing.mods.polaris.registry.MaterialRegistry;

public class Components {
    public static final Component IRON = new Component("Fe", false, () -> MaterialRegistry.IRON);
    public static final Component COPPER = new Component("Cu", false, () -> MaterialRegistry.COPPER);
    public static final Component TIN = new Component("Sn", false, () -> null);
    public static final Component GOLD = new Component("Au", false, () -> null);
}
