package turing.mods.polaris.itemgroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemGroupMisc extends ItemGroup {
    public ItemGroupMisc() {
        super("polaris.misc");
    }

    @Override
    public ItemStack makeIcon() {
        return Items.ACACIA_BOAT.getDefaultInstance();
    }
}
