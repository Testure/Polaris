package turing.mods.polaris.registry;

import com.mojang.datafixers.util.Function3;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import turing.mods.polaris.block.ITintedBlock;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.screen.MachineScreen;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MachineRegistryObject<T extends TileEntity, B extends Block, I extends BlockItem> {
    public final String name;
    private final List<RegistryObject<B>> blocks;
    private final List<RegistryObject<I>> items;
    private final List<RegistryObject<TileEntityType<T>>> tiles;
    private final List<RegistryObject<ContainerType<?>>> containers;
    @OnlyIn(Dist.CLIENT)
    public Function3<Container, PlayerInventory, ITextComponent, MachineScreen<?>> screenSupplier;

    public MachineRegistryObject(String name, List<RegistryObject<B>> blocks, List<RegistryObject<I>> items, List<RegistryObject<TileEntityType<T>>> tileType, List<RegistryObject<ContainerType<?>>> containers) {
        this.name = name;
        this.blocks = blocks;
        this.items = items;
        this.tiles = tileType;
        this.containers = containers;
    }

    public List<RegistryObject<B>> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }

    public List<RegistryObject<I>> getItems() {
        return Collections.unmodifiableList(items);
    }

    public List<RegistryObject<TileEntityType<T>>> getTiles() {
        return Collections.unmodifiableList(tiles);
    }

    public List<RegistryObject<ContainerType<?>>> getContainers() {
        return Collections.unmodifiableList(containers);
    }

    @OnlyIn(Dist.CLIENT)
    public void doClientSetup(FMLClientSetupEvent event, BlockColors blockColors, ItemColors itemColors) {
        event.enqueueWork(() -> getBlocks().forEach(block -> {
            RenderTypeLookup.setRenderLayer(block.get(), RenderType.getCutoutMipped());
            if (block.get() instanceof ITintedBlock) {
                blockColors.register(((ITintedBlock) block.get())::getColor, block.get());
                itemColors.register((a, layer) -> ((ITintedBlock) block.get()).getColor(block.get().getDefaultState(), null, null, layer), block.get().asItem());
            }
        }));
        for (RegistryObject<ContainerType<?>> container : containers)
            ScreenManager.registerFactory((ContainerType<? extends MachineContainer>) container.get(), screenSupplier::apply);
    }
}
