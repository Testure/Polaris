package turing.mods.polaris.registry;

import net.minecraftforge.eventbus.api.IEventBus;
import turing.mods.polaris.block.compressor.CompressorBlock;
import turing.mods.polaris.block.compressor.CompressorItem;
import turing.mods.polaris.block.compressor.CompressorTile;

import java.util.Map;

public class MachineRegistry {
    private static final MachineDeferredRegister MACHINE_DEFERRED_REGISTER = new MachineDeferredRegister();

    public static final MachineRegistryObject<CompressorTile, CompressorBlock, CompressorItem> COMPRESSOR = MACHINE_DEFERRED_REGISTER.register("compressor", CompressorTile::new, CompressorBlock::new, CompressorItem::new);

    public static void register(IEventBus bus) {
        MACHINE_DEFERRED_REGISTER.register(bus);
    }

    public static Map<String, MachineRegistryObject<?, ?, ?>> getMachines() {
        return MACHINE_DEFERRED_REGISTER.getMachines();
    }
}
