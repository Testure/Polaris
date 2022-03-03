package turing.mods.polaris.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface ITintedBlock {
    default int getColor(@Nonnull BlockState state, @Nullable IBlockDisplayReader displayReader, @Nullable BlockPos pos, int layer) {
        return 0xFFFFFFFF;
    }
}
