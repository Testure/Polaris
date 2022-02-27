package turing.mods.polaris.material;

import net.minecraft.fluid.Fluids;
import turing.mods.polaris.registry.MaterialRegistry;

public class Components {
    public static final Component IRON = new Component("Fe", false, () -> MaterialRegistry.IRON, null);
    public static final Component COPPER = new Component("Cu", false, () -> MaterialRegistry.COPPER, null);
    public static final Component TIN = new Component("Sn", false, () -> null, null);
    public static final Component GOLD = new Component("Au", false, () -> MaterialRegistry.GOLD, null);
    public static final Component SILVER = new Component("Ag", false, () -> MaterialRegistry.SILVER, null);
    public static final Component LEAD = new Component("Pb", false, () -> MaterialRegistry.LEAD, null);
    public static final Component OXYGEN = new Component("O", false, () -> MaterialRegistry.OXYGEN, null);
    public static final Component HYDROGEN = new Component("H", false, () -> MaterialRegistry.HYDROGEN, null);
    public static final Component WATER = new Component("H2O", true, () -> Fluids.WATER, new ComponentStack(HYDROGEN, 2), new ComponentStack(OXYGEN, 1));
}
