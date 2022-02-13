package turing.mods.polaris.block;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface ITintedBlock extends IBlockColor {
    default int getColor(@Nonnull BlockState state, @Nullable IBlockDisplayReader displayReader, @Nullable BlockPos pos, int layer) {
        return 0xFFFFFFFF;
    }
}
