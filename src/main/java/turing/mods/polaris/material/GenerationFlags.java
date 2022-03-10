package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public enum GenerationFlags {
    GENERATE_GEAR,
    GENERATE_ROD,
    GENERATE_SMALL_GEAR,
    GENERATE_SCREW,
    GENERATE_LENS,
    GENERATE_SPRING,
    GENERATE_FOIL,
    NO_BLOCK,
    NO_COMPRESSION,
    NO_MORTAR_GRINDING,
    NO_DUST_SMELTING,
    NO_ORE_SMELTING,
    NO_MANUAL_POLARIZATION,
    IS_SOFT,
    NO_VANILLA_TOOLS,
    NO_MORTAR,
}
