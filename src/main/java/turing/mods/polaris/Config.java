package turing.mods.polaris;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@Mod.EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Config {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue SHOW_MACHINE_FLAVOR_TEXT;
    public static Supplier<Integer> MACHINE_BASE_COLOR;
    public static ForgeConfigSpec.BooleanValue MACHINE_TIER_COLORS;
    public static ForgeConfigSpec.BooleanValue RUBBER_TREES;
    public static ForgeConfigSpec.BooleanValue ORE_VEINS;

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

    public static void register() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }

    private static void setCommonConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("General Settings").push("general");
            MACHINE_TIER_COLORS = builder.comment("If true, machine colors will be the color of their tier.", "default: false").define("machine_color_by_tier", false);
            ForgeConfigSpec.ConfigValue<String> color = builder.comment("If the above property is false, all machine are tinted to this color.", "default: " + 0xFFD2DCFF + " (classic GT blue)", "white: " + 0xFFFFFFFF).define("machine_base_color", Integer.toString(0xFFD2DCFF));
            MACHINE_BASE_COLOR = () -> Integer.parseInt(color.get());
        builder.pop();
        builder.comment("World Generation").push("world_gen");
            RUBBER_TREES = builder.comment("If true, rubber trees will generate in the world.", "default: true").define("rubber_trees", true);
            ORE_VEINS = builder.comment("Catch-all option to enable/disable all ore veins.", "true: ore veins defined via json will be generated", "false: no ore veins will be generated at all", "default: true").define("ore_veins", true);
        builder.pop();
    }

    private static void setServerConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("General Settings").push("general");

        builder.pop();
    }

    private static void setClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("General Settings").push("general");
            SHOW_MACHINE_FLAVOR_TEXT = builder.comment("Determines if machines have a flavor text tooltip", "default: true").define("flavor_text", true);
        builder.pop();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading event) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading event) {

    }
}
