package turing.mods.polaris.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
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

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Polaris.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        registerClient(event);
    }

    public static void registerClient(FMLClientSetupEvent event) {
        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        machineClientSetup(event);
        event.enqueueWork(() -> blockClientSetup(blockColors, itemColors));
        event.enqueueWork(() -> setupItemColors(itemColors));
        materialClientSetup(itemColors, blockColors, event);
        event.enqueueWork(() -> setupBucketColors(itemColors));
    }

    private static void machineClientSetup(FMLClientSetupEvent event) {
        MachineRegistry.initScreens();
        MachineRegistry.getMachines().forEach((name, machine) -> machine.doClientSetup(event));
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
                itemColors.register((a, layer) -> ((ITintedBlock) block.get()).getColor(block.get().defaultBlockState(), null, null, layer), block.get().asItem());
            }
            if (block.get() instanceof IRenderTypedBlock) RenderTypeLookup.setRenderLayer(block.get(), ((IRenderTypedBlock) block.get()).getRenderType());
        });
    }
}
