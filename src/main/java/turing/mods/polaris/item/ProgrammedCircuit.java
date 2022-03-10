package turing.mods.polaris.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import turing.mods.polaris.network.NetHandler;
import turing.mods.polaris.network.OpenCircuitGuiPacket;
import turing.mods.polaris.registry.ItemRegistry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ProgrammedCircuit extends Item {
    public ProgrammedCircuit() {
        super(new Properties().maxStackSize(1).containerItem(Items.CHAIN).rarity(Rarity.UNCOMMON));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltips, ITooltipFlag flagIn) {
        tooltips.add(new TranslationTextComponent("tooltip.polaris.circuit"));
        super.addInformation(stack, worldIn, tooltips, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (worldIn.isRemote) return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
        if (playerIn instanceof ServerPlayerEntity && !playerIn.getHeldItem(handIn).isEmpty()) {
            NetHandler.sendToClient(new OpenCircuitGuiPacket(getCircuitLevel(playerIn.getHeldItem(handIn))), (ServerPlayerEntity) playerIn);
            return ActionResult.resultConsume(playerIn.getHeldItem(handIn));
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    public static ItemStack ofLevel(int circuitLevel) {
        ItemStack newStack = new ItemStack(ItemRegistry.PROGRAMMED_CIRCUIT.get());
        setCircuitLevel(newStack, circuitLevel);
        return newStack;
    }

    public static int getCircuitLevel(ItemStack stack) {
        CompoundNBT nbt = stack.getTag();
        return nbt != null ? nbt.getInt("level") : 0;
    }

    public static void setCircuitLevel(ItemStack stack, int circuitLevel) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putInt("level", MathHelper.clamp(circuitLevel, 0, 32));
    }

    public static boolean isAtLevel(ItemStack stack, int wantedLevel) {
        return getCircuitLevel(stack) == wantedLevel;
    }

    public static class PropertyGetter implements IItemPropertyGetter {
        public PropertyGetter() {

        }

        @Override
        public float call(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity player) {
            int level = getCircuitLevel(stack);
            if (level > 0) return Float.parseFloat("0." + (level > 9 ? level : ("0" + level)));
            return 0.0F;
        }
    }
}
