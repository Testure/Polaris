package turing.mods.polaris.network;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.network.NetworkEvent;
import turing.mods.polaris.item.ProgrammedCircuit;
import turing.mods.polaris.registry.ItemRegistry;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ProgramCircuitPacket implements IPacket {
    public final int level;

    public ProgramCircuitPacket(int level) {
        this.level = level;
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> context) {
        ServerPlayerEntity sender = context.get().getSender();
        if (sender == null) return false;
        ItemStack heldStack = sender.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
        if (!heldStack.isEmpty() && heldStack.isItemEqual(ItemRegistry.PROGRAMMED_CIRCUIT.get().getDefaultInstance())) {
            ProgrammedCircuit.setCircuitLevel(heldStack, MathHelper.clamp(this.level, 0, 32));
        }
        return false;
    }
}
