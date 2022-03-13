package turing.mods.polaris.datagen;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.SubBlockItemGenerated;
import turing.mods.polaris.item.SubItemGenerated;
import turing.mods.polaris.item.ToolItemGenerated;
import turing.mods.polaris.registry.BlockRegistry;
import turing.mods.polaris.registry.ItemRegistry;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper fileHelper) {
        super(dataGenerator, blockTagsProvider, Polaris.MODID, fileHelper);
    }

    @Override
    protected void registerTags() {
        addMaterialTags();
        getOrCreateBuilder(PolarisTags.Items.CIRCUIT_LOGIC).add(ItemRegistry.VACUUM_TUBE.get(), ItemRegistry.NAND.get());
        getOrCreateItemBuilder(ItemTags.LOGS).add(BlockRegistry.RUBBER_LOG.get().asItem());
        getOrCreateItemBuilder(ItemTags.LOGS_THAT_BURN).add(BlockRegistry.RUBBER_LOG.get().asItem());
        getOrCreateItemBuilder(ItemTags.LEAVES).add(BlockRegistry.RUBBER_LEAVES.get().asItem());
        getOrCreateItemBuilder(ItemTags.SAPLINGS).add(BlockRegistry.RUBBER_SAPLING.get().asItem());
        getOrCreateItemBuilder(ItemTags.PLANKS).add(BlockRegistry.RUBBER_PLANKS.get().asItem());
        getOrCreateItemBuilder(PolarisTags.Items.RUBBER_LOGS).add(BlockRegistry.RUBBER_LOG.get().asItem());
        getOrCreateItemBuilder(PolarisTags.Items.RUBBER_RESIN).add(ItemRegistry.STICKY_RESIN.get());

        getOrCreateItemBuilder(PolarisTags.Items.forge("gems/flint")).add(Items.FLINT);
    }

    private void addMaterialTags() {
        for (MaterialRegistryObject materialRegistryObject : MaterialRegistry.getMaterials().values()) {
            for (RegistryObject<Item> item : materialRegistryObject.getItems()) {
                if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.containsValue(item.get())) {
                    if (item.get() instanceof SubItemGenerated) {
                        SubItemGenerated itemGenerated = (SubItemGenerated) item.get();
                        String parentTag = itemGenerated.getSubItem().name().toLowerCase() + "s";

                        getOrCreateBuilder(PolarisTags.Items.forge(parentTag + "/" + materialRegistryObject.getName())).add(itemGenerated);
                        getOrCreateBuilder(PolarisTags.Items.forge(parentTag)).add(itemGenerated);
                    } else if (item.get() instanceof ToolItemGenerated) {
                        ToolItemGenerated toolItemGenerated = (ToolItemGenerated) item.get();
                        String parentTag = "crafting_tools";

                        getOrCreateBuilder(PolarisTags.Items.CRAFTING_TOOLS).add(toolItemGenerated);
                        getOrCreateBuilder(PolarisTags.Items.mod(parentTag + "/" + toolItemGenerated.getSubItem().name().toLowerCase())).add(toolItemGenerated);
                    }
                }
            }
            if (materialRegistryObject.hasBlocks()) {
                for (RegistryObject<Block> block : materialRegistryObject.getBlocks()) {
                    if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.containsValue(block.get().asItem())) {
                        SubBlockItemGenerated blockItem = (SubBlockItemGenerated) block.get().asItem();
                        String parentTag = blockItem.getSubItem().name().toLowerCase() + "s";

                        getOrCreateBuilder(PolarisTags.Items.forge(parentTag + "/" + materialRegistryObject.getName())).add(blockItem);
                        getOrCreateBuilder(PolarisTags.Items.forge(parentTag)).add(blockItem);
                    }
                }
            }
        }
    }

    public TagsProvider.Builder<Item> getOrCreateItemBuilder(ITag.INamedTag<Item> tag) {
        return getOrCreateBuilder(tag);
    }
}
