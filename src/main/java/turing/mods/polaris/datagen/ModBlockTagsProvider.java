package turing.mods.polaris.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.SubBlockGenerated;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper fileHelper) {
        super(dataGenerator, Polaris.MODID, fileHelper);
    }

    @Override
    protected void addTags() {
        addMaterialTags();
    }

    private void addMaterialTags() {
        for (MaterialRegistryObject materialRegistryObject : MaterialRegistry.getMaterials().values()) {
            if (materialRegistryObject.hasBlocks()) {
                for (RegistryObject<Block> block : materialRegistryObject.getBlocks()) {
                    if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.contains(block.get().asItem())) {
                        SubBlockGenerated blockGenerated = (SubBlockGenerated) block.get();
                        String parentTag = blockGenerated.getSubItem().name().toLowerCase() + "s";

                        tag(PolarisTags.Blocks.forge(parentTag + "/" + materialRegistryObject.getName())).add(blockGenerated);
                        tag(PolarisTags.Blocks.forge(parentTag)).add(blockGenerated);
                    }
                }
            }
        }
    }

    public TagsProvider.Builder<Block> getOrCreateBlockBuilder(ITag.INamedTag<Block> tag) {
        return tag(tag);
    }
}
