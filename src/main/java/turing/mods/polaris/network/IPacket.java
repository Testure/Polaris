package turing.mods.polaris.network;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IPacket {
    boolean handle(Supplier<NetworkEvent.Context> context);
}
