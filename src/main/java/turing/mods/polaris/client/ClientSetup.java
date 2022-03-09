package turing.mods.polaris.client;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.IRenderTypedBlock;
import turing.mods.polaris.block.ITintedBlock;
import turing.mods.polaris.item.ITintedItem;
import turing.mods.polaris.registry.*;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Polaris.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ClientSetup {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        registerClient(event);
    }

    public static void registerClient(FMLClientSetupEvent event) {
        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        machineClientSetup(event, blockColors, itemColors);
        event.enqueueWork(() -> blockClientSetup(blockColors, itemColors));
        event.enqueueWork(() -> setupItemColors(itemColors));
        materialClientSetup(itemColors, blockColors, event);
        event.enqueueWork(() -> setupBucketColors(itemColors));
        RenderTypeLookup.setRenderLayer(BlockRegistry.RUBBER_SAPLING.get(), RenderType.getCutout());
    }

    private static void machineClientSetup(FMLClientSetupEvent event, BlockColors blockColors, ItemColors itemColors) {
        MachineRegistry.initScreens();
        MachineRegistry.getMachines().forEach((name, machine) -> machine.doClientSetup(event, blockColors, itemColors));
    }

    private static void materialClientSetup(ItemColors itemColors, BlockColors blockColors, FMLClientSetupEvent event) {
        MaterialRegistry.getMaterials().forEach((name, material) -> material.doClientSetup(event, itemColors, blockColors));
    }

    private static void setupBucketColors(ItemColors itemColors) {
        FluidRegistry.getFluids().forEach((name, fluid) -> itemColors.register((stack, layer) -> layer == 1 ? fluid.getFluid().getAttributes().getColor() : 0xFFFFFFFF, fluid.getBucket()));
    }

    private static void setupItemColors(ItemColors itemColors) {
        ItemRegistry.ITEMS.forEach(item -> {
            if (item.get() instanceof ITintedItem) itemColors.register(((ITintedItem) item.get())::getColor, item.get());
        });
    }

    private static void blockClientSetup(BlockColors blockColors, ItemColors itemColors) {
        BlockRegistry.BLOCKS.forEach(block -> {
            if (block.get() instanceof ITintedBlock) {
                blockColors.register(((ITintedBlock) block.get())::getColor, block.get());
                itemColors.register((a, layer) -> ((ITintedBlock) block.get()).getColor(block.get().getDefaultState(), null, null, layer), block.get().asItem());
            }
            if (block.get() instanceof IRenderTypedBlock) RenderTypeLookup.setRenderLayer(block.get(), ((IRenderTypedBlock) block.get()).getRenderType());
        });
        itemColors.register((a, b) -> FoliageColors.getDefault(), BlockRegistry.RUBBER_LEAVES.get().asItem());
        blockColors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.getDefault(), BlockRegistry.RUBBER_LEAVES.get());
    }
}
