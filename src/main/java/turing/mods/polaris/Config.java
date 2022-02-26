package turing.mods.polaris;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue SHOW_MACHINE_FLAVOR_TEXT;
    public static ForgeConfigSpec.IntValue MACHINE_BASE_COLOR;
    public static ForgeConfigSpec.BooleanValue MACHINE_TIER_COLORS;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        setCommonConfig(COMMON_BUILDER);
        setServerConfig(SERVER_BUILDER);
        setClientConfig(CLIENT_BUILDER);

        COMMON_CONFIG = COMMON_BUILDER.build();
        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setCommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("General Settings").push("general");
            MACHINE_TIER_COLORS = builder.comment("If true, machine colors will be the color of their tier.").comment("default: false").define("machine_color_by_tier", false);
            MACHINE_BASE_COLOR = builder.comment("If the above property is false, all machine are tinted to this color.").comment("default: 0xFFD2DCFF (classic GT blue)").comment("white: 0xFFFFFFFF").defineInRange("machine_base_color", 0xFFD2DCFF, 0, Integer.MAX_VALUE);
        builder.pop();
    }

    private static void setServerConfig(ForgeConfigSpec.Builder builder) {

    }

    private static void setClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("General Settings").push("general");
            SHOW_MACHINE_FLAVOR_TEXT = builder.comment("Determines if machines have a flavor text tooltip").comment("default: true").define("flavor_text", true);
        builder.pop();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading event) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading event) {

    }
}
