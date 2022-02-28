package turing.mods.polaris.itemgroups;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemGroupMisc extends ItemGroup {
    public ItemGroupMisc() {
        super("polaris.misc");
    }

    @Override
    public ItemStack createIcon() {
        return Items.ACACIA_BOAT.getDefaultInstance();
    }
}
