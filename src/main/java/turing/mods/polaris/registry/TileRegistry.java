package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TileRegistry {
    public static final List<RegistryObject<TileEntityType<?>>> TILES = new ArrayList<>();

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> supplier, RegistryObject<? extends Block> block) {
        return Registration.TILES.register(name, () -> TileEntityType.Builder.of(supplier, block.get()).build(null));
    }

    public static void register() {

    }
}
