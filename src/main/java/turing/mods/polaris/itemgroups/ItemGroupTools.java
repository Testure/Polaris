package turing.mods.polaris.itemgroups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemGroupTools extends ItemGroup {
    public ItemGroupTools() {
        super("polaris.tools");
    }

    @Override
    public ItemStack makeIcon() {
        return Items.IRON_PICKAXE.getDefaultInstance();
    }
}
