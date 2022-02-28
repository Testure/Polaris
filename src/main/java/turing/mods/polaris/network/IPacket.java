package turing.mods.polaris.network;

import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacket {
    boolean handle(Supplier<NetworkEvent.Context> context);
}
