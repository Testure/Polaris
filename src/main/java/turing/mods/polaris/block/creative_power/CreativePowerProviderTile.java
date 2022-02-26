package turing.mods.polaris.block.creative_power;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.Constants;
import tesseract.api.capability.TesseractGTCapability;
import tesseract.api.gt.GTTransaction;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.block.MachineBlock;
import turing.mods.polaris.registry.TileRegistry;
import turing.mods.polaris.tile.MachineEnergyHandler;
import turing.mods.polaris.tile.MachineTile;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CreativePowerProviderTile extends MachineTile implements ITickableTileEntity {
    private long amperage = 1L;
    private int voltageTier = 0;

    public CreativePowerProviderTile() {
        super(TileRegistry.CREATIVE_POWER_PROVIDER.get());
    }

    @Override
    protected MachineEnergyHandler createEnergyHandler() {
        return new MachineEnergyHandler(this, Long.MAX_VALUE, 16L, Long.MAX_VALUE, true, false);
    }

    public void adjustAmps() {
        amperage *= 2L;
        if (amperage > 16L) amperage = 1L;
        updateState(false);
    }

    public void adjustVoltage() {
        voltageTier += 1;
        if (voltageTier >= Voltages.VOLTAGES.length) voltageTier = 0;
        updateState(true);
    }

    private CompoundNBT writeConfig(CompoundNBT tag) {
        tag.putInt("voltageConfig", voltageTier);
        tag.putLong("amperageConfig", amperage);
        return tag;
    }

    private void readConfig(CompoundNBT tag) {
        this.voltageTier = tag.getInt("voltageConfig");
        this.amperage = tag.getLong("amperageConfig");
        updateState(true);
    }

    private void updateState(boolean hackUpdate) {
        if (level == null || level.isClientSide) return;
        BlockState newState = getBlockState().setValue(MachineBlock.AMPERAGE_OUTPUT, Voltages.getAmpIndex(amperage) + 1);
        level.setBlock(getBlockPos(), !hackUpdate ? newState : newState.setValue(MachineBlock.BLOCK_STATE_UPDATE_HACK, !newState.getValue(MachineBlock.BLOCK_STATE_UPDATE_HACK)), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.RERENDER_MAIN_THREAD);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;
        if (this.energyHandler.getEnergy() < this.energyHandler.getCapacity()) this.energyHandler.setEnergy(this.energyHandler.getCapacity());
        Direction direction = level.getBlockState(getBlockPos()).getValue(BlockStateProperties.FACING);
        TileEntity te = level.getBlockEntity(getBlockPos().relative(direction));
        if (te != null) {
            te.getCapability(TesseractGTCapability.ENERGY_HANDLER_CAPABILITY, direction.getOpposite()).ifPresent(handler -> {
                if (handler.canInput()) {
                    Voltages.Voltage voltage = Voltages.VOLTAGES[voltageTier];
                    GTTransaction transaction = new GTTransaction(amperage, voltage.energy, energyHandler::extractEnergy);
                    if (handler.insert(transaction)) transaction.commit();
                }
            });
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        return writeConfig(super.save(tag));
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        readConfig(tag);
        super.load(state, tag);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getBlockPos(), -1, writeConfig(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        readConfig(pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return writeConfig(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
        readConfig(tag);
    }
}
