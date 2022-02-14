package turing.mods.polaris.datagen;

import net.minecraft.data.DataGenerator;
import turing.mods.polaris.registry.BlockRegistry;

public class ModLootTableProvider extends BaseLootTableProvider {
    public ModLootTableProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void addTables() {
        lootTables.put(BlockRegistry.CREATIVE_POWER_PROVIDER.get(), createBasicTable("creative_power_provider", BlockRegistry.CREATIVE_POWER_PROVIDER.get()));
    }
}
