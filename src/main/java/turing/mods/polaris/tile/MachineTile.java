package turing.mods.polaris.tile;

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

import static turing.mods.polaris.Voltages.ULV;

public class MachineTile extends TileEntity {
    protected final MachineEnergyHandler energyHandler;
    protected final LazyOptional<IEnergyHandler> energy;
    protected boolean workingDisabled;

    public MachineTile(TileEntityType<?> type) {
        super(type);
        this.energyHandler = createEnergyHandler();
        this.energy = LazyOptional.of(() -> energyHandler);
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
    public void setRemoved() {
        super.setRemoved();
        energy.invalidate();
    }

    protected MachineEnergyHandler createEnergyHandler() {
        Voltages.Voltage ulv = Voltages.VOLTAGES[ULV];
        return new MachineEnergyHandler(this, ulv.capacity, 2L, ulv.energy, false, true);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == TesseractGTCapability.ENERGY_HANDLER_CAPABILITY) return energy.cast();
        return super.getCapability(cap, side);
    }
}
