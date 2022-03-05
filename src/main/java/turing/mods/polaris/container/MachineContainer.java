package turing.mods.polaris.container;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import turing.mods.polaris.block.compressor.CompressorTile;
import turing.mods.polaris.tile.MachineTile;
import turing.mods.polaris.ui.ContainerSlots;
import turing.mods.polaris.ui.SlotInfoProvider;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MachineContainer extends Container {
    public final TileEntity tile;
    final PlayerEntity player;
    final IItemHandler itemHandler;
    final Block block;

    public MachineContainer(ContainerType<?> type, int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player, ContainerSlots slots, Block block) {
        super(type, windowId);
        this.tile = world.getTileEntity(pos);
        this.player = player;
        this.itemHandler = new InvWrapper(inventory);
        this.block = block;

        if (this.tile != null) {
            this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(handler -> {
                for (int i = 0; i < slots.getSlots().length; i++) {
                    addSlot(new SlotItemHandler(handler, i, slots.getSlots()[i].x, slots.getSlots()[i].y));
                }
            });
        }

        layoutPlayerInventorySlots(10, 82);
    }

    public int getProgress(int totalSize) {
        MachineTile tile = (MachineTile) this.tile;
        CompoundNBT tag = tile.getUpdateTag();
        int time = tag.getInt("time");
        int timeGoal = tag.getInt("timeGoal");

        return timeGoal > 0 ? (time * totalSize) / timeGoal : 0;
    }

    @Nullable
    public ItemStack getStack(int slot) {
        if (this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).isPresent())
            return this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).map((handler) -> handler.getStackInSlot(slot)).orElse(ItemStack.EMPTY);
        return null;
    }

    @Nullable
    public FluidStack getFluidStack(int slot) {
        if (this.tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).isPresent())
            return this.tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).map((handler) -> handler.getFluidInTank(slot)).orElse(FluidStack.EMPTY);
        return null;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        if (this.tile.getWorld() == null) return false;
        return isWithinUsableDistance(IWorldPosCallable.of(this.tile.getWorld(), tile.getPos()), this.player, this.block);
    }

    protected int getSlotCount() {
        return 0;
    }

    protected boolean canInsertStackIntoSlot(ItemStack stack, int slot) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemStack = stack.copy();

            if (index <= getSlotCount()) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChanged();
            } else {
                if (canInsertStackIntoSlot(stack.getStack(), 0)) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28 && !this.mergeItemStack(stack, 28, 37, false)) {
                    return ItemStack.EMPTY;
                } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                stack.isItemEqual(ItemStack.EMPTY);
            } else slot.onSlotChanged();

            if (stack.getCount() == itemStack.getCount()) return ItemStack.EMPTY;

            slot.onTake(player, stack);
        }
        return itemStack;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int i = 0; i < verAmount; i++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(itemHandler, 9, leftCol, topRow, 9, 18, 3, 18);
        topRow += 58;
        addSlotRange(itemHandler, 0, leftCol, topRow, 9, 18);
    }
}
