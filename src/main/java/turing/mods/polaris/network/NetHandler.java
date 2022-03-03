package turing.mods.polaris.network;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import turing.mods.polaris.Polaris;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class NetHandler {
   private static SimpleChannel INSTANCE;
   private static int ID = 0;

   private static int nextID() {
       return ID++;
   }

   public static void register() {
       INSTANCE = NetworkRegistry.newSimpleChannel(Polaris.modLoc("polaris"), () -> "1.0", s -> true, s -> true);


   }

   public static void sendToClient(IPacket packet, ServerPlayerEntity player) {
       INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
   }

   public static void sendToServer(IPacket packet) {
       INSTANCE.sendToServer(packet);
   }
}
