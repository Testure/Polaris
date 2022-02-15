package turing.mods.polaris.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.registry.BlockRegistry;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

public class ModLootTableProvider extends BaseLootTableProvider {
    public ModLootTableProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void addTables() {
        addMaterialBlockLootTables();
        lootTables.put(BlockRegistry.CREATIVE_POWER_PROVIDER.get(), createBasicTable("creative_power_provider", BlockRegistry.CREATIVE_POWER_PROVIDER.get()));
    }

    private void addMaterialBlockLootTables() {
        for (MaterialRegistryObject materialRegistryObject : MaterialRegistry.getMaterials().values()) {
            if (materialRegistryObject.hasBlocks()) {
                for (RegistryObject<Block> block : materialRegistryObject.getBlocks()) {
                    if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.contains(block.get().asItem())) {
                        lootTables.put(block.get(), createBasicTable(block.get().getRegistryName().getPath(), block.get()));
                    }
                }
            }
        }
    }
}
