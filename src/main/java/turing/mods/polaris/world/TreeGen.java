package turing.mods.polaris.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import turing.mods.polaris.Config;
import turing.mods.polaris.registry.FeatureRegistry;
import turing.mods.polaris.util.MathUtil;

public class TreeGen {
    public static final ConfiguredFeature<?, ?> RUBBER_TREE = FeatureRegistry.RUBBER.get().withConfiguration(RubberTree.DEFAULT_CONFIG);
    public static final ConfiguredFeature<?, ?> RUBBER_TREE_TALL = FeatureRegistry.RUBBER.get().withConfiguration(RubberTree.TALL_CONFIG);
    public static final ConfiguredFeature<?, ?> RUBBER_WATER = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(RUBBER_TREE_TALL.withChance(0.2F)), RUBBER_TREE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> RUBBER_MOUNTAIN = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(RUBBER_TREE_TALL.withChance(0.1F)), RUBBER_TREE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
    public static final ConfiguredFeature<?, ?> RUBBER_BIRCH = Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(RUBBER_TREE_TALL.withChance(0.1F)), RUBBER_TREE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1)));

    public static void genTrees(BiomeLoadingEvent event) {
        if (Config.RUBBER_TREES.get()) generateRubberTrees(event);
    }

    private static void generateRubberTrees(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.PLAINS) event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, RUBBER_WATER);
        if (MathUtil.isWithinBoundsF(event.getClimate().temperature, 0.4F, 1.0F)) event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, RUBBER_MOUNTAIN);
        if (event.getCategory() == Biome.Category.FOREST) event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, RUBBER_BIRCH);
        if (event.getCategory() == Biome.Category.EXTREME_HILLS) event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, RUBBER_MOUNTAIN);
    }
}
