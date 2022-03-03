package turing.mods.polaris.datagen;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.block.CasingBlock;
import turing.mods.polaris.block.HullBlock;
import turing.mods.polaris.registry.*;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModLootTableProvider extends BaseLootTableProvider {
    public ModLootTableProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void addTables() {
        addMachineLootTables();
        addMaterialBlockLootTables();
        addCasingLootTables();
        addHullLootTables();

        lootTables.put(BlockRegistry.CREATIVE_POWER_PROVIDER.get(), createBasicTable("creative_power_provider", BlockRegistry.CREATIVE_POWER_PROVIDER.get()));
    }

    private void addHullLootTables() {
        for (RegistryObject<HullBlock> hull : BlockRegistry.HULLS) {
            lootTables.put(hull.get(), createBasicTable(hull.get().getRegistryName().getPath(), hull.get()));
        }
    }

    private void addCasingLootTables() {
        for (RegistryObject<CasingBlock> casing : BlockRegistry.CASINGS) {
            lootTables.put(casing.get(), createBasicTable(casing.get().getRegistryName().getPath(), casing.get()));
        }
    }

    private void addMachineLootTables() {
        for (MachineRegistryObject<?, ?, ?> machine : MachineRegistry.getMachines().values()) {
            for (RegistryObject<? extends Block> block : machine.getBlocks()) {
                lootTables.put(block.get(), createBasicTable(block.get().getRegistryName().getPath(), block.get()));
            }
        }
    }

    private void addMaterialBlockLootTables() {
        for (MaterialRegistryObject materialRegistryObject : MaterialRegistry.getMaterials().values()) {
            if (materialRegistryObject.hasBlocks()) {
                for (RegistryObject<Block> block : materialRegistryObject.getBlocks()) {
                    if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.containsValue(block.get().asItem())) {
                        lootTables.put(block.get(), createBasicTable(block.get().getRegistryName().getPath(), block.get()));
                    }
                }
            }
        }
    }
}
