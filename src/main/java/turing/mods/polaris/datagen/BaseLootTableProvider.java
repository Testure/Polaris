package turing.mods.polaris.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.CopyName;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.loot.functions.SetContents;
import net.minecraft.util.ResourceLocation;
import turing.mods.polaris.Polaris;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseLootTableProvider extends LootTableProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    private final DataGenerator dataGenerator;

    public BaseLootTableProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
        this.dataGenerator = dataGenerator;
    }

    protected abstract void addTables();

    protected LootTable.Builder createStandardTable(String name, Block block) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(block)
                        .apply(CopyName.copyName(CopyName.Source.BLOCK_ENTITY))
                        .apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY)
                                .copy("energy", "BlockEntityTag.energy", CopyNbt.Action.REPLACE)
                        )
                        .apply(SetContents.setContents()
                                .withEntry(DynamicLootEntry.dynamicEntry(Polaris.mcLoc("contents")))
                        )
                );
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder createBasicTable(String name, Block block) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(block));
        return LootTable.lootTable().withPool(builder);
    }

    @Override
    public void run(DirectoryCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();

        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootParameterSets.BLOCK).build());
        }

        writeTables(cache, tables);
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.dataGenerator.getOutputFolder();

        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable), path);
            } catch (IOException exception) {
                Polaris.LOGGER.error("Couldn't write loot table {}", path, exception);
            }
        });
    }

    @Nonnull
    @Override
    public String getName() {
        return "Polaris LootTables";
    }
}
