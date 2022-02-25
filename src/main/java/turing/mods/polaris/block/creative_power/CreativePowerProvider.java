package turing.mods.polaris.block.creative_power;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import turing.mods.polaris.Config;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.block.IRenderTypedBlock;
import turing.mods.polaris.block.ITintedBlock;
import turing.mods.polaris.block.MachineBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CreativePowerProvider extends Block implements ITintedBlock, IRenderTypedBlock {
    public CreativePowerProvider() {
        super(AbstractBlock.Properties.of(Material.METAL)
                .harvestTool(ToolType.PICKAXE)
                .strength(-1.0F, 50.0F)
                .sound(SoundType.NETHERITE_BLOCK)
                .harvestLevel(0)
                .noDrops()
        );
    }

    @Override
    public int getColor(@Nonnull BlockState state, @Nullable IBlockDisplayReader displayReader, @Nullable BlockPos pos, int layer) {
        if (displayReader != null && pos != null && layer == 3) {
            TileEntity te = displayReader.getBlockEntity(pos);
            if (te instanceof CreativePowerProviderTile) {
                CompoundNBT nbt = te.getUpdateTag();
                int voltageTier = nbt.getInt("voltageConfig");

                return Voltages.VOLTAGES[voltageTier].color;
            }
        }
        if (layer == 2) return 0xFFD2DCFF;
        return 0xFFFFFFFF;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.cutoutMipped();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().setValue(BlockStateProperties.FACING, context.getHorizontalDirection().getOpposite());
    }

    protected BlockState getDefaultState() {
        return defaultBlockState().setValue(MachineBlock.AMPERAGE_OUTPUT, 1).setValue(BlockStateProperties.FACING, Direction.NORTH).setValue(MachineBlock.BLOCK_STATE_UPDATE_HACK, false);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable IBlockReader reader, @Nonnull List<ITextComponent> tooltips, @Nonnull ITooltipFlag flag) {
        if (Config.SHOW_MACHINE_FLAVOR_TEXT.get()) tooltips.add(new TranslationTextComponent("flavor.polaris.creative"));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.creative"));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.creative1"));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.creative2"));
        super.appendHoverText(stack, reader, tooltips, flag);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (world.isClientSide) return ActionResultType.FAIL;
        ItemStack stack = player.getItemInHand(hand);
        TileEntity te = world.getBlockEntity(pos);
        if (!stack.isEmpty() && (stack.getToolTypes().contains(Polaris.ToolTypes.HAMMER) || stack.getToolTypes().contains(Polaris.ToolTypes.SOFT_HAMMER))) {
            if (te instanceof CreativePowerProviderTile) {
                CreativePowerProviderTile tile = (CreativePowerProviderTile) te;
                if (stack.getToolTypes().contains(Polaris.ToolTypes.SOFT_HAMMER)) tile.adjustVoltage();
                else tile.adjustAmps();
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CreativePowerProviderTile();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MachineBlock.AMPERAGE_OUTPUT, BlockStateProperties.FACING, MachineBlock.BLOCK_STATE_UPDATE_HACK);
    }
}
