package turing.mods.polaris.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IInventoryTile extends IInventory {
    int getContainerSize();

    boolean isEmpty();

    ItemStack getItem(int slot);

    ItemStack removeItem(int slot, int amount);

    ItemStack removeItemNoUpdate(int slot);

    void setItem(int slot, ItemStack stack);

    default boolean isItemValidForSlot(int slot, ItemStack stack) {
        return canInsertIntoSlot(stack, slot);
    }

    default boolean canInsertIntoSlot(ItemStack stack, int slot) {
        return true;
    }

    BlockState getBlockState();

    World getWorld();

    BlockPos getBlockPos();

    void clear();

    default boolean isUsableByPlayer(PlayerEntity player) {
        return isWithinUsableDistance(IWorldPosCallable.create(getWorld(), getBlockPos()), player);
    }

    default boolean isWithinUsableDistance(IWorldPosCallable worldPos, PlayerEntity player) {
        return worldPos.evaluate((a, b) -> a.getBlockState(b).is(this.getBlockState().getBlock()) && player.distanceToSqr((double) b.getX() + 0.5D, (double) b.getY() + 0.5D, (double) b.getZ() + 0.5D) <= 64.0D, true);
    }
}
