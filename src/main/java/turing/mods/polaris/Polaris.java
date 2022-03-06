package turing.mods.polaris;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Blocks;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
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
import turing.mods.polaris.item.BucketItemGenerated;
import turing.mods.polaris.item.SubItemGenerated;
import turing.mods.polaris.item.ToolItemGenerated;
import turing.mods.polaris.itemgroups.ItemGroupMaterials;
import turing.mods.polaris.itemgroups.ItemGroupMisc;
import turing.mods.polaris.itemgroups.ItemGroupOres;
import turing.mods.polaris.itemgroups.ItemGroupTools;
import turing.mods.polaris.material.Components;
import turing.mods.polaris.material.Material;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.recipe.DefaultRecipes;
import turing.mods.polaris.recipe.IPromisedTag;
import turing.mods.polaris.registry.FluidRegistry;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;
import turing.mods.polaris.registry.Registration;
import turing.mods.polaris.util.Formatting;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

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

    private static ITextComponent WATER_FORMULA = new StringTextComponent("");
    private static final ITextComponent UNKNOWN_FORMULA = new StringTextComponent(TextFormatting.YELLOW + "?");
    private static final ITextComponent LIQUID_STATE = new TranslationTextComponent("tooltip.polaris.fluid_state.liquid");
    private static final ITextComponent LAVA_TEMP = new TranslationTextComponent("tooltip.polaris.fluid_temp", TextFormatting.RED + Formatting.formattedNumber(1400));
    private static final ITextComponent NORMAL_FLUID_TEMP = new TranslationTextComponent("tooltip.polaris.fluid_temp", TextFormatting.RED + Formatting.formattedNumber(300));
    @Nullable
    private static ItemStack lastTooltipRequestStack = null;
    @Nullable
    private static ITextComponent lastTooltipRequestText = null;

    public Polaris() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        Material.createFormulaTooltip((formula) -> WATER_FORMULA = formula, Components.WATER.getMadeOf());

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
        if (lastTooltipRequestStack != null && lastTooltipRequestText != null && lastTooltipRequestStack.isItemEqual(stack)) {
            event.getToolTip().add(lastTooltipRequestText);
            return;
        }

        if (stack.isItemEqual(Items.WATER_BUCKET.getDefaultInstance()) || stack.isItemEqual(Items.LAVA_BUCKET.getDefaultInstance()) || stack.isItemEqual(Items.MILK_BUCKET.getDefaultInstance())) {
            ResourceLocation itemName = stack.getItem().getRegistryName();
            if (itemName != null) {
                event.getToolTip().addAll(vanillaLiquidTooltip(itemName.getPath().replace("_bucket", "")));
            }
            return;
        }

        boolean isMaterialItem = isMaterialItem(stack, false);

        if (isMaterialItem) {
            MaterialRegistryObject material = null;

            for (MaterialRegistryObject registryObject : MaterialRegistry.getMaterials().values())
                if (registryObject.hasItem(stack.getItem())) material = registryObject;

            if (material != null && material.get().getComponents() != null) {
                event.getToolTip().add(material.get().getFormulaTooltip());
                lastTooltipRequestStack = stack;
                lastTooltipRequestText = material.get().getFormulaTooltip();
            } else { lastTooltipRequestStack = null; lastTooltipRequestText = null; }
        } else { lastTooltipRequestStack = null; lastTooltipRequestText = null; }
    }

    public static boolean isMaterialItem(Item item, boolean countTools) {
        boolean isGenerated = (item instanceof SubItemGenerated || item instanceof SubBlockItemGenerated);
        if (countTools) isGenerated = isGenerated || (item instanceof ToolItemGenerated);
        if (isGenerated) return true;

        Function<Tuple<SubItem, Item>[], Boolean> func = (list) -> {
            for (Tuple<SubItem, Item> tuple : list) {
                if (tuple.getB().getDefaultInstance().isItemEqual(item.getDefaultInstance())) {
                    if (countTools || (tuple.getB().getDefaultInstance().getToolTypes().isEmpty() && !tuple.getB().getDefaultInstance().canHarvestBlock(Blocks.COBWEB.getDefaultState()))) return true;
                }
            }
            return false;
        };

        if (func.apply(MaterialRegistry.IRON_EXISTING)) return true;
        if (func.apply(MaterialRegistry.GOLD_EXISTING)) return true;

        return false;
    }

    public static boolean isMaterialItem(ItemStack stack, boolean countTools) {
        return isMaterialItem(stack.getItem(), countTools);
    }

    public static boolean isMaterialItem(FluidStack stack) {
        return (stack.getFluid().getFilledBucket() instanceof BucketItemGenerated);
    }

    public static List<ITextComponent> vanillaLiquidTooltip(String liquid) {
        List<ITextComponent> tooltips = new ArrayList<>();
        if (liquid.equals("water"))
            tooltips.add(WATER_FORMULA);
        else tooltips.add(UNKNOWN_FORMULA);
        tooltips.add(LIQUID_STATE);
        if (liquid.equals("lava"))
            tooltips.add(LAVA_TEMP);
        else tooltips.add(NORMAL_FLUID_TEMP);
        return tooltips;
    }

    @SubscribeEvent
    public void onServerClosing(FMLServerStoppedEvent event) {
        hasResolvedBefore = false;
        tagsResolved = false;
        for (IPromisedTag tag : TAGS) {
            if (tag.isResolved()) tag.unResolve();
        }
        lastTooltipRequestStack = null; lastTooltipRequestText = null;
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
