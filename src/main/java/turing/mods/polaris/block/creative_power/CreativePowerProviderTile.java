package turing.mods.polaris.block.creative_power;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import tesseract.api.capability.TesseractGTCapability;
import tesseract.api.gt.GTTransaction;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.registry.TileRegistry;
import turing.mods.polaris.tile.MachineEnergyHandler;
import turing.mods.polaris.tile.MachineTile;

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
        amperage *= amperage;
        if (amperage > 16L) amperage = 1L;
    }

    public void adjustVoltage() {
        voltageTier += 1;
        if (voltageTier >= Voltages.VOLTAGES.length) voltageTier = 0;
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;
        if (this.energyHandler.getEnergy() < this.energyHandler.getCapacity()) this.energyHandler.setEnergy(this.energyHandler.getCapacity());
        for (Direction direction : Direction.values()) {
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
    }
}
