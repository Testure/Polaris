package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.Collections;
import java.util.List;

public class MachineRegistryObject<T extends TileEntity, B extends Block, I extends BlockItem> {
    public final String name;
    private final List<RegistryObject<B>> blocks;
    private final List<RegistryObject<I>> items;
    private final List<RegistryObject<TileEntityType<T>>> tiles;

    public MachineRegistryObject(String name, List<RegistryObject<B>> blocks, List<RegistryObject<I>> items, List<RegistryObject<TileEntityType<T>>> tileType) {
        this.name = name;
        this.blocks = blocks;
        this.items = items;
        this.tiles = tileType;
    }

    public List<RegistryObject<B>> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }

    public List<RegistryObject<I>> getItems() {
        return items;
    }

    public List<RegistryObject<TileEntityType<T>>> getTiles() {
        return tiles;
    }
}
