package turing.mods.polaris.registry;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.item.CraftingItem;
import turing.mods.polaris.item.MachinePart;
import turing.mods.polaris.item.ProgrammedCircuit;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemRegistry {
    public static final List<RegistryObject<? extends Item>> ITEMS = new ArrayList<>();

    public static final RegistryObject<Item> VACUUM_TUBE = addItem(Registration.ITEMS.register("vacuum_tube", () -> new CraftingItem("vacuum_tube")));
    public static final RegistryObject<Item> NAND = addItem(Registration.ITEMS.register("nand", () -> new CraftingItem("nand")));
    public static final RegistryObject<Item> STICKY_RESIN = addItem(register("sticky_resin", () -> new CraftingItem("sticky_resin")));
    public static final RegistryObject<Item> PROGRAMMED_CIRCUIT = addItem(register("programmed_circuit", ProgrammedCircuit::new));

    public static final RegistryObject<Item> WOOD_SUBSTRATE = addItem(Registration.ITEMS.register("wood_substrate", () ->
            new CraftingItem("wood_substrate", Collections.singletonList("tooltip.polaris.wood_substrate"), null, null, null)
    ));
    public static final RegistryObject<Item> WOOD_BOARD = addItem(Registration.ITEMS.register("wood_board", () ->
            new CraftingItem("wood_board", Collections.singletonList("tooltip.polaris.wood_board"), null, null, null)
    ));

    public static final RegistryObject<MachinePart>[] MOTORS = registerMachineParts("motor", MachinePart.toSuppliers(MachinePart.forEachTier(new int[]{0, 1, 2}, "item.polaris.motor")));
    public static final RegistryObject<MachinePart>[] PISTONS = registerMachineParts("piston", MachinePart.toSuppliers(MachinePart.forEachTier(new int[]{0, 1, 2}, "item.polaris.piston")));

    public static RegistryObject<MachinePart>[] registerMachineParts(String name, Supplier<MachinePart>[] suppliers) {
        RegistryObject<MachinePart>[] array = new RegistryObject[suppliers.length];
        for (int i = 0; i < suppliers.length; i++) {
            array[i] = Registration.ITEMS.register(Voltages.VOLTAGES[i].name + "_" + name, suppliers[i]);
            addItem(array[i]);
        }
        return array;
    }

    public static void register() {

    }

    public static RegistryObject<Item> register(String name, Supplier<Item> supplier) {
        return Registration.ITEMS.register(name, supplier);
    }

    private static <T extends Item> RegistryObject<T> addItem(RegistryObject<T> item) {
        ITEMS.add(item);
        return item;
    }
}
