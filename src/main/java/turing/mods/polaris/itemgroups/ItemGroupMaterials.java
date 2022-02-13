package turing.mods.polaris.itemgroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemGroupMaterials extends ItemGroup {
    public ItemGroupMaterials() {
        super("polaris.materials");
    }

    @Override
    public ItemStack makeIcon() {
        return Items.IRON_BLOCK.getDefaultInstance();
    }
}
