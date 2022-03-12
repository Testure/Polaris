package turing.mods.polaris.world;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.registries.ForgeRegistries;
import turing.mods.polaris.util.BlockMatchTest;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Optional;

import static turing.mods.polaris.util.OptionalUtil.jsonGetAsOptional;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VeinConfiguration {
    public final RuleTest filler;
    public final BlockState[] states;
    public final int[] weights;
    public final int minY;
    public final int maxY;
    public final int chance;
    public final float density;

    public VeinConfiguration(RuleTest filler, BlockState[] states, int[] weights, int[] level, int chance, float density) {
        this.filler = filler;
        this.states = states;
        this.weights = weights;
        this.minY = level[0];
        this.maxY = level[1];
        this.chance = chance;
        this.density = density;
    }

    public RuleTest getFiller() {
        return filler;
    }

    public BlockState getTopState() {
        return states[0];
    }

    public BlockState getBottomState() {
        return states[1];
    }

    public BlockState getSporadicState() {
        return states[2];
    }

    public BlockState getBetweenState() {
        return states[3];
    }

    public int getTopWeight() {
        return weights[0];
    }

    public int getBottomWeight() {
        return weights[1];
    }

    public int getSporadicWeight() {
        return weights[2];
    }

    public int getBetweenWeight() {
        return weights[3];
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getChance() {
        return chance;
    }

    public float getDensity() {
        return density;
    }

    public VeinFeatureConfig toConfig() {
        return new VeinFeatureConfig(getFiller(), getTopState(), getBottomState(), getSporadicState(), getBetweenState(), getTopWeight(), getBottomWeight(), getSporadicWeight(), getBetweenWeight(), getMinY(), getMaxY(), getDensity());
    }

    public JsonObject write() {
        JsonObject json = new JsonObject();

        if (filler == OreGeneration.FillerBlockType.OW) {
            json.addProperty("biome", "plains");
        } else if (filler == OreGeneration.FillerBlockType.NETHER) {
            json.addProperty("biome", "nether");
        } else if (filler == OreGeneration.FillerBlockType.END) {
            json.addProperty("biome", "theend");
        } else if (filler instanceof BlockMatchTest) {
            json.addProperty("filler", ((BlockMatchTest) filler).block.getRegistryName().toString());
        }

        JsonObject ores = new JsonObject();
        ores.addProperty("top", getTopState().getBlock().getRegistryName().toString());
        ores.addProperty("bottom", getBottomState().getBlock().getRegistryName().toString());
        ores.addProperty("sporadic", getSporadicState().getBlock().getRegistryName().toString());
        ores.addProperty("between", getBetweenState().getBlock().getRegistryName().toString());
        json.add("ores", ores);

        JsonObject weights = new JsonObject();
        weights.addProperty("top", getTopWeight());
        weights.addProperty("bottom", getBottomWeight());
        weights.addProperty("sporadic", getSporadicWeight());
        weights.addProperty("between", getBetweenWeight());
        json.add("weights", weights);

        json.addProperty("minimumY", getMinY());
        json.addProperty("maximumY", getMaxY());
        json.addProperty("chance", getChance());
        if (density < 1F) json.addProperty("density", getDensity());

        return json;
    }

    public static VeinConfiguration read(JsonObject json) {
        String biome = jsonGetAsOptional(json, "biome").map(JsonElement::getAsString).orElse(null);
        String filler = jsonGetAsOptional(json, "filler").map(JsonElement::getAsString).orElse(null);

        Block fillerBlock = filler == null ? null : ForgeRegistries.BLOCKS.getValue(new ResourceLocation(filler));
        Optional<RuleTest> test = biome != null ? OreGeneration.fillerOfBiome(Biome.Category.byName(biome.toUpperCase())) : Optional.empty();
        RuleTest ruleTest = Optional.ofNullable(test.orElse(fillerBlock != null ? new BlockMatchRuleTest(fillerBlock) : null)).orElse(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD);

        int minY = jsonGetAsOptional(json, "minimumY").map(JsonElement::getAsInt).orElse(8);
        int maxY = jsonGetAsOptional(json, "maximumY").map(JsonElement::getAsInt).orElse(64);
        int chance = jsonGetAsOptional(json, "chance").map(JsonElement::getAsInt).orElse(1);
        int top = 4;
        int bottom = 4;
        int sporadic = 1;
        int between = 2;
        float density = jsonGetAsOptional(json, "density").map(JsonElement::getAsFloat).orElse(1F);
        JsonObject weights = json.getAsJsonObject("weights");
        JsonObject ores = Objects.requireNonNull(json.getAsJsonObject("ores"));

        if (weights != null) {
            top = jsonGetAsOptional(weights, "top").map(JsonElement::getAsInt).orElse(4);
            bottom = jsonGetAsOptional(weights, "bottom").map(JsonElement::getAsInt).orElse(4);
            sporadic = jsonGetAsOptional(weights, "sporadic").map(JsonElement::getAsInt).orElse(1);
            between = jsonGetAsOptional(weights, "between").map(JsonElement::getAsInt).orElse(2);
        }

        Block topBlock = Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ores.get("top").getAsString())));
        Block bottomBlock = Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ores.get("bottom").getAsString())));
        Block sporadicBlock = Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ores.get("sporadic").getAsString())));
        Block betweenBlock = Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ores.get("between").getAsString())));

        return new VeinConfiguration(ruleTest, new BlockState[]{topBlock.getDefaultState(), bottomBlock.getDefaultState(), sporadicBlock.getDefaultState(), betweenBlock.getDefaultState()}, new int[]{top, bottom, sporadic, between}, new int[]{minY, maxY}, chance, density);
    }
}
