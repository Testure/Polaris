package turing.mods.polaris;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.item.ItemGroup;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
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
import turing.mods.polaris.itemgroups.ItemGroupMaterials;
import turing.mods.polaris.itemgroups.ItemGroupMisc;
import turing.mods.polaris.itemgroups.ItemGroupOres;
import turing.mods.polaris.itemgroups.ItemGroupTools;
import turing.mods.polaris.recipe.DefaultRecipes;
import turing.mods.polaris.recipe.IPromisedTag;
import turing.mods.polaris.registry.FluidRegistry;
import turing.mods.polaris.registry.Registration;

import java.util.ArrayList;
import java.util.List;

@Mod("polaris")
public class Polaris {
    public static final String MODID = "polaris";
    public static final Logger LOGGER = LogManager.getLogger("Polaris");
    public static final ItemGroup MATERIALS = new ItemGroupMaterials();
    public static final ItemGroup ORES = new ItemGroupOres();
    public static final ItemGroup TOOLS = new ItemGroupTools();
    public static final ItemGroup MISC = new ItemGroupMisc();

    public static final List<IPromisedTag> TAGS = new ArrayList<>();

    private static boolean hasResolvedBefore = false;
    public static boolean tagsResolved = false;

    public Polaris() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        Registration.register();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void resolveTags() {
        LOGGER.fatal("Resolving tags");
        hasResolvedBefore = true;
        tagsResolved = true;
        for (IPromisedTag tag : TAGS)
            if (!tag.isResolved()) tag.resolve();
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
                LOGGER.fatal("Reloading recipes");
                DefaultRecipes.clearRecipes();
                DefaultRecipes.addRecipes();
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
        tagsResolved = false;
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
