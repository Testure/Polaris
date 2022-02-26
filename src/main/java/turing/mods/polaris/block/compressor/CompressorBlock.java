package turing.mods.polaris.block.compressor;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import turing.mods.polaris.Config;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.block.ITintedBlock;
import turing.mods.polaris.block.MachineBlock;
import turing.mods.polaris.container.compressor.CompressorContainer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CompressorBlock extends MachineBlock implements ITintedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public CompressorBlock(int tier) {
        super(tier);
    }

    @Override
    public int getColor(@Nonnull BlockState state, @Nullable IBlockDisplayReader displayReader, @Nullable BlockPos pos, int layer) {
        if (!Config.MACHINE_TIER_COLORS.get()) {
            if (layer == 0) return Config.MACHINE_BASE_COLOR.get();
        } else if (layer == 0) return tier == 0 ? Config.MACHINE_BASE_COLOR.get() : Voltages.VOLTAGES[tier + 1].color;
        return 0xFFFFFFFF;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CompressorTile(tier);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected BlockState getDefaultState() {
        return defaultBlockState().setValue(MachineBlock.TIER, tier + 1).setValue(POWERED, false).setValue(FACING, Direction.NORTH);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return rotate(state, direction);
    }

    @Override
    protected void interactWith(World world, BlockPos pos, PlayerEntity player, Hand hand) {
        TileEntity te = world.getBlockEntity(pos);

        if (te instanceof CompressorTile && player instanceof ServerPlayerEntity) {
            CompressorTile tile = (CompressorTile) te;
            INamedContainerProvider provider = new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return new TranslationTextComponent("screen.polaris.compressor");
                }

                @Nullable
                @Override
                public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {
                    return new CompressorContainer(tier, i, world, pos, inventory, player, null);
                }
            };

            NetworkHooks.openGui((ServerPlayerEntity) player, provider, tile::encodeExtraData);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, POWERED);
    }
}
