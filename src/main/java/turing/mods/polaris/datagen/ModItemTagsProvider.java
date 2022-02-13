package turing.mods.polaris.datagen;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.registry.ItemRegistry;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper fileHelper) {
        super(dataGenerator, blockTagsProvider, Polaris.MODID, fileHelper);
    }

    @Override
    protected void addTags() {
        tag(PolarisTags.Items.CIRCUIT_LOGIC).add(ItemRegistry.VACUUM_TUBE.get(), ItemRegistry.NAND.get());
    }

    public TagsProvider.Builder<Item> getOrCreateItemBuilder(ITag.INamedTag<Item> tag) {
        return tag(tag);
    }
}
