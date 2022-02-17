package turing.mods.polaris.block.compressor;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.Config;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.registry.MachineRegistry;
import turing.mods.polaris.util.Formatting;

import javax.annotation.Nullable;
import java.util.List;

public class CompressorItem extends BlockItem {
    private final int tier;

    public CompressorItem(int tier) {
        super(MachineRegistry.COMPRESSOR.getBlocks().get(tier).get(), new Properties().tab(Polaris.MISC));
        this.tier = tier;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        if (Config.SHOW_MACHINE_FLAVOR_TEXT.get()) tooltips.add(new TranslationTextComponent("flavor.polaris.compressor"));
        tooltips.add(Formatting.createVoltageTooltip("tooltip.polaris.voltage_in", tier + 1, TextFormatting.GREEN));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.energy_storage", TextFormatting.RED + Formatting.formattedNumber(Polaris.VOLTAGES.getEnergyCapacity(Polaris.VOLTAGES.VOLTAGE_LIST.get(tier + 1).getA()))));
        super.appendHoverText(stack, world, tooltips, flag);
    }
}
