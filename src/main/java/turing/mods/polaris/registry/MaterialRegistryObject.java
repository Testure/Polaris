package turing.mods.polaris.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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
            Optional<RegistryObject<Block>> object = blocks.stream().map(r -> {
                if (((SubBlockGenerated) r.get()).getSubItem() == subItem) return r;
                return null;
            }).findFirst();
            assert object.isPresent();
            return object.get().get().asItem();
        }
        Optional<RegistryObject<Item>> object = items.stream().map(r -> {
            if (((SubItemGenerated) r.get()).getSubItem() == subItem) return r;
            return null;
        }).findFirst();
        assert object.isPresent();
        return object.get().get();
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
}
