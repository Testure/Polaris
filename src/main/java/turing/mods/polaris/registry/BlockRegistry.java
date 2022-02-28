package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.CasingBlock;
import turing.mods.polaris.block.HullBlock;
import turing.mods.polaris.block.creative_power.CreativePowerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockRegistry {
    public static final List<RegistryObject<Block>> BLOCKS = new ArrayList<>();

    public static final RegistryObject<Block> CREATIVE_POWER_PROVIDER = register("creative_power_provider", CreativePowerProvider::new);

    public static final RegistryObject<Block>[] CASINGS = new RegistryObject[]{
            register("casing_ulv", () -> new CasingBlock(0)),
            register("casing_lv", () -> new CasingBlock(1)),
            register("casing_mv", () -> new CasingBlock(2)),
            register("casing_hv", () -> new CasingBlock(3)),
            register("casing_ev", () -> new CasingBlock(4)),
            register("casing_iv", () -> new CasingBlock(5)),
    };
    public static final RegistryObject<Block>[] HULLS = new RegistryObject[]{
            register("hull_ulv", () -> new HullBlock(0)),
            register("hull_lv", () -> new HullBlock(1)),
            register("hull_mv", () -> new HullBlock(2)),
            register("hull_hv", () -> new HullBlock(3)),
            register("hull_ev", () -> new HullBlock(4)),
            register("hull_iv", () -> new HullBlock(5)),
    };

    public static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> supplier) {
        return Registration.BLOCKS.register(name, supplier);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> supplier) {
        RegistryObject<T> block = registerNoItem(name, supplier);
        Item.Properties properties = new Item.Properties().group(Polaris.MISC);
        Registration.ITEMS.register(name, () -> new BlockItem(block.get(), properties));
        BLOCKS.add((RegistryObject<Block>) block);
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
