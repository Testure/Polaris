package turing.mods.polaris.material;

import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum SubItem {
    INGOT,
    DUST,
    SMALL_DUST,
    TINY_DUST,
    GEAR,
    SMALL_GEAR,
    ROD,
    BOLT,
    SCREW,
    ROTOR,
    PLATE,
    FOIL,
    NUGGET,
    BLOCK,
    SPRING,
    SMALL_SPRING,
    RING,
    WIRE_SINGLE,
    WIRE_DOUBLE,
    WIRE_QUAD,
    WIRE_OCT,
    WIRE_SEXT,
    FINE_WIRE,
    CABLE_SINGLE,
    CABLE_DOUBLE,
    CABLE_QUAD,
    CABLE_OCT,
    CABLE_SEXT,
    ORE(false, true),
    CRUSHED_ORE(false, true),
    LENS,
    SWORD(false, true, true),
    AXE(false, true, true),
    PICKAXE(false, true, true),
    SHOVEL(false, true, true),
    HOE(false, true, true),
    CROWBAR(true, false, true),
    WRENCH(true, false, true),
    HAMMER(false, true, true),
    SOFT_HAMMER(false, true, true),
    MORTAR(false, true, true),
    SAW(false, true, true),
    FILE(false, true, true),
    SCREWDRIVER(false, true, true),
    GEM;

    private final boolean layer0Color;
    private final boolean layer1Color;
    private final boolean tool;

    SubItem(boolean layer0Color, boolean layer1Color, boolean tool) {
        this.layer0Color = layer0Color;
        this.layer1Color = layer1Color;
        this.tool = tool;
    }

    SubItem(boolean layer0Color, boolean layer1Color) {
        this(layer0Color, layer1Color, false);
    }

    SubItem() {
        this(true, false, false);
    }

    public boolean isLayer0Color() {
        return layer0Color;
    }

    public boolean isLayer1Color() {
        return layer1Color;
    }

    public boolean isTool() {
        return tool;
    }

    @Nullable
    public static ToolType getToolType(@Nonnull SubItem subItem) {
        if (!subItem.isTool()) return null;
        return ToolType.get(subItem.name().toLowerCase());
    }
}
