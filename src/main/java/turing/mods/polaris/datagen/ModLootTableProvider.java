package turing.mods.polaris.datagen;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.ApplyBonus;
import net.minecraft.loot.functions.ExplosionDecay;
import net.minecraft.loot.functions.LimitCount;
import net.minecraft.loot.functions.SetCount;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.block.CasingBlock;
import turing.mods.polaris.block.HullBlock;
import turing.mods.polaris.block.RubberLog;
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
        lootTables.put(BlockRegistry.RUBBER_PLANKS.get(), createBasicTable("rubber_planks", BlockRegistry.RUBBER_PLANKS.get()));
        lootTables.put(BlockRegistry.RUBBER_SAPLING.get(), createBasicTable("rubber_sapling", BlockRegistry.RUBBER_SAPLING.get()));
        lootTables.put(BlockRegistry.RUBBER_LOG.get(), LootTable.builder().addLootPool(LootPool.builder()
                .name("rubber_log")
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(BlockRegistry.RUBBER_LOG.get()))
        ).addLootPool(LootPool.builder()
                .name("sticky_resin")
                .acceptCondition(BlockStateProperty.builder(BlockRegistry.RUBBER_LOG.get()).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withBoolProp(RubberLog.NATURAL, true)))
                .acceptFunction(SetCount.builder(RandomValueRange.of(1F, 2F)))
                .acceptFunction(ApplyBonus.uniformBonusCount(Enchantments.FORTUNE))
                .acceptFunction(LimitCount.func_215911_a(IntClamper.func_215843_a(1, 4)))
                .acceptFunction(ExplosionDecay.builder())
                .addEntry(ItemLootEntry.builder(ItemRegistry.STICKY_RESIN.get()))
        ));
        lootTables.put(BlockRegistry.RUBBER_LEAVES.get(), LootTable.builder().addLootPool(LootPool.builder()
                .name("sapling")
                .acceptFunction(SetCount.builder(RandomValueRange.of(0F, 1F)))
                .acceptFunction(LimitCount.func_215911_a(IntClamper.func_215843_a(0, 1)))
                .acceptFunction(ExplosionDecay.builder())
                .addEntry(ItemLootEntry.builder(BlockRegistry.RUBBER_SAPLING.get()))
        ).addLootPool(LootPool.builder()
                .name("leaves")
                .acceptCondition(Alternative.builder(MatchTool.builder(ItemPredicate.Builder.create().item(Items.SHEARS)), MatchTool.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))))))
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(BlockRegistry.RUBBER_LEAVES.get()))
        ));
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
