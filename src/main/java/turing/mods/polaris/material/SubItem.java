package turing.mods.polaris.material;

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
    ORE(false, true),
    CRUSHED_ORE(false, true),
    LENS,
    SWORD(false, true, true),
    AXE(false, true, true),
    PICKAXE(false, true, true),
    SHOVEL(false, true, true),
    HOE(false, true, true),
    CROWBAR(false, true, true),
    WRENCH(true, false, true),
    HAMMER(false, true, true),
    SOFT_HAMMER(false, true, true),
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
}
