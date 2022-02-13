package turing.mods.polaris.datagen.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.registry.FluidRegistry;
import turing.mods.polaris.registry.FluidRegistryObject;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator dataGenerator, ExistingFileHelper fileHelper) {
        super(dataGenerator, Polaris.MODID, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (FluidRegistryObject<?, ?, ?, ?> fluidRegistryObject : FluidRegistry.getFluids().values()) {
            simpleBlock(fluidRegistryObject.getBlock(), models().getExistingFile(modLoc("block/fluid")));
        }
    }

    private void directionalBlockFixed(Block block, Function<BlockState, ModelFile> func) {
        getVariantBuilder(block)
                .forAllStates(blockState -> {
                    Direction direction = blockState.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(func.apply(blockState))
                            .rotationX(0)
                            .rotationY(direction.getAxis().isVertical() ? 0 : getX(direction))
                            .build();
                });
    }

    private int getX(Direction direction) {
        return (180 + direction.getStepX() * 90) % 360;
    }
}
