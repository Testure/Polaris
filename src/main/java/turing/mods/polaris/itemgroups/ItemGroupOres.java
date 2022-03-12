package turing.mods.polaris.itemgroups;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import turing.mods.polaris.material.Materials;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemGroupOres extends ItemGroup {
    public ItemGroupOres() {
        super("polaris.ores");
    }

    @Override
    public void fill(NonNullList<ItemStack> list) {
        for (MaterialRegistryObject material : MaterialRegistry.getMaterials().values()) {
            if (material.hasSubItem(SubItem.ORE)) {
                Item ore = material.getItemFromSubItem(SubItem.ORE);
                list.add(ore.getDefaultInstance());
            }
        }
    }

    @Override
    public ItemStack createIcon() {
        return Materials.SULFUR.getItemFromSubItem(SubItem.ORE).getDefaultInstance();
    }
}
