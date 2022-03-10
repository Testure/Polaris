package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.fluid.Fluids;
import turing.mods.polaris.registry.MaterialRegistry;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Components {
    public static final Component IRON = new Component("Fe", false, () -> MaterialRegistry.IRON, null);
    public static final Component COPPER = new Component("Cu", false, () -> MaterialRegistry.COPPER, null);
    public static final Component TIN = new Component("Sn", false, () -> null, null);
    public static final Component GOLD = new Component("Au", false, () -> MaterialRegistry.GOLD, null);
    public static final Component SILVER = new Component("Ag", false, () -> MaterialRegistry.SILVER, null);
    public static final Component LEAD = new Component("Pb", false, () -> MaterialRegistry.LEAD, null);
    public static final Component CARBON = new Component("C", false, () -> null, null);
    public static final Component SILICON = new Component("Si", false, () -> MaterialRegistry.SILICON, null);
    public static final Component SULFUR = new Component("S", false, () -> MaterialRegistry.SULFUR, null);
    public static final Component OXYGEN = new Component("O", false, () -> MaterialRegistry.OXYGEN, null);
    public static final Component HYDROGEN = new Component("H", false, () -> MaterialRegistry.HYDROGEN, null);
    public static final Component RUBBER = new Component("C5H8", true, null, new ComponentStack(CARBON, 5), new ComponentStack(HYDROGEN, 8));
    public static final Component WATER = new Component("H2O", true, () -> Fluids.WATER, new ComponentStack(HYDROGEN, 2), new ComponentStack(OXYGEN, 1));
}
