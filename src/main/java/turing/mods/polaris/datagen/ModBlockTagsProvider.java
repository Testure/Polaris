package turing.mods.polaris.datagen;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.SubBlockGenerated;
import turing.mods.polaris.registry.BlockRegistry;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper fileHelper) {
        super(dataGenerator, Polaris.MODID, fileHelper);
    }

    @Override
    protected void registerTags() {
        addMaterialTags();
        getOrCreateBuilder(BlockTags.LOGS).add(BlockRegistry.RUBBER_LOG.get());
        getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).add(BlockRegistry.RUBBER_LOG.get());
        getOrCreateBuilder(BlockTags.LEAVES).add(BlockRegistry.RUBBER_LEAVES.get());
        getOrCreateBuilder(BlockTags.PLANKS).add(BlockRegistry.RUBBER_PLANKS.get());
        getOrCreateBuilder(PolarisTags.Blocks.RUBBER_LOGS).add(BlockRegistry.RUBBER_LOG.get());
    }

    private void addMaterialTags() {
        for (MaterialRegistryObject materialRegistryObject : MaterialRegistry.getMaterials().values()) {
            if (materialRegistryObject.hasBlocks()) {
                for (RegistryObject<Block> block : materialRegistryObject.getBlocks()) {
                    if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.containsValue(block.get().asItem())) {
                        SubBlockGenerated blockGenerated = (SubBlockGenerated) block.get();
                        String parentTag = blockGenerated.getSubItem().name().toLowerCase() + "s";

                        getOrCreateBuilder(PolarisTags.Blocks.forge(parentTag + "/" + materialRegistryObject.getName())).add(blockGenerated);
                        getOrCreateBuilder(PolarisTags.Blocks.forge(parentTag)).add(blockGenerated);
                    }
                }
            }
        }
    }

    public TagsProvider.Builder<Block> getOrCreateBlockBuilder(ITag.INamedTag<Block> tag) {
        return getOrCreateBuilder(tag);
    }
}
