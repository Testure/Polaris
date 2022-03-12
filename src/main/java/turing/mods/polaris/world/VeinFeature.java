package turing.mods.polaris.world;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import turing.mods.polaris.util.Consumer2;
import turing.mods.polaris.util.Runner;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class VeinFeature extends Feature<VeinFeatureConfig> {
    public VeinFeature() {
        super(VeinFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, VeinFeatureConfig config) {
        int y = MathHelper.clamp(rand.nextInt(config.maxY + 1), config.minY, config.maxY);
        return this.chunkGen(reader, rand, y, reader.getChunk(pos), config);
    }

    protected boolean chunkGen(ISeedReader reader, Random rand, int Y, IChunk chunk, VeinFeatureConfig config) {
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = Y - 4; y < reader.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, chunk.getPos().getXStart() + x, chunk.getPos().getZStart() + z) && y <= (Y + 3); y++) {
                    BlockPos.Mutable thisPos = new BlockPos.Mutable(chunk.getPos().getXStart() + x, y, chunk.getPos().getZStart() + z);
                    if (!config.target.test(reader.getBlockState(thisPos), rand)) continue;

                    int chance = (int) ((rand.nextInt(10) + 1) * config.density);

                    if (chance <= config.betweenWeight) reader.setBlockState(thisPos, config.between, 2);

                    Consumer2<Integer, Boolean> changer = (k, positive) -> {
                        if (k <= 0) thisPos.setX(MathHelper.clamp(positive ? thisPos.getX() + 1 : thisPos.getX() - 1, chunk.getPos().getXStart(), chunk.getPos().getXStart() + 15));
                        else thisPos.setZ(MathHelper.clamp(positive ? thisPos.getZ() + 1 : thisPos.getZ() - 1, chunk.getPos().getZStart(), chunk.getPos().getZStart() + 15));
                    };
                    Runner adjustPos = () -> {
                        if (!config.target.test(reader.getBlockState(thisPos), rand)) changer.accept(0, true);
                        if (!config.target.test(reader.getBlockState(thisPos), rand)) changer.accept(1, true);
                        if (!config.target.test(reader.getBlockState(thisPos), rand)) changer.accept(0, false);
                        if (!config.target.test(reader.getBlockState(thisPos), rand)) changer.accept(1, false);
                    };
                    adjustPos.run();

                    if (chance <= config.sporadicWeight && config.target.test(reader.getBlockState(thisPos), rand)) {
                        reader.setBlockState(thisPos, config.sporadic, 2);
                        adjustPos.run();
                    }

                    if (y > Y && config.target.test(reader.getBlockState(thisPos), rand)) {
                        if (chance <= config.topWeight) reader.setBlockState(thisPos, config.top, 2);
                    } else if (y < Y && config.target.test(reader.getBlockState(thisPos), rand)) {
                        if (chance <= config.bottomWeight) reader.setBlockState(thisPos, config.bottom, 2);
                    }
                }
            }
        }

        return true;
    }
}
