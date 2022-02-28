package turing.mods.polaris.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import turing.mods.polaris.Voltages;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MachineBlock extends Block {
    public static final IntegerProperty AMPERAGE_OUTPUT = IntegerProperty.create("amperage_output", 1, 5);
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, Voltages.VOLTAGES.length);

    //THIS IS A HORRIBLE HACK PLEASE FIX
    public static final BooleanProperty BLOCK_STATE_UPDATE_HACK = BooleanProperty.create("hack");

    protected final int tier;

    public MachineBlock(int tier) {
        super(AbstractBlock.Properties.create(Material.IRON)
                .hardnessAndResistance(2.5F, 5.0F)
                .harvestLevel(0)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.NETHERITE)
                .setAllowsSpawn((a, b, c, d) -> false)
        );
        this.tier = tier;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState();
    }

    protected BlockState defaultState() {
        return this.getDefaultState().with(TIER, tier);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            TileEntity te = world.getTileEntity(pos);

            if (te instanceof IInventory) {
                InventoryHelper.dropInventoryItems(world, pos, (IInventory) te);
                world.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        this.interactWith(world, pos, player, hand);
        return ActionResultType.CONSUME;
    }

    protected void interactWith(World world, BlockPos pos, PlayerEntity player, Hand hand) {

    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(TIER);
    }
}
