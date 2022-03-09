package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockStateMatchTest extends BlockMatchTest {
    public final BlockState state;

    public BlockStateMatchTest(BlockState state) {
        super(state.getBlock());
        this.state = state;
    }

    @Override
    public boolean test(BlockState state, Random rand) {
        return state.equals(this.state);
    }
}
