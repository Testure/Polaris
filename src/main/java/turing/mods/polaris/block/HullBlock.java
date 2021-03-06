package turing.mods.polaris.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
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
import turing.mods.polaris.registry.MaterialRegistryObject;
import turing.mods.polaris.util.Formatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class HullBlock extends Block implements IRenderTypedBlock {
    private final int tier;
    private final Supplier<MaterialRegistryObject> hullMaterial;

    public HullBlock(int tier, Supplier<MaterialRegistryObject> hullMaterial) {
        super(Properties.create(Material.IRON)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.NETHERITE)
                .hardnessAndResistance(2.5F, 5.0F)
                .setAllowsSpawn((a, b, c, d) -> false)
        );
        this.tier = tier;
        this.hullMaterial = hullMaterial;
    }

    @Nullable
    public MaterialRegistryObject getHullMaterial() {
        return hullMaterial.get();
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutoutMipped();
    }

    @Nonnull
    @Override
    public String getTranslationKey() {
        return "block.polaris.hull." + Voltages.VOLTAGES[tier].name;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> tooltips, ITooltipFlag flag) {
        if (Config.SHOW_MACHINE_FLAVOR_TEXT.get()) tooltips.add(new TranslationTextComponent("flavor.polaris.hull"));
        tooltips.add(Formatting.createVoltageTooltip("tooltip.polaris.voltage_in", tier, TextFormatting.GREEN));
        tooltips.add(Formatting.createVoltageTooltip("tooltip.polaris.voltage_out", tier, TextFormatting.RED));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.energy_storage", TextFormatting.GREEN + Formatting.formattedNumber(Voltages.VOLTAGES[tier].capacity)));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.hull_spawn"));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.hull_disclaimer"));
        super.addInformation(stack, reader, tooltips, flag);
    }

    @Override
    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getTranslatedName() {
        return new TranslationTextComponent(getTranslationKey());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(BlockStateProperties.FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
}
