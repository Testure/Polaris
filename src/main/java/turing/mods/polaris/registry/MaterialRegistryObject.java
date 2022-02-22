package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.block.SubBlockGenerated;
import turing.mods.polaris.item.SubItemGenerated;
import turing.mods.polaris.material.Material;
import turing.mods.polaris.material.SubItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
            RegistryObject<Block> found = null;
            for (RegistryObject<Block> block : getBlocks()) {
                if (((SubBlockGenerated) block.get()).getSubItem() == subItem) {
                    found = block;
                    break;
                }
            }
            if (found == null) throw new NullPointerException();
            return found.get().asItem();
        }
        RegistryObject<Item> found = null;
        for (RegistryObject<Item> item : getItems()) {
            if (((SubItemGenerated) item.get()).getSubItem() == subItem) {
                found = item;
                break;
            }
        }
        if (found == null) throw new NullPointerException();
        return found.get();
    }

    public boolean hasSubItem(SubItem subItem) {
        return get().getSubItems().stream().anyMatch(s -> s == subItem);
    }

    public List<RegistryObject<Item>> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Material get() {
        return material.get();
    }

    @OnlyIn(Dist.CLIENT)
    public void doClientSetup(ItemColors itemColors, BlockColors blockColors) {
        getItems().forEach(item -> itemColors.register((a, b) -> get().getColor(a, b), item.get()));
        if (hasBlocks())
            getBlocks().forEach(block -> {
                blockColors.register(((SubBlockGenerated) block.get())::getColor, block.get());
                itemColors.register((stack, layer) -> get().color, block.get().asItem());
                RenderTypeLookup.setRenderLayer(block.get(), ((SubBlockGenerated) block.get()).getRenderType());
            });
    }
}
