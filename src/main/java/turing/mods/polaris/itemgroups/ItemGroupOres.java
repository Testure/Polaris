package turing.mods.polaris.itemgroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemGroupOres extends ItemGroup {
    public ItemGroupOres() {
        super("polaris.ores");
    }

    @Override
    public ItemStack makeIcon() {
        return Items.IRON_ORE.getDefaultInstance();
    }
}
