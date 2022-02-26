package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.block.SubBlockGenerated;
import turing.mods.polaris.block.SubBlockItemGenerated;
import turing.mods.polaris.item.SubItemGenerated;
import turing.mods.polaris.item.ToolItemGenerated;
import turing.mods.polaris.material.MaterialBuilder;
import turing.mods.polaris.material.SubItem;

import java.util.*;

public class MaterialDeferredRegister {
    public static final Map<String, MaterialRegistryObject> materials = new HashMap<>();
    private final DeferredRegister<Item> itemRegister;
    private final DeferredRegister<Block> blockRegister;
    private static final ResourceLocation TEXTURE = Polaris.modLoc("block/fluids/autogenerated");

    public MaterialDeferredRegister() {
        itemRegister = DeferredRegister.create(ForgeRegistries.ITEMS, Polaris.MODID);
        blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, Polaris.MODID);
    }

    public Map<String, MaterialRegistryObject> getMaterials() {
        return materials;
    }

    public MaterialRegistryObject register(String name, MaterialBuilder builder) {
        if (materials.containsKey(name)) throw new IllegalStateException("Material already exists.");
        BasicFluidRegistryObject fluid = null;
        List<RegistryObject<Block>> blocks = new ArrayList<>();
        List<RegistryObject<Item>> items = new ArrayList<>();
        turing.mods.polaris.material.Material material = builder.build();
        if (builder.fluidStats != null) {
            FluidAttributes.Builder attributes = FluidAttributes.builder(TEXTURE, TEXTURE).color(builder.color).temperature(builder.fluidStats.temp).translationKey("material.polaris." + name);
            if (builder.fluidStats.isGaseous()) attributes.gaseous();
            fluid = FluidRegistry.register(name, attributes, () -> material);
        }
        if (builder.oreStats != null && (builder.existingItems == null || !builder.existingItems.containsKey(SubItem.ORE))) {
            SubBlockGenerated block = new SubBlockGenerated(name + "_ore", () -> material, SubItem.ORE, Material.STONE, 1);
            blocks.add(BlockRegistry.registerCustomItem(name + "_ore", () -> block, () -> new SubBlockItemGenerated(block, () -> material)));
        }
        if (builder.subItems.contains(SubItem.BLOCK) && (builder.existingItems == null || !builder.existingItems.containsKey(SubItem.BLOCK))) {
            SubBlockGenerated block = new SubBlockGenerated(name + "_block", () -> material, SubItem.BLOCK, Material.METAL, 1);
            blocks.add(BlockRegistry.registerCustomItem(name + "_block", () -> block, () -> new SubBlockItemGenerated(block, () -> material)));
        }

        for (int i = 0; i < material.getSubItems().size(); i++) {
            int finalI = i;
            if (builder.existingItems == null || !builder.existingItems.containsKey(material.getSubItems().get(i))) {
                switch (material.getSubItems().get(i)) {
                    case ORE:
                    case BLOCK:
                        break;
                    default:
                        if (!material.getSubItems().get(i).isTool()) items.add(ItemRegistry.register(name + "_" + material.getSubItems().get(i).name().toLowerCase(), () -> new SubItemGenerated(() -> material, material.getSubItems().get(finalI))));
                        else if (SubItem.getToolType(material.getSubItems().get(i)) != null) items.add(ItemRegistry.register(name + "_" + material.getSubItems().get(i).name().toLowerCase(), () -> new ToolItemGenerated(() -> material, material.getSubItems().get(finalI), Objects.requireNonNull(SubItem.getToolType(material.getSubItems().get(finalI))))));
                        break;
                }
            }
        }

        MaterialRegistryObject newMaterial = new MaterialRegistryObject(name, () -> material, items, blocks.size() > 0 ? blocks : null, fluid);
        materials.put(name, newMaterial);

        Polaris.LOGGER.info(String.format("created material '%s'", name));
        return newMaterial;
    }

    public void register(IEventBus bus) {
        itemRegister.register(bus);
        blockRegister.register(bus);
    }
}
