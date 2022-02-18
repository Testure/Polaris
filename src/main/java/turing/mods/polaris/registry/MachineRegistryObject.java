package turing.mods.polaris.registry;

import com.mojang.datafixers.util.Function3;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.screen.MachineScreen;

import java.util.Collections;
import java.util.List;

public class MachineRegistryObject<T extends TileEntity, B extends Block, I extends BlockItem> {
    public final String name;
    private final List<RegistryObject<B>> blocks;
    private final List<RegistryObject<I>> items;
    private final List<RegistryObject<TileEntityType<T>>> tiles;
    private final List<RegistryObject<ContainerType<?>>> containers;
    public final Function3<Container, PlayerInventory, ITextComponent, MachineScreen<?>> screenProvider;

    public MachineRegistryObject(String name, List<RegistryObject<B>> blocks, List<RegistryObject<I>> items, List<RegistryObject<TileEntityType<T>>> tileType, List<RegistryObject<ContainerType<?>>> containers, Function3<Container, PlayerInventory, ITextComponent, MachineScreen<?>> screenProvider) {
        this.name = name;
        this.blocks = blocks;
        this.items = items;
        this.tiles = tileType;
        this.containers = containers;
        this.screenProvider = screenProvider;
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
}
