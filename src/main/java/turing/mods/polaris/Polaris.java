package turing.mods.polaris;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
import turing.mods.polaris.block.SubBlockItemGenerated;
import turing.mods.polaris.item.SubItemGenerated;
import turing.mods.polaris.itemgroups.ItemGroupMaterials;
import turing.mods.polaris.itemgroups.ItemGroupMisc;
import turing.mods.polaris.itemgroups.ItemGroupOres;
import turing.mods.polaris.itemgroups.ItemGroupTools;
import turing.mods.polaris.recipe.DefaultRecipes;
import turing.mods.polaris.recipe.IPromisedTag;
import turing.mods.polaris.registry.FluidRegistry;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;
import turing.mods.polaris.registry.Registration;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod("polaris")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
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

    public static class ToolTypes {
        public static final ToolType WRENCH = ToolType.get("wrench");
        public static final ToolType HAMMER = ToolType.get("hammer");
        public static final ToolType SOFT_HAMMER = ToolType.get("soft_hammer");
        public static final ToolType CROWBAR = ToolType.get("crowbar");
        public static final ToolType SCREWDRIVER = ToolType.get("screwdriver");
        public static final ToolType SAW = ToolType.get("saw");
        public static final ToolType MORTAR = ToolType.get("mortar");
        public static final ToolType FILE = ToolType.get("file");
    }

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

    }

    private void processIMC(final InterModProcessEvent event) {

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
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        boolean isGenerated = (stack.getItem() instanceof SubItemGenerated) || (stack.getItem() instanceof SubBlockItemGenerated);
        boolean isMaterialItem = isGenerated || Arrays.stream(MaterialRegistry.IRON_EXISTING).anyMatch(tuple -> stack.sameItem(tuple.getB().getDefaultInstance()));

        if (isMaterialItem && stack.getToolTypes().isEmpty() && !stack.isCorrectToolForDrops(Blocks.COBWEB.defaultBlockState())) {
            MaterialRegistryObject material = null;

            for (MaterialRegistryObject registryObject : MaterialRegistry.getMaterials().values())
                if (registryObject.hasItem(stack.getItem())) material = registryObject;

            if (material != null && material.get().getComponents() != null)
                event.getToolTip().add(material.get().getFormulaTooltip());
        }
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
