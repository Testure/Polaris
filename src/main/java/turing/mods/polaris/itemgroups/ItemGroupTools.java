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
public class ItemGroupTools extends ItemGroup {
    public ItemGroupTools() {
        super("polaris.tools");
    }

    @Override
    public void fill(NonNullList<ItemStack> list) {
        for (MaterialRegistryObject material : MaterialRegistry.getMaterials().values()) {
            for (SubItem subItem : SubItem.values()) {
                if (subItem.isTool() && material.hasSubItem(subItem)) {
                    Item item = material.getItemFromSubItem(subItem);
                    list.add(item.getDefaultInstance());
                }
            }
        }
    }

    @Override
    public ItemStack createIcon() {
        return Materials.BRONZE.getItemFromSubItem(SubItem.WRENCH).getDefaultInstance();
    }
}
