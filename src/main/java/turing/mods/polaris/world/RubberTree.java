package turing.mods.polaris.world;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import turing.mods.polaris.block.RubberLog;
import turing.mods.polaris.registry.BlockRegistry;
import turing.mods.polaris.registry.FeatureRegistry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RubberTree extends Tree {
    public static final BaseTreeFeatureConfig DEFAULT_CONFIG = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.RUBBER_LOG.get().getDefaultState().with(RubberLog.NATURAL, true)), new SimpleBlockStateProvider(BlockRegistry.RUBBER_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0),  3), new StraightTrunkPlacer(6, 2, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build();
    public static final BaseTreeFeatureConfig TALL_CONFIG = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(BlockRegistry.RUBBER_LOG.get().getDefaultState().with(RubberLog.NATURAL, true)), new SimpleBlockStateProvider(BlockRegistry.RUBBER_LEAVES.get().getDefaultState()), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0),  3), new StraightTrunkPlacer(7, 2, 0), new TwoLayerFeature(1, 0, 1)).setIgnoreVines().build();

    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        if (randomIn.nextInt(10) == 0) return FeatureRegistry.RUBBER.get().withConfiguration(TALL_CONFIG);
        return FeatureRegistry.RUBBER.get().withConfiguration(DEFAULT_CONFIG);
    }
}
