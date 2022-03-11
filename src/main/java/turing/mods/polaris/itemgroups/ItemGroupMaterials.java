package turing.mods.polaris.itemgroups;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemGroupMaterials extends ItemGroup {
    public ItemGroupMaterials() {
        super("polaris.materials");
    }

    @Override
    public void fill(NonNullList<ItemStack> list) {
        for (MaterialRegistryObject material : MaterialRegistry.getMaterials().values()) {
            for (SubItem subItem : SubItem.values()) {
                if (material.hasSubItem(subItem) && !subItem.isTool()) {
                    Item item = material.getItemFromSubItem(subItem);
                    if (item.getGroup() instanceof ItemGroupMaterials || !Objects.equals(item.getCreatorModId(item.getDefaultInstance()), Polaris.MODID)) list.add(item.getDefaultInstance());
                }
            }
        }
    }

    @Override
    public ItemStack createIcon() {
        return MaterialRegistry.SILVER.getItemFromSubItem(SubItem.INGOT).getDefaultInstance();
    }
}
