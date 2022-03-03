package turing.mods.polaris.registry;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import turing.mods.polaris.block.compressor.CompressorBlock;
import turing.mods.polaris.block.compressor.CompressorItem;
import turing.mods.polaris.block.compressor.CompressorTile;
import turing.mods.polaris.container.compressor.CompressorContainer;
import turing.mods.polaris.screen.compressor.CompressorScreen;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MachineRegistry {
    private static final MachineDeferredRegister MACHINE_DEFERRED_REGISTER = new MachineDeferredRegister();

    public static final MachineRegistryObject<CompressorTile, CompressorBlock, CompressorItem> COMPRESSOR = MACHINE_DEFERRED_REGISTER.register("compressor", CompressorTile::new, CompressorBlock::new, CompressorItem::new, CompressorContainer::new);

    public static void register(IEventBus bus) {
        MACHINE_DEFERRED_REGISTER.register(bus);
    }

    public static Map<String, MachineRegistryObject<?, ?, ?>> getMachines() {
        return MACHINE_DEFERRED_REGISTER.getMachines();
    }

    @OnlyIn(Dist.CLIENT)
    public static void initScreens() {
        COMPRESSOR.screenSupplier = CompressorScreen::new;
    }
}
