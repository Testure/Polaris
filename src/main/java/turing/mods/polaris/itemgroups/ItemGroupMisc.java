package turing.mods.polaris.itemgroups;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import turing.mods.polaris.item.ProgrammedCircuit;
import turing.mods.polaris.registry.ItemRegistry;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemGroupMisc extends ItemGroup {
    public ItemGroupMisc() {
        super("polaris.misc");
    }

    @Override
    public void fill(NonNullList<ItemStack> items) {
        super.fill(items);
        fillCircuits(items);
    }

    private void fillCircuits(NonNullList<ItemStack> items) {
        for (int i = 0; i < 33; i++) {
            ItemStack circuit = new ItemStack(ItemRegistry.PROGRAMMED_CIRCUIT.get());
            ProgrammedCircuit.setCircuitLevel(circuit, i);
            items.add(circuit);
        }
    }

    @Override
    public ItemStack createIcon() {
        return Items.ACACIA_BOAT.getDefaultInstance();
    }
}
