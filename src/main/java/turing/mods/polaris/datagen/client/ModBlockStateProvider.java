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
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.SubBlockGenerated;
import turing.mods.polaris.registry.*;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator dataGenerator, ExistingFileHelper fileHelper) {
        super(dataGenerator, Polaris.MODID, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createMaterialBlockStates();
        createFluidBlockStates();
        simpleBlock(BlockRegistry.CREATIVE_POWER_PROVIDER.get(), models().getExistingFile(modLoc("block/creative_power_provider")));
        MachineBlockStates.createCompressorModel(this, MachineRegistry.COMPRESSOR);
    }

    private void createFluidBlockStates() {
        for (FluidRegistryObject<?, ?, ?, ?> fluidRegistryObject : FluidRegistry.getFluids().values()) {
            simpleBlock(fluidRegistryObject.getBlock(), models().getExistingFile(modLoc("block/fluid")));
        }
    }

    private void createMaterialBlockStates() {
        for (MaterialRegistryObject materialRegistryObject : MaterialRegistry.getMaterials().values()) {
            if (materialRegistryObject.hasBlocks()) {
                for (RegistryObject<Block> block : materialRegistryObject.getBlocks()) {
                    if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.contains(block.get().asItem())) {
                        SubBlockGenerated subBlockGenerated = (SubBlockGenerated) block.get();
                        simpleBlock(block.get(), models().getExistingFile(modLoc("block/" + materialRegistryObject.get().textureSet.name().toLowerCase() + "_" + subBlockGenerated.getSubItem().name().toLowerCase())));
                    }
                }
            }
        }
    }

    public void directionalBlockFixed(Block block, Function<BlockState, ModelFile> func) {
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

    public void horizontalDirectionalBlockFixed(Block block, Function<BlockState, ModelFile> func) {
        getVariantBuilder(block)
                .forAllStates(blockState -> {
                    Direction direction = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    return ConfiguredModel.builder()
                            .modelFile(func.apply(blockState))
                            .rotationX(0)
                            .rotationY(direction.getAxis().isVertical() ? 0 : getX(direction))
                            .build();
                });
    }

    private int getX(Direction direction) {
        return (180 + direction.get2DDataValue() * 90) % 360;
    }
}
