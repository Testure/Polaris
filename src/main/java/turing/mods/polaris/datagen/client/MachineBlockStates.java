package turing.mods.polaris.datagen.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.registry.MachineRegistryObject;

import java.util.Objects;
import java.util.function.Function;

public class MachineBlockStates {
    private static Function<BlockState, ModelFile> machineModelProviderFunction(ModelFile normal, ModelFile powered) {
        return state -> state.getValue(BlockStateProperties.POWERED) ? powered : normal;
    }

    public static void createCasingModel(ModBlockStateProvider provider, Block block, int tier, boolean isHull) {
        String tierString = tier > 1 ? Integer.toString(tier - 1) : "";
        String blockName = Objects.requireNonNull(block.getRegistryName()).getPath();
        String texture = "block/machines/" + (tier == 0 ? "casing" : "machine" + tierString);
        String hullSpot = "block/machines/generator_output";
        ResourceLocation modelBase = provider.modLoc("block/machine_complete");

        BlockModelBuilder builder = provider.models().withExistingParent(blockName, modelBase);

        builder.texture("top_overlay", texture)
                .texture("bottom_overlay", texture)
                .texture("left_overlay", texture)
                .texture("right_overlay", texture)
                .texture("front_overlay", isHull ? hullSpot : texture)
                .texture("back_overlay", texture);
        builder.texture("top", texture)
                .texture("bottom", texture)
                .texture("left", texture)
                .texture("right", texture)
                .texture("front", texture)
                .texture("back", texture);

        if (isHull) provider.directionalBlockFixed(block, blockState -> builder);
        else provider.simpleBlock(block, builder);
    }

    public static void createCasingModel(ModBlockStateProvider provider, Block block, int tier) {
        createCasingModel(provider, block, tier, false);
    }

    public static void createCompressorModel(ModBlockStateProvider provider, MachineRegistryObject<?, ?, ?> machine) {
        for (int i = 0; i < machine.getBlocks().size(); i++) {
            int tier = i + 1;
            Block block = machine.getBlocks().get(i).get();

            String tierString = tier > 1 ? Integer.toString(tier - 1) : "";
            String blockName = Objects.requireNonNull(block.getRegistryName()).getPath();
            String front = "block/machines/compressor/front";
            String side = "block/machines/compressor/side";
            String top = "block/machines/compressor/top";
            String on = "_powered";
            ResourceLocation modelBase = provider.modLoc("block/machine_block" + tierString);

            BlockModelBuilder normal = provider.models().withExistingParent(blockName, modelBase);
            BlockModelBuilder powered = provider.models().withExistingParent(blockName + on, modelBase);

            normal.texture("top_overlay", top).texture("side_overlay", side).texture("front_overlay", front);
            powered.texture("top_overlay", top + on).texture("side_overlay", side + on).texture("front_overlay", front + on);

            provider.directionalBlockFixed(block, machineModelProviderFunction(normal, powered));
        }
    }
}
