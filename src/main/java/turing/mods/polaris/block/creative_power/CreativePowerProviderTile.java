package turing.mods.polaris.block.creative_power;

import turing.mods.polaris.registry.TileRegistry;
import turing.mods.polaris.tile.MachineEnergyHandler;
import turing.mods.polaris.tile.MachineTile;

public class CreativePowerProviderTile extends MachineTile {
    public CreativePowerProviderTile() {
        super(TileRegistry.CREATIVE_POWER_PROVIDER.get());
    }

    @Override
    protected MachineEnergyHandler createEnergyHandler() {
        return new MachineEnergyHandler(this, Long.MAX_VALUE, 16L, 32L, true, false);
    }
}
