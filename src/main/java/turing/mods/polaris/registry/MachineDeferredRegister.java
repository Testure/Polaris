package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import turing.mods.polaris.Polaris;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class MachineDeferredRegister {
    private final Map<String, MachineRegistryObject<?, ?, ?>> machines = new HashMap<>();
    private final DeferredRegister<Item> itemRegister;
    private final DeferredRegister<Block> blockRegister;
    private final DeferredRegister<TileEntityType<?>> tileRegister;

    public MachineDeferredRegister() {
        itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, Polaris.MODID);
        blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, Polaris.MODID);
        tileRegister = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Polaris.MODID);
    }

    public Map<String, MachineRegistryObject<?, ? ,?>> getMachines() {
        return machines;
    }

    public <T extends TileEntity, B extends Block, I extends BlockItem> MachineRegistryObject<T, B, I> register(String name, Function<Integer, T> tileSupplier, Function<Integer, B> blockSupplier, Function<Integer, I> itemSupplier) {
        if (machines.containsKey(name)) throw new IllegalStateException("Machine already exists.");
        List<RegistryObject<B>> blocks = new ArrayList<>();
        List<RegistryObject<I>> items = new ArrayList<>();
        List<RegistryObject<TileEntityType<T>>> tiles = new ArrayList<>();

        for (int i = 1; i < Polaris.VOLTAGES.VOLTAGE_LIST.size(); i++) {
            int finalI = i - 1;
            String tieredName = name + "_" + Polaris.VOLTAGES.VOLTAGE_LIST.get(i).getB();
            Supplier<T> TSupplier = () -> tileSupplier.apply(finalI);
            Supplier<B> BSupplier = () -> blockSupplier.apply(finalI);
            Supplier<I> ISupplier = () -> itemSupplier.apply(finalI);

            RegistryObject<B> block = blockRegister.register(tieredName, BSupplier);
            RegistryObject<I> item = itemRegister.register(tieredName, ISupplier);
            RegistryObject<TileEntityType<T>> tile = tileRegister.register(tieredName, () -> TileEntityType.Builder.of(TSupplier, block.get()).build(null));
            blocks.add(block);
            items.add(item);
            tiles.add(tile);
        }

        MachineRegistryObject<T, B, I> machine = new MachineRegistryObject<>(name, blocks, items, tiles);
        machines.put(name, machine);
        return machine;
    }

    public void register(IEventBus bus) {
        blockRegister.register(bus);
        itemRegister.register(bus);
        tileRegister.register(bus);
    }
}
