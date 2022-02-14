package turing.mods.polaris.client;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.ITintedBlock;
import turing.mods.polaris.item.ITintedItem;
import turing.mods.polaris.registry.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Polaris.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        registerClient();
    }

    public static void registerClient() {
        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();

        for (RegistryObject<Block> block : BlockRegistry.BLOCKS) {
            if (block.get() instanceof ITintedBlock) {
                blockColors.register(((ITintedBlock) block.get())::getColor, block.get());
            }
        }

        for (RegistryObject<Item> item : ItemRegistry.ITEMS) {
            if (item.get() instanceof ITintedItem) {
                itemColors.register(((ITintedItem) item.get())::getColor, item.get());
            }
        }

        for (MaterialRegistryObject materialRegistryObject : MaterialRegistry.getMaterials().values()) {
            for (RegistryObject<Item> item : materialRegistryObject.getItems()) {
                itemColors.register(materialRegistryObject.get()::getColor, item.get());
            }
        }

        for (FluidRegistryObject<?, ?, ?, ?> fluidRegistryObject : FluidRegistry.getFluids().values()) {
            itemColors.register((a, b) -> b == 1 ? fluidRegistryObject.getFluid().getAttributes().getColor() : 0xFFFFFFFF, fluidRegistryObject.getBucket());
        }
    }
}
