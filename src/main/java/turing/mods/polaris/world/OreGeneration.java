package turing.mods.polaris.world;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.registry.FeatureRegistry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class OreGeneration {
    private static final Map<String, VeinConfiguration> VEINS = new HashMap<>();

    public static void genOre(BiomeGenerationSettingsBuilder builder, RuleTest filler, BlockState[] ores, int[] weights, int[] range, int chance) {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                FeatureRegistry.VEIN_FEATURE.get().withConfiguration(new VeinFeatureConfig(filler, ores[0], ores[1], ores[2], ores[3], weights[0], weights[1], weights[2], weights[3], range[0], range[1]))
                        .chance(chance)
                        .func_242731_b(1)
        );
    }

    public static void genOre(BiomeGenerationSettingsBuilder builder, VeinFeatureConfig config, int chance) {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                FeatureRegistry.VEIN_FEATURE.get().withConfiguration(config)
                        .chance(chance)
                        .func_242731_b(1)
        );
    }

    public static void oreGeneration(BiomeLoadingEvent event) {
        if (!Polaris.handledOreConfig) {
            VEINS.putAll(OreConfig.readVeins());
            OreVeins.register();
            Polaris.handledOreConfig = true;
        }
        for (VeinConfiguration vein : VEINS.values()) {
            genOre(event.getGeneration(), vein.toConfig(), vein.getChance());
        }
    }

    public static void registerVein(String name, VeinConfiguration vein) {
        if (VEINS.containsKey(name)) throw new IllegalStateException("Ore vein already registered!");
        VEINS.put(name, vein);
    }

    public static Map<String, VeinConfiguration> getOreVeins() {
        return Collections.unmodifiableMap(VEINS);
    }

    public static Optional<RuleTest> fillerOfBiome(@Nullable Biome.Category biomeCategory) {
        Function<Biome.Category, Optional<RuleTest>> func = (biome) -> {
            switch (biome) {
                case NETHER: return Optional.of(OreFeatureConfig.FillerBlockType.BASE_STONE_NETHER);
                case THEEND: return Optional.of(new BlockMatchRuleTest(Blocks.END_STONE));
                default: return Optional.empty();
            }
        };
        return biomeCategory != null ? Optional.of(func.apply(biomeCategory).orElse(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD)) : Optional.empty();
    }

    public static class FillerBlockType {
        public static final RuleTest OW = fillerOfBiome(Biome.Category.PLAINS).orElseThrow(NullPointerException::new);
        public static final RuleTest NETHER = fillerOfBiome(Biome.Category.NETHER).orElseThrow(NullPointerException::new);
        public static final RuleTest END = fillerOfBiome(Biome.Category.THEEND).orElseThrow(NullPointerException::new);
    }
}
