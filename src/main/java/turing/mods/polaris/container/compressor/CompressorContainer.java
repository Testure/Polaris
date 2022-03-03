package turing.mods.polaris.container.compressor;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import turing.mods.polaris.block.compressor.CompressorTile;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.container.SlotInfoProvider;
import turing.mods.polaris.registry.MachineRegistry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CompressorContainer extends MachineContainer {
    private final int tier;

    public CompressorContainer(int tier, int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player, @Nullable PacketBuffer data) {
        super(MachineRegistry.COMPRESSOR.getContainers().get(tier).get(), windowId, world, pos, inventory, player, new SlotInfoProvider(
                new SlotInfoProvider.SlotInfo(0, 52, 27),
                new SlotInfoProvider.SlotInfo(1, 102, 27)
        ), MachineRegistry.COMPRESSOR.getBlocks().get(tier).get());
        this.tier = tier;
        trackProgress();
    }

    private void trackProgress() {
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                CompressorTile tileEntity = (CompressorTile) tile;
                CompoundNBT tag = tileEntity.getUpdateTag();
                return tag.getInt("time") & 0xffff;
            }

            @Override
            public void set(int amount) {
                CompressorTile tileEntity = (CompressorTile) tile;
                CompoundNBT tag = tileEntity.getUpdateTag();
                int current = tag.getInt("time") & 0xffff0000;

                tag.putInt("time", current + (amount & 0xffff));
                tileEntity.readProgress(tag);
            }
        });
        trackInt(new IntReferenceHolder() {
            @Override
            public int get() {
                CompressorTile tileEntity = (CompressorTile) tile;
                CompoundNBT tag = tileEntity.getUpdateTag();
                return tag.getInt("timeGoal") & 0xffff;
            }

            @Override
            public void set(int amount) {
                CompressorTile tileEntity = (CompressorTile) tile;
                CompoundNBT tag = tileEntity.getUpdateTag();
                int current = tag.getInt("timeGoal") & 0xffff0000;

                tag.putInt("timeGoal", current + (amount & 0xffff));
                tileEntity.readProgress(tag);
            }
        });
    }

    public int getProgress() {
        CompressorTile tile = (CompressorTile) this.tile;
        CompoundNBT tag = tile.getUpdateTag();
        int time = tag.getInt("time");
        int timeGoal = tag.getInt("timeGoal");

        return timeGoal > 0 ? (time * 20) / timeGoal : 0;
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
