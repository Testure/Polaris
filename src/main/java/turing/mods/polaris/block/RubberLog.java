package turing.mods.polaris.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RubberLog extends Block {
    public static final BooleanProperty NATURAL = BooleanProperty.create("natural");

    public RubberLog() {
        super(Properties.create(Material.WOOD).harvestTool(ToolType.AXE).sound(SoundType.WOOD).hardnessAndResistance(2F));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(NATURAL, false);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NATURAL);
    }
}
