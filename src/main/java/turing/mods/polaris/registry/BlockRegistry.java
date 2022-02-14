package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.CreativePowerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockRegistry {
    public static final List<RegistryObject<Block>> BLOCKS = new ArrayList<>();

    public static final RegistryObject<Block> CREATIVE_POWER_PROVIDER = register("creative_power_provider", CreativePowerProvider::new);

    public static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> supplier) {
        return Registration.BLOCKS.register(name, supplier);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier) {
        RegistryObject<T> block = registerNoItem(name, supplier);
        Item.Properties properties = new Item.Properties().tab(Polaris.MISC);
        Registration.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        return block;
    }

    public static <T extends Block, I extends BlockItem> RegistryObject<T> registerCustomItem(String name, Supplier<T> supplier, Supplier<I> iSupplier) {
        RegistryObject<T> block = registerNoItem(name, supplier);
        Registration.ITEMS.register(name, iSupplier);
        return block;
    }

    public static void register() {

    }
}
