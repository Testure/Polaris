package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.block.creative_power.CreativePowerProviderTile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TileRegistry {
    public static final List<RegistryObject<TileEntityType<?>>> TILES = new ArrayList<>();

    public static final RegistryObject<TileEntityType<CreativePowerProviderTile>> CREATIVE_POWER_PROVIDER = register("creative_power_provider", CreativePowerProviderTile::new, BlockRegistry.CREATIVE_POWER_PROVIDER);

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> supplier, RegistryObject<? extends Block> block) {
        return Registration.TILES.register(name, () -> TileEntityType.Builder.create(supplier, block.get()).build(null));
    }

    public static void register() {

    }
}
