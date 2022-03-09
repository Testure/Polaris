package turing.mods.polaris.world;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import turing.mods.polaris.registry.FeatureRegistry;

public class TreeGen {
    public static void genTrees(BiomeLoadingEvent event) {
        event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR
                .withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FeatureRegistry.RUBBER.get().withConfiguration(RubberTree.TALL_CONFIG).withChance(0.2F)), FeatureRegistry.RUBBER.get().withConfiguration(RubberTree.DEFAULT_CONFIG)))
                .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
                .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1)))
        );
    }
}
