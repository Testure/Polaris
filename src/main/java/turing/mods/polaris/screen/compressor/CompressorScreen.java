package turing.mods.polaris.screen.compressor;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;
import turing.mods.polaris.client.ModularUIs;
import turing.mods.polaris.container.compressor.CompressorContainer;
import turing.mods.polaris.screen.MachineScreen;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CompressorScreen extends MachineScreen<CompressorContainer> {
    public CompressorScreen(Container container, PlayerInventory inv, ITextComponent name) {
        super((CompressorContainer) container, inv, name, ModularUIs.COMPRESSOR_UI);
    }
}
