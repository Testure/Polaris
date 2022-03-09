package turing.mods.polaris.world;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.template.RuleTest;
import turing.mods.polaris.block.SubBlockItemGenerated;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.MaterialRegistryObject;
import turing.mods.polaris.util.BlockMatchTest;
import turing.mods.polaris.util.BlockStateMatchTest;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class VeinBuilder {
    private RuleTest filler;
    private int chance;
    private int minY;
    private int maxY;
    private int topW, bottomW, sporadicW, betweenW;
    private BlockState top, bottom, sporadic, between;

    private VeinBuilder() {

    }


    public VeinBuilder withChance(int chance) {
        this.chance = MathHelper.clamp(chance, 1, 10);
        return this;
    }

    public VeinBuilder withChance(float chance) {
        this.chance = (int)(MathHelper.clamp(chance, 0.1F, 1.0F) * 10);
        return this;
    }

    public VeinBuilder minimumYLevel(int minY) {
        this.minY = MathHelper.clamp(minY, 1, 64);
        return this;
    }

    public VeinBuilder maximumYLevel(int maxY) {
        this.maxY = MathHelper.clamp(maxY, 8, 128);
        return this;
    }

    public VeinBuilder atYLevel(Vector2i y) {
        minimumYLevel(y.x);
        return maximumYLevel(y.y);
    }

    public VeinBuilder withFiller(Block block) {
        this.filler = new BlockMatchTest(block);
        return this;
    }

    public VeinBuilder withFiller(BlockState state) {
        this.filler = new BlockStateMatchTest(state);
        return this;
    }

    public VeinBuilder inDimension(Biome.Category biome) {
        this.filler = OreGeneration.fillerOfBiome(biome).orElse(OreGeneration.FillerBlockType.OW);
        return this;
    }

    public VeinBuilder inDimension(int dimID) {
        switch (dimID) {
            default:
            case 0: this.filler = OreGeneration.FillerBlockType.OW;
                break;
            case 1: this.filler = OreGeneration.FillerBlockType.NETHER;
                break;
            case 2: this.filler = OreGeneration.FillerBlockType.END;
                break;
        }
        return this;
    }

    public VeinBuilder defaultChances(int baseWeight) {
        baseWeight = MathHelper.clamp(baseWeight, 1, 15);
        this.topW = baseWeight;
        this.bottomW = baseWeight;
        this.sporadicW = Math.max((baseWeight - (baseWeight - 1)), 1);
        this.betweenW = Math.max((baseWeight / 2), 1);
        return this;
    }

    public VeinBuilder sporadicChance(int chance) {
        this.sporadicW = MathHelper.clamp(chance, 1, 15);
        return this;
    }

    public VeinBuilder betweenChance(int chance) {
        this.betweenW = MathHelper.clamp(chance, 1, 15);
        return this;
    }

    public VeinBuilder topChance(int chance) {
        this.topW = MathHelper.clamp(chance, 1, 15);
        return this;
    }

    public VeinBuilder bottomChance(int chance) {
        this.bottomW = MathHelper.clamp(chance, 1, 15);
        return this;
    }

    public VeinBuilder mainChance(int chance) {
        topChance(chance);
        return bottomChance(chance);
    }

    public VeinBuilder topOre(BlockState state) {
        this.top = state;
        return this;
    }

    public VeinBuilder bottomOre(BlockState state) {
        this.bottom = state;
        return this;
    }

    public VeinBuilder sporadicOre(BlockState state) {
        this.sporadic = state;
        return this;
    }

    public VeinBuilder betweenOre(BlockState state) {
        this.between = state;
        return this;
    }

    public VeinBuilder topOre(MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.ORE)) throw new NullPointerException("Material does not have an ore!");
        this.top = ((SubBlockItemGenerated) material.getItemFromSubItem(SubItem.ORE)).getBlock().getDefaultState();
        return this;
    }

    public VeinBuilder bottomOre(MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.ORE)) throw new NullPointerException("Material does not have an ore!");
        this.bottom = ((SubBlockItemGenerated) material.getItemFromSubItem(SubItem.ORE)).getBlock().getDefaultState();
        return this;
    }

    public VeinBuilder sporadicOre(MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.ORE)) throw new NullPointerException("Material does not have an ore!");
        this.sporadic = ((SubBlockItemGenerated) material.getItemFromSubItem(SubItem.ORE)).getBlock().getDefaultState();
        return this;
    }

    public VeinBuilder betweenOre(MaterialRegistryObject material) {
        if (!material.hasSubItem(SubItem.ORE)) throw new NullPointerException("Material does not have an ore!");
        this.between = ((SubBlockItemGenerated) material.getItemFromSubItem(SubItem.ORE)).getBlock().getDefaultState();
        return this;
    }

    public VeinBuilder withOres(BlockState... states) {
        this.top = states[0];
        this.bottom = states[1];
        this.sporadic = states[2];
        this.between = states[3];
        return this;
    }

    public VeinBuilder withOres(MaterialRegistryObject... materials) {
        if (!materials[0].hasSubItem(SubItem.ORE) || !materials[1].hasSubItem(SubItem.ORE) || !materials[2].hasSubItem(SubItem.ORE) || !materials[3].hasSubItem(SubItem.ORE))
            throw new NullPointerException("Material does not have an ore!");
        this.top = ((SubBlockItemGenerated) materials[0].getItemFromSubItem(SubItem.ORE)).getBlock().getDefaultState();
        this.bottom = ((SubBlockItemGenerated) materials[1].getItemFromSubItem(SubItem.ORE)).getBlock().getDefaultState();
        this.sporadic = ((SubBlockItemGenerated) materials[2].getItemFromSubItem(SubItem.ORE)).getBlock().getDefaultState();
        this.between = ((SubBlockItemGenerated) materials[3].getItemFromSubItem(SubItem.ORE)).getBlock().getDefaultState();
        return this;
    }

    public static VeinBuilder builder() {
        return new VeinBuilder();
    }

    public VeinConfiguration build() {
        return new VeinConfiguration(filler, new BlockState[]{top, bottom, sporadic, between}, new int[]{topW, bottomW, sporadicW, betweenW}, new int[]{minY, maxY}, chance);
    }

    public VeinConfiguration build(Consumer<VeinConfiguration> consumer) {
        VeinConfiguration built = build();
        consumer.accept(built);
        return built;
    }
}
