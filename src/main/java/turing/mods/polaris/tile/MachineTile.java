package turing.mods.polaris.tile;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import tesseract.api.capability.TesseractGTCapability;
import tesseract.api.gt.IEnergyHandler;
import turing.mods.polaris.Voltages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static turing.mods.polaris.Voltages.ULV;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MachineTile extends TileEntity {
    protected final MachineEnergyHandler energyHandler;
    protected final LazyOptional<IEnergyHandler> energyOptional;
    protected boolean workingDisabled;

    public MachineTile(TileEntityType<?> type) {
        super(type);
        this.energyHandler = createEnergyHandler();
        this.energyOptional = LazyOptional.of(() -> this.energyHandler);
    }

    protected MachineEnergyHandler createEnergyHandler() {
        Voltages.Voltage ulv = Voltages.VOLTAGES[ULV];
        return new MachineEnergyHandler(this, ulv.capacity, 2L, ulv.energy, false, true);
    }

    public void setDisabled(boolean disabled) {
        boolean previous = workingDisabled;
        workingDisabled = disabled;
        handleDisabled(previous);
    }

    public void toggleDisabled() {
        setDisabled(!this.workingDisabled);
    }

    public void handleDisabled(boolean previousValue) {}

    public void overVolt() {}

    @Override
    public void remove() {
        super.remove();
        energyOptional.invalidate();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("energy", energyHandler.serializeNBT());
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        energyHandler.deserializeNBT(nbt.getCompound("energy"));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == TesseractGTCapability.ENERGY_HANDLER_CAPABILITY) return energyOptional.cast();
        return super.getCapability(cap, side);
    }
}
