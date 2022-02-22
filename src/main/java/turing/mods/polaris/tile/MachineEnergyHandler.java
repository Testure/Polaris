package turing.mods.polaris.tile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import tesseract.api.gt.GTConsumer;
import tesseract.api.gt.GTTransaction;
import tesseract.api.gt.IEnergyHandler;

public class MachineEnergyHandler implements IEnergyHandler {
    protected long energy;
    protected final long energyCapacity;
    protected final long amperage;
    protected final long voltage;
    protected final boolean canInput;
    protected final boolean canOutput;
    protected final MachineTile tile;

    private final GTConsumer.State state = new GTConsumer.State(this);

    public MachineEnergyHandler(MachineTile tile, long capacity, long amperage, long voltage, boolean canOutput, boolean canInput) {
        this.tile = tile;
        this.energyCapacity = capacity;
        this.amperage = amperage;
        this.voltage = voltage;
        this.canOutput = canOutput;
        this.canInput = canInput;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putLong("energy", energy);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.energy = nbt.getLong("energy");
    }

    @Override
    public boolean extractEnergy(GTTransaction.TransferData data) {
        return false;
    }

    @Override
    public boolean addEnergy(GTTransaction.TransferData data) {
        /*if (canInput()) {
            if (data.getVoltage() > getInputVoltage()) {
                tile.overVolt();
                return false;
            }
            this.energy = Math.min(getCapacity(), this.energy + data.getEnergy(getInputAmperage(), true));
            data.useAmps(true, getInputAmperage());
            data.drainEu(getInputVoltage() * getInputAmperage());
            return true;
        }*/
        return false;
    }

    public void setEnergy(long energy) {
        if (energy < 0L) energy = 0L;
        this.energy = Math.min(energy, getCapacity());
    }

    public long addEnergy(long amount) {
        if (amount < 0L) amount = 0L;
        long addTo = this.energy;

        this.energy = Math.min(addTo + amount, getCapacity());
        return this.energy - addTo;
    }

    public long removeEnergy(long amount) {
        if (amount < 0L) amount = 0L;
        if (amount > getCapacity()) amount = getCapacity();
        long removeFrom = this.energy;

        this.energy = Math.max(removeFrom - amount, 0L);
        return removeFrom - this.energy;
    }

    @Override
    public long getEnergy() {
        return energy;
    }

    @Override
    public long getCapacity() {
        return energyCapacity;
    }

    @Override
    public long getOutputAmperage() {
        return amperage;
    }

    @Override
    public long getOutputVoltage() {
        return voltage;
    }

    @Override
    public long getInputAmperage() {
        return amperage;
    }

    @Override
    public long getInputVoltage() {
        return voltage;
    }

    @Override
    public boolean canOutput() {
        return canOutput;
    }

    @Override
    public boolean canInput() {
        return canInput;
    }

    @Override
    public boolean canInput(Direction direction) {
        return canInput;
    }

    @Override
    public boolean canOutput(Direction direction) {
        return canOutput;
    }

    @Override
    public GTConsumer.State getState() {
        return state;
    }
}
