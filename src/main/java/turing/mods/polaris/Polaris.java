package turing.mods.polaris;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.item.ItemGroup;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tesseract.Tesseract;
import turing.mods.polaris.itemgroups.ItemGroupMaterials;
import turing.mods.polaris.itemgroups.ItemGroupMisc;
import turing.mods.polaris.itemgroups.ItemGroupOres;
import turing.mods.polaris.itemgroups.ItemGroupTools;
import turing.mods.polaris.recipe.IPromisedTag;
import turing.mods.polaris.registry.FluidRegistry;
import turing.mods.polaris.registry.Registration;
import turing.mods.polaris.util.Lists;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mod("polaris")
public class Polaris {
    public static final String MODID = "polaris";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup MATERIALS = new ItemGroupMaterials();
    public static final ItemGroup ORES = new ItemGroupOres();
    public static final ItemGroup TOOLS = new ItemGroupTools();
    public static final ItemGroup MISC = new ItemGroupMisc();

    public static final List<IPromisedTag> TAGS = new ArrayList<>();

    private static boolean hasResolvedBefore = false;

    public static class VOLTAGES {
        public static final List<Tuple<Integer, String>> VOLTAGE_LIST = Lists.listOf(
                new Tuple<>(8, "ulv"),
                new Tuple<>(32, "lv"),
                new Tuple<>(128, "mv"),
                new Tuple<>(512, "hv"),
                new Tuple<>(2048, "ev"),
                new Tuple<>(8192, "iv")
        );

        public static final int ULV = VOLTAGE_LIST.get(0).getA();
        public static final int LV = VOLTAGE_LIST.get(1).getA();
        public static final int MV = VOLTAGE_LIST.get(2).getA();
        public static final int HV = VOLTAGE_LIST.get(3).getA();
        public static final int EV = VOLTAGE_LIST.get(4).getA();
        public static final int IV = VOLTAGE_LIST.get(5).getA();

        @Nullable
        public static String voltageToString(int voltage) {
            for (int i = 0; i < VOLTAGE_LIST.size(); i++) {
                if (voltage <= VOLTAGE_LIST.get(i).getA() && ((i + 1) >= VOLTAGE_LIST.size() || voltage < VOLTAGE_LIST.get(i + 1).getA())) return VOLTAGE_LIST.get(i).getB();
            }
            return null;
        }

        public static String getVoltageTranslationKey(int voltage) {
            String str = voltageToString(voltage);
            return "voltage.polaris." + (str != null ? str : "ulv");
        }

        public static String getVoltageTierTranslationKey(int tier) {
            return getVoltageTranslationKey(VOLTAGE_LIST.get(tier).getA());
        }

        public static int getEnergyCapacity(int voltage) {
            return voltage * voltage;
        }
    }

    public Polaris() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        Registration.register();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void resolveTags() {
        LOGGER.info("Resolving tags");
        hasResolvedBefore = true;
        for (IPromisedTag tag : TAGS) {
            if (!tag.isResolved()) tag.resolve();
        }
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
    public void reloadRecipes(AddReloadListenerEvent event) {
        event.addListener(new ReloadListener<Void>() {
            @Override
            protected Void prepare(IResourceManager resourceManager, IProfiler profiler) {
                return null;
            }

            @Override
            protected void apply(Void object, IResourceManager resourceManager, IProfiler profiler) {
                LOGGER.info("Reloading tags");

                if (hasResolvedBefore) {
                    resolveTags();
                }
            }
        });
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        resolveTags();
    }

    @SubscribeEvent
    public void onServerClosing(FMLServerStoppedEvent event) {
        hasResolvedBefore = false;
        for (IPromisedTag tag : TAGS) {
            if (tag.isResolved()) tag.unResolve();
        }
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
