package turing.mods.polaris.network;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.network.NetworkEvent;
import turing.mods.polaris.screen.CircuitScreen;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class OpenCircuitGuiPacket implements IPacket {
    public final int start;

    public OpenCircuitGuiPacket(int start) {
        this.start = start;
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> context) {
        if (Minecraft.getInstance().currentScreen == null) {
            Minecraft.getInstance().displayGuiScreen(new CircuitScreen(this.start));
        }
        return false;
    }
}
