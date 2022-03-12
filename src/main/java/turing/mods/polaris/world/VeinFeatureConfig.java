package turing.mods.polaris.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;

public class VeinFeatureConfig implements IFeatureConfig {
    public static final Codec<VeinFeatureConfig> CODEC = RecordCodecBuilder.create((i) ->
            i.group(RuleTest.field_237127_c_.fieldOf("target").forGetter((config) -> config.target),
                    BlockState.CODEC.fieldOf("top").forGetter((config) -> config.top),
                    BlockState.CODEC.fieldOf("bottom").forGetter((config) -> config.bottom),
                    BlockState.CODEC.fieldOf("sporadic").forGetter((config) -> config.sporadic),
                    BlockState.CODEC.fieldOf("between").forGetter((config) -> config.between),
                    Codec.intRange(1, 64).fieldOf("minY").forGetter((config) -> config.minY),
                    Codec.intRange(8, 128).fieldOf("maxY").forGetter((config) -> config.maxY),
                    Codec.intRange(0, 10).fieldOf("topWeight").forGetter((config) -> config.topWeight),
                    Codec.intRange(0, 10).fieldOf("bottomWeight").forGetter((config) -> config.bottomWeight),
                    Codec.intRange(0, 10).fieldOf("sporadicWeight").forGetter((config) -> config.sporadicWeight),
                    Codec.intRange(0, 10).fieldOf("betweenWeight").forGetter((config) -> config.betweenWeight),
                    Codec.floatRange(0F, 1F).fieldOf("density").forGetter((config) -> config.density)
            ).apply(i, VeinFeatureConfig::new));

    public final RuleTest target;
    public final BlockState top;
    public final BlockState bottom;
    public final BlockState sporadic;
    public final BlockState between;
    public final int maxY;
    public final int minY;
    public final int topWeight;
    public final int bottomWeight;
    public final int sporadicWeight;
    public final int betweenWeight;
    public final float density;

    public VeinFeatureConfig(RuleTest target, BlockState top, BlockState bottom, BlockState sporadic, BlockState between, int topWeight, int bottomWeight, int sporadicWeight, int betweenWeight, int minY, int maxY, float density) {
        this.target = target;
        this.top = top;
        this.bottom = bottom;
        this.sporadic = sporadic;
        this.between = between;
        this.topWeight = topWeight;
        this.bottomWeight = bottomWeight;
        this.sporadicWeight = sporadicWeight;
        this.betweenWeight = betweenWeight;
        this.minY = minY;
        this.maxY = maxY;
        this.density = density;
    }
}
