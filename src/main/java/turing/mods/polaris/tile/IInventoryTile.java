package turing.mods.polaris.tile;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IInventoryTile extends IInventory {
    boolean isEmpty();

    default boolean isItemValidForSlot(int slot, ItemStack stack) {
        return canInsertIntoSlot(stack, slot);
    }

    default boolean canInsertIntoSlot(ItemStack stack, int slot) {
        return true;
    }

    BlockState getBlockState();

    World getWorld();

    BlockPos getPos();

    void clear();

    default boolean isUsableByPlayer(PlayerEntity player) {
        return isWithinUsableDistance(IWorldPosCallable.of(getWorld(), getPos()), player);
    }

    default boolean isWithinUsableDistance(IWorldPosCallable worldPos, PlayerEntity player) {
        return worldPos.applyOrElse((a, b) -> a.getBlockState(b).isIn(this.getBlockState().getBlock()) && player.getDistanceSq((double) b.getX() + 0.5D, (double) b.getY() + 0.5D, (double) b.getZ() + 0.5D) <= 64.0D, true);
    }
}
