package turing.mods.polaris.datagen.client;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Tuple;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.SubBlockGenerated;
import turing.mods.polaris.item.IBasicModeledItem;
import turing.mods.polaris.item.IHandheldItem;
import turing.mods.polaris.item.ILayeredItem;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.*;
import turing.mods.polaris.util.Lists;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

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
        public final Map<SubItem, ItemModelBuilder> toolModels;

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

            toolModels = Lists.mapOf(
                    new Tuple<>(SubItem.CROWBAR, crowbar),
                    new Tuple<>(SubItem.WRENCH, wrench),
                    new Tuple<>(SubItem.HAMMER, hammer),
                    new Tuple<>(SubItem.SOFT_HAMMER, softHammer),
                    new Tuple<>(SubItem.SWORD, sword),
                    new Tuple<>(SubItem.AXE, axe),
                    new Tuple<>(SubItem.SHOVEL, shovel),
                    new Tuple<>(SubItem.PICKAXE, pickaxe),
                    new Tuple<>(SubItem.HOE, hoe)
            );
        }
    }

    @Override
    protected void registerModels() {
        SingletonModels models = new SingletonModels(this);
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));

        withExistingParent("creative_power_provider", modLoc("block/creative_power_provider"));

        casingModels();
        hullModels();

        for (MachineRegistryObject<?, ?, ?> machine : MachineRegistry.getMachines().values()) {
            for (RegistryObject<? extends Block> block : machine.getBlocks()) {
                withExistingParent(block.get().getRegistryName().getPath(), modLoc("block/" + block.get().getRegistryName().getPath()));
            }
        }

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

        for (MaterialRegistryObject materialRegistryObject : MaterialRegistry.getMaterials().values()) {
            if (materialRegistryObject.hasBlocks()) {
                for (RegistryObject<Block> block : materialRegistryObject.getBlocks()) {
                    if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.contains(block.get().asItem())) {
                        SubBlockGenerated subBlockGenerated = (SubBlockGenerated) block.get();
                        withExistingParent(block.get().getRegistryName().getPath(), modLoc("block/" + materialRegistryObject.get().textureSet.name().toLowerCase() + "_" + subBlockGenerated.getSubItem().name().toLowerCase()));
                    }
                }
            }
            for (RegistryObject<Item> item : materialRegistryObject.getItems()) {
                if (materialRegistryObject.get().existingItems == null || !materialRegistryObject.get().existingItems.contains(item.get())) {
                    String itemName = item.get().getRegistryName().getPath();
                    SubItem subItem = SubItem.valueOf(itemName.replaceFirst(materialRegistryObject.getName() + "_", "").toUpperCase());
                    if (!subItem.isTool())
                        builder(itemGenerated, itemName, subItem.name().toLowerCase(), materialRegistryObject.get().textureSet.name(), true);
                    else getBuilder(itemName).parent(models.toolModels.get(subItem));
                }
            }
        }

        for (FluidRegistryObject<?, ?, ?, ?> fluidRegistryObject : FluidRegistry.getFluids().values()) {
            withExistingParent(fluidRegistryObject.getName() + "_bucket", modLoc("item/bucket"));
        }
    }

    private void hullModels() {
        for (RegistryObject<Block> hull : BlockRegistry.HULLS) {
            withExistingParent(hull.get().getRegistryName().getPath(), modLoc("block/" + hull.get().getRegistryName().getPath()));
        }
    }

    private void casingModels() {
        for (RegistryObject<Block> casing : BlockRegistry.CASINGS) {
            withExistingParent(casing.get().getRegistryName().getPath(), modLoc("block/" + casing.get().getRegistryName().getPath()));
        }
    }

    public ItemModelBuilder builder(ModelFile modelFile, String name, String subName, String textureSetName, @Nullable Boolean overlay) {
        ItemModelBuilder builder = getBuilder(name).parent(modelFile).texture("layer0", "item/material_sets/" + textureSetName.toLowerCase() + "/" + subName);

        if (Boolean.TRUE.equals(overlay)) {
            builder.texture("layer1", "item/material_sets/" + textureSetName.toLowerCase() + "/" + subName + "_overlay");
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
