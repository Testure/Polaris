package turing.mods.polaris;

import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import turing.mods.polaris.itemgroups.ItemGroupMaterials;
import turing.mods.polaris.itemgroups.ItemGroupMisc;
import turing.mods.polaris.itemgroups.ItemGroupOres;
import turing.mods.polaris.itemgroups.ItemGroupTools;
import turing.mods.polaris.registry.FluidRegistry;
import turing.mods.polaris.registry.Registration;

@Mod("polaris")
public class Polaris {
    public static final String MODID = "polaris";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup MATERIALS = new ItemGroupMaterials();
    public static final ItemGroup ORES = new ItemGroupOres();
    public static final ItemGroup TOOLS = new ItemGroupTools();
    public static final ItemGroup MISC = new ItemGroupMisc();

    public static class VOLTAGES {
        public static final int LV = 32;
        public static final int MV = 128;
        public static final int HV = 512;
        public static final int EV = 1024;
    }

    public Polaris() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        Registration.register();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        FluidRegistry.registerDispenseBehavior();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        //InterModComms.sendTo("polaris", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event) {
        //LOGGER.info("Got IMC {}", event.getIMCStream().
                //map(m->m.getMessageSupplier().get()).
                //collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    public static ResourceLocation modLoc(String loc, String modid) {
        return new ResourceLocation(modid, loc);
    }

    public static ResourceLocation modLoc(String loc) {
        return modLoc(loc, MODID);
    }

    public static ResourceLocation mcLoc(String loc) {
        return modLoc(loc, "minecraft");
    }

    public static ResourceLocation forgeLoc(String loc) {
        return modLoc(loc, "forge");
    }
}
