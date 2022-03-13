package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.fluid.Fluids;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Components {
    public static final Component ALUMINIUM = new Component("Al", false, () -> null, null);
    public static final Component BERYLLIUM = new Component("Be", false, () -> null, null);
    public static final Component UNKNOWN = new Component("?", false, () -> null, null);
    public static final Component ANCIENT_DEBRIS = new Component("?", false, () -> null, null);
    public static final Component IRON = new Component("Fe", false, () -> Materials.IRON, null);
    public static final Component COPPER = new Component("Cu", false, () -> Materials.COPPER, null);
    public static final Component TIN = new Component("Sn", false, () -> Materials.TIN, null);
    public static final Component GOLD = new Component("Au", false, () -> Materials.GOLD, null);
    public static final Component SILVER = new Component("Ag", false, () -> Materials.SILVER, null);
    public static final Component LEAD = new Component("Pb", false, () -> Materials.LEAD, null);
    public static final Component CARBON = new Component("C", false, () -> null, null);
    public static final Component SILICON = new Component("Si", false, () -> Materials.SILICON, null);
    public static final Component CHROME = new Component("Cr", false, () -> Materials.CHROME, null);
    public static final Component SULFUR = new Component("S", false, () -> Materials.SULFUR, null);
    public static final Component OXYGEN = new Component("O", false, () -> Materials.OXYGEN, null);
    public static final Component HYDROGEN = new Component("H", false, () -> Materials.HYDROGEN, null);
    public static final Component PYRITE = new Component("FeS2", true, null, new ComponentStack(IRON), new ComponentStack(SULFUR, 2));
    public static final Component SILICON_DIOXIDE = new Component("SiO2", true, null, new ComponentStack(SILICON), new ComponentStack(OXYGEN, 2));
    public static final Component RUBBER = new Component("C5H8", true, null, new ComponentStack(CARBON, 5), new ComponentStack(HYDROGEN, 8));
    public static final Component WATER = new Component("H2O", true, () -> Fluids.WATER, new ComponentStack(HYDROGEN, 2), new ComponentStack(OXYGEN, 1));
}
