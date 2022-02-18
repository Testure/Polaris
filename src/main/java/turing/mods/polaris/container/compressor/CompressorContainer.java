package turing.mods.polaris.container.compressor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.registry.MachineRegistry;

public class CompressorContainer extends MachineContainer {
    private final int tier;

    public CompressorContainer(int tier, int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {
        super(MachineRegistry.COMPRESSOR.getContainers().get(tier).get(), windowId, world, pos, inventory, player, MachineRegistry.COMPRESSOR.getBlocks().get(tier).get());
        this.tier = tier;
    }

    public int getTier() {
        return tier;
    }

    @Override
    protected int getSlotCount() {
        return 1;
    }

    @Override
    protected boolean canInsertStackIntoSlot(ItemStack stack, int slot) {
        if (slot != 0) return false;
        return super.canInsertStackIntoSlot(stack, slot);
    }
}
