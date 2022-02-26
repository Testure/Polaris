package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import turing.mods.polaris.block.SubBlockGenerated;
import turing.mods.polaris.item.SubItemGenerated;
import turing.mods.polaris.item.ToolItemGenerated;
import turing.mods.polaris.material.Material;
import turing.mods.polaris.material.SubItem;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class MaterialRegistryObject {
    protected String name;
    protected Supplier<Material> material;
    protected List<RegistryObject<Item>> items = new ArrayList<>();
    protected List<RegistryObject<Block>> blocks;
    protected BasicFluidRegistryObject fluid;

    public MaterialRegistryObject(String name, Supplier<Material> material, List<RegistryObject<Item>> items, @Nullable List<RegistryObject<Block>> blocks, @Nullable BasicFluidRegistryObject fluid) {
        this.name = name;
        this.material = material;
        this.items.addAll(items);
        this.fluid = fluid;

        if (blocks != null) this.blocks = blocks;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public BasicFluidRegistryObject getFluid() {
        return fluid;
    }

    public boolean hasBlocks() {
        return this.blocks != null;
    }

    public List<RegistryObject<Block>> getBlocks() {
        if (!hasBlocks()) throw new NullPointerException("Attempt to get blocks list that doesn't exist!");
        return Collections.unmodifiableList(blocks);
    }

    public Item getItemFromSubItem(SubItem subItem) {
        if (hasBlocks() && blocks.stream().anyMatch(r -> ((SubBlockGenerated) r.get()).getSubItem() == subItem)) {
            Block found = null;
            if (get().existingItems != null) {
                for (Map.Entry<SubItem, Item> item : get().existingItems.entrySet()) {
                    if (Block.byItem(item.getValue()) != Blocks.AIR && item.getKey() == subItem) {
                        found = Block.byItem(item.getValue());
                        break;
                    }
                }
            }
            if (found == null)
                for (RegistryObject<Block> block : getBlocks()) {
                    if (((SubBlockGenerated) block.get()).getSubItem() == subItem) {
                        found = block.get();
                        break;
                    }
                }
            if (found == null) throw new NullPointerException();
            return found.asItem();
        }
        Item found = null;
        if (get().existingItems != null) {
            for (Map.Entry<SubItem, Item> item : get().existingItems.entrySet()) {
                if (item.getKey() == subItem) {
                    found = item.getValue();
                    break;
                }
            }
        }
        if (found == null)
            for (RegistryObject<Item> item : getItems()) {
                if (item.get() instanceof SubItemGenerated && ((SubItemGenerated) item.get()).getSubItem() == subItem) {
                    found = item.get();
                    break;
                }
                if (item.get() instanceof ToolItemGenerated && ((ToolItemGenerated) item.get()).getSubItem() == subItem) {
                    found = item.get();
                    break;
                }
            }
        if (found == null) throw new NullPointerException();
        return found;
    }

    public boolean hasSubItem(SubItem subItem) {
        return get().getSubItems().stream().anyMatch(s -> s == subItem);
    }

    public List<RegistryObject<Item>> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean hasItem(Item item) {
        for (RegistryObject<Item> registryObject : getItems()) if (Objects.requireNonNull(registryObject.get().getRegistryName()).getPath().equals(Objects.requireNonNull(item.getRegistryName()).getPath())) return true;
        if (get().existingItems != null)
            for (Item existingItem : get().existingItems.values()) if (Objects.requireNonNull(existingItem.getRegistryName()).getPath().equals(Objects.requireNonNull(item.getRegistryName()).getPath())) return true;
        return false;
    }

    public Material get() {
        return material.get();
    }

    @OnlyIn(Dist.CLIENT)
    public void doClientSetup(FMLClientSetupEvent event, ItemColors itemColors, BlockColors blockColors) {
        event.enqueueWork(() -> getItems().forEach(item -> itemColors.register((a, b) -> get().getColor(a, b), item.get())));
        if (hasBlocks())
            event.enqueueWork(() -> getBlocks().forEach(block -> {
                blockColors.register(((SubBlockGenerated) block.get())::getColor, block.get());
                itemColors.register((stack, layer) -> get().color, block.get().asItem());
                RenderTypeLookup.setRenderLayer(block.get(), ((SubBlockGenerated) block.get()).getRenderType());
            }));
    }
}
