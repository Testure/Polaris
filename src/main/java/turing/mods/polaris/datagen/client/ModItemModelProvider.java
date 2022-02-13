package turing.mods.polaris.datagen.client;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.item.IBasicModeledItem;
import turing.mods.polaris.item.IHandheldItem;
import turing.mods.polaris.item.ILayeredItem;
import turing.mods.polaris.registry.FluidRegistry;
import turing.mods.polaris.registry.FluidRegistryObject;
import turing.mods.polaris.registry.ItemRegistry;

import javax.annotation.Nullable;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator dataGenerator, ExistingFileHelper fileHelper) {
        super(dataGenerator, Polaris.MODID, fileHelper);
    }

    protected static class SingletonModels {
        public final ItemModelBuilder crowbar;
        public final ItemModelBuilder wrench;
        public final ItemModelBuilder hammer;
        public final ItemModelBuilder softHammer;
        public final ItemModelBuilder sword;
        public final ItemModelBuilder axe;
        public final ItemModelBuilder shovel;
        public final ItemModelBuilder pickaxe;
        public final ItemModelBuilder hoe;

        public SingletonModels(ModItemModelProvider provider) {
            crowbar = provider.toolBuilder("crow_bar", "item/material_sets/tools/crowbar", "item/material_sets/tools/crowbar_overlay");
            wrench = provider.toolBuilder("wrench", "item/material_sets/tools/wrench", null);
            hammer = provider.stickToolBuilder("hammer", "item/material_sets/tools/hammer");
            softHammer = provider.stickToolBuilder("soft_hammer", "item/material_sets/tools/soft_hammer");
            sword = provider.stickToolBuilder("sword", "item/material_sets/tools/sword");
            shovel = provider.stickToolBuilder("shovel", "item/material_sets/tools/shovel");
            axe = provider.stickToolBuilder("axe", "item/material_sets/tools/axe");
            pickaxe = provider.stickToolBuilder("pickaxe", "item/material_sets/tools/pickaxe");
            hoe = provider.stickToolBuilder("hoe", "item/material_sets/tools/hoe");
        }
    }

    @Override
    protected void registerModels() {
        SingletonModels models = new SingletonModels(this);
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));

        for (RegistryObject<Item> itemRegistryObject : ItemRegistry.ITEMS) {
            String itemName = itemRegistryObject.get().getRegistryName().getPath();
            int modelCode = 0;
            if (itemRegistryObject.get() instanceof IBasicModeledItem) modelCode = 1;
            if (itemRegistryObject.get() instanceof IHandheldItem) modelCode = 3;
            if (itemRegistryObject.get() instanceof ILayeredItem) modelCode += 1;

            switch (modelCode) {
                case 1:
                case 3:
                    basicBuilder(modelCode == 1 ? itemGenerated : itemHandheld, itemName, "item/" + itemName, null);
                    break;
                case 2:
                case 4:
                    basicBuilder(modelCode == 2 ? itemGenerated : itemHandheld, itemName, "item/" + itemName, "item/" + itemName + "_overlay");
                    break;
                case 0:
                default:
                    break;
            }
        }

        for (FluidRegistryObject<?, ?, ?, ?> fluidRegistryObject : FluidRegistry.getFluids().values()) {
            withExistingParent(fluidRegistryObject.getName() + "_bucket", modLoc("item/bucket"));
        }
    }

    public ItemModelBuilder builder(ModelFile modelFile, String name, String subName, String materialName, @Nullable Boolean overlay) {
        ItemModelBuilder builder = getBuilder(name).parent(modelFile).texture("layer0", "item/material_sets/" + materialName.toLowerCase() + "/" + subName);

        if (Boolean.TRUE.equals(overlay)) {
            builder.texture("layer1", "item/material_sets/" + materialName.toLowerCase() + "/" + subName + "_overlay");
        }

        return builder;
    }

    public ItemModelBuilder basicBuilder(ModelFile modelFile, String name, String layer0, @Nullable String layer1) {
        ItemModelBuilder builder = getBuilder(name).parent(modelFile).texture("layer0", layer0);
        return layer1 != null ? builder.texture("layer1", layer1) : builder;
    }

    public ItemModelBuilder stickToolBuilder(String name, String layer1) {
        return getBuilder(name).parent(getExistingFile(mcLoc("item/handheld"))).texture("layer0", "item/material_sets/tools/handle").texture("layer1", layer1);
    }

    public ItemModelBuilder toolBuilder(String name, String layer0, @Nullable String layer1) {
        ItemModelBuilder builder = getBuilder(name).parent(getExistingFile(mcLoc("item/handheld"))).texture("layer0", layer0);
        return layer1 != null ? builder.texture("layer1", layer1) : builder;
    }
}
