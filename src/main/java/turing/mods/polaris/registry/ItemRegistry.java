package turing.mods.polaris.registry;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.item.CraftingItem;
import turing.mods.polaris.util.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class ItemRegistry {
    public static final List<RegistryObject<Item>> ITEMS = new ArrayList<>();

    public static final RegistryObject<Item> VACUUM_TUBE = addItem(Registration.ITEMS.register("vacuum_tube", () -> new CraftingItem("vacuum_tube")));
    public static final RegistryObject<Item> NAND = addItem(Registration.ITEMS.register("nand", () -> new CraftingItem("nand")));

    public static final RegistryObject<Item> WOOD_SUBSTRATE = addItem(Registration.ITEMS.register("wood_substrate", () ->
            new CraftingItem("wood_substrate", Lists.listOf("tooltip.polaris.wood_substrate"), null, null, null)
    ));
    public static final RegistryObject<Item> WOOD_BOARD = addItem(Registration.ITEMS.register("wood_board", () ->
            new CraftingItem("wood_board", Lists.listOf("tooltip.polaris.wood_board"), null, null, null)
    ));

    public static void register() {

    }

    public static RegistryObject<Item> register(String name, Supplier<Item> supplier) {
        return Registration.ITEMS.register(name, supplier);
    }

    private static RegistryObject<Item> addItem(RegistryObject<Item> item) {
        ITEMS.add(item);
        return item;
    }
}
