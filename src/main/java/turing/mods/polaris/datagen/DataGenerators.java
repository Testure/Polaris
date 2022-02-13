package turing.mods.polaris.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.datagen.client.ModBlockStateProvider;
import turing.mods.polaris.datagen.client.ModItemModelProvider;

@Mod.EventBusSubscriber(modid = Polaris.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {

    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        dataGenerator.addProvider(new ModBlockStateProvider(dataGenerator, fileHelper));
        dataGenerator.addProvider(new ModItemModelProvider(dataGenerator, fileHelper));
        dataGenerator.addProvider(new ModLootTableProvider(dataGenerator));

        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(dataGenerator, fileHelper);
        ModItemTagsProvider itemTags = new ModItemTagsProvider(dataGenerator, blockTags, fileHelper);
        ModFluidTagsProvider fluidTags = new ModFluidTagsProvider(dataGenerator, fileHelper);
        ModRecipeProvider recipes = new ModRecipeProvider(dataGenerator);

        dataGenerator.addProvider(blockTags);
        dataGenerator.addProvider(itemTags);
        dataGenerator.addProvider(fluidTags);
        dataGenerator.addProvider(recipes);
    }
}
