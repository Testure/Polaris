package turing.mods.polaris.block.compressor;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import turing.mods.polaris.registry.MachineRegistry;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CompressorTile extends LockableTileEntity implements ITickableTileEntity {
    public CompressorTile(int tier) {
        super(MachineRegistry.COMPRESSOR.getTiles().get(tier).get());
    }

    @Override
    public void tick() {

    }

    public void encodeExtraData(PacketBuffer buffer) {
        buffer.writeBlockPos(getBlockPos());
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new StringTextComponent("");
    }

    @Override
    protected Container createMenu(int i, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getItem(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {

    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }

    @Override
    public void clearContent() {

    }
}
