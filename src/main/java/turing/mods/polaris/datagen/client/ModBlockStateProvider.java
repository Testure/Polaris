package turing.mods.polaris.datagen.client;

import mcp.MethodsReturnNonnullByDefault;
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
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.function.Function;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(DataGenerator dataGenerator, ExistingFileHelper fileHelper) {
        super(dataGenerator, Polaris.MODID, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createMaterialBlockStates();
        createFluidBlockStates();
        casingBlockStates();
        hullBlockStates();
        MachineBlockStates.createCreativePowerProviderModel(this);

        MachineBlockStates.createCompressorModel(this, MachineRegistry.COMPRESSOR);
        simpleBlock(BlockRegistry.RUBBER_LOG.get(), models().getExistingFile(modLoc("block/rubber_log")));
        simpleBlock(BlockRegistry.RUBBER_PLANKS.get(), models().getExistingFile(modLoc("block/rubber_planks")));
        simpleBlock(BlockRegistry.RUBBER_LEAVES.get(), models().getExistingFile(modLoc("block/rubber_leaves")));
        simpleBlock(BlockRegistry.RUBBER_SAPLING.get(), models().getExistingFile(modLoc("block/rubber_sapling")));
    }

    private void hullBlockStates() {
        for (int i = 0; i < BlockRegistry.HULLS.length; i++) {
            Block hull = BlockRegistry.HULLS[i].get();
            MachineBlockStates.createCasingModel(this, hull, i, true);
        }
    }

    private void casingBlockStates() {
        for (int i = 0; i < BlockRegistry.CASINGS.length; i++) {
            Block casing = BlockRegistry.CASINGS[i].get();
            MachineBlockStates.createCasingModel(this, casing, i);
        }
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
                    if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.containsValue(block.get().asItem())) {
                        SubBlockGenerated subBlockGenerated = (SubBlockGenerated) block.get();
                        if (subBlockGenerated.getSubItem() != SubItem.ORE) {
                            simpleBlock(block.get(),
                                    models().withExistingParent(Objects.requireNonNull(block.get().getRegistryName()).getPath(), modLoc("block/" + subBlockGenerated.getSubItem().name().toLowerCase()))
                                            .texture("all", "block/material_sets/" + materialRegistryObject.get().textureSet.name().toLowerCase() + "/" + subBlockGenerated.getSubItem().name().toLowerCase())
                                            .texture("overlay", "block/material_sets/" + materialRegistryObject.get().textureSet.name().toLowerCase() + "/" + subBlockGenerated.getSubItem().name().toLowerCase() + "_overlay")
                            );
                        } else {
                            simpleBlock(block.get(),
                                    models().withExistingParent(Objects.requireNonNull(block.get().getRegistryName()).getPath(), modLoc("block/" + subBlockGenerated.getSubItem().name().toLowerCase()))
                                            .texture("background", materialRegistryObject.get().textureSet.getOreBackground())
                                            .texture("all", "block/material_sets/" + materialRegistryObject.get().textureSet.name().toLowerCase() + "/" + subBlockGenerated.getSubItem().name().toLowerCase())
                                            .texture("overlay", "block/material_sets/" + materialRegistryObject.get().textureSet.name().toLowerCase() + "/" + subBlockGenerated.getSubItem().name().toLowerCase() + "_overlay")
                            );
                        }
                    }
                }
            }
        }
    }

    public void directionalBlockFixed(Block block, Function<BlockState, ModelFile> func) {
        getVariantBuilder(block)
                .forAllStates(blockState -> {
                    Direction direction = blockState.get(BlockStateProperties.FACING);
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
                    Direction direction = blockState.get(BlockStateProperties.HORIZONTAL_FACING);
                    return ConfiguredModel.builder()
                            .modelFile(func.apply(blockState))
                            .rotationX(0)
                            .rotationY(direction.getAxis().isVertical() ? 0 : getX(direction))
                            .build();
                });
    }

    private int getX(Direction direction) {
        return (180 + direction.getHorizontalIndex() * 90) % 360;
    }
}
