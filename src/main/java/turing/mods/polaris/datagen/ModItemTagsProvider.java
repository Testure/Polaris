package turing.mods.polaris.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.SubBlockItemGenerated;
import turing.mods.polaris.item.SubItemGenerated;
import turing.mods.polaris.registry.ItemRegistry;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper fileHelper) {
        super(dataGenerator, blockTagsProvider, Polaris.MODID, fileHelper);
    }

    @Override
    protected void addTags() {
        addMaterialTags();
        tag(PolarisTags.Items.CIRCUIT_LOGIC).add(ItemRegistry.VACUUM_TUBE.get(), ItemRegistry.NAND.get());
    }

    private void addMaterialTags() {
        for (MaterialRegistryObject materialRegistryObject : MaterialRegistry.getMaterials().values()) {
            for (RegistryObject<Item> item : materialRegistryObject.getItems()) {
                if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.contains(item.get())) {
                    SubItemGenerated itemGenerated = (SubItemGenerated) item.get();
                    String parentTag = itemGenerated.getSubItem().name().toLowerCase() + "s";

                    tag(PolarisTags.Items.forge(parentTag + "/" + materialRegistryObject.getName())).add(itemGenerated);
                    tag(PolarisTags.Items.forge(parentTag)).add(itemGenerated);
                }
            }
            if (materialRegistryObject.hasBlocks()) {
                for (RegistryObject<Block> block : materialRegistryObject.getBlocks()) {
                    if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.contains(block.get().asItem())) {
                        SubBlockItemGenerated blockItem = (SubBlockItemGenerated) block.get().asItem();
                        String parentTag = blockItem.getSubItem().name().toLowerCase() + "s";

                        tag(PolarisTags.Items.forge(parentTag + "/" + materialRegistryObject.getName())).add(blockItem);
                        tag(PolarisTags.Items.forge(parentTag)).add(blockItem);
                    }
                }
            }
        }
    }

    public TagsProvider.Builder<Item> getOrCreateItemBuilder(ITag.INamedTag<Item> tag) {
        return tag(tag);
    }
}
