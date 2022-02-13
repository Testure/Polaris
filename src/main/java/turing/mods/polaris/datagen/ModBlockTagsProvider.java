package turing.mods.polaris.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;
import turing.mods.polaris.Polaris;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper fileHelper) {
        super(dataGenerator, Polaris.MODID, fileHelper);
    }

    @Override
    protected void addTags() {

    }

    public TagsProvider.Builder<Block> getOrCreateBlockBuilder(ITag.INamedTag<Block> tag) {
        return tag(tag);
    }
}
