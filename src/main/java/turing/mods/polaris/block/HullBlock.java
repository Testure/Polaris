package turing.mods.polaris.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import turing.mods.polaris.Config;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.util.Formatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class HullBlock extends Block implements IRenderTypedBlock {
    private final int tier;

    public HullBlock(int tier) {
        super(Properties.of(Material.HEAVY_METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.NETHERITE_BLOCK)
                .strength(2.5F, 5.0F)
                .isValidSpawn((a, b, c, d) -> false)
        );
        this.tier = tier;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return "block.polaris.hull";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> tooltips, ITooltipFlag flag) {
        if (Config.SHOW_MACHINE_FLAVOR_TEXT.get()) tooltips.add(new TranslationTextComponent("flavor.polaris.hull"));
        tooltips.add(Formatting.createVoltageTooltip("tooltip.polaris.voltage_in", tier, TextFormatting.GREEN));
        tooltips.add(Formatting.createVoltageTooltip("tooltip.polaris.voltage_out", tier, TextFormatting.RED));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.energy_storage", TextFormatting.GREEN + Formatting.formattedNumber(Voltages.VOLTAGES[tier].capacity)));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.hull_spawn"));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.hull_disclaimer"));
        super.appendHoverText(stack, reader, tooltips, flag);
    }

    @Override
    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getName() {
        return new TranslationTextComponent(getDescriptionId(), new TranslationTextComponent(Voltages.getVoltageTierTranslationKey(tier)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return defaultBlockState().setValue(BlockStateProperties.FACING, context.getHorizontalDirection().getOpposite());
    }
}
