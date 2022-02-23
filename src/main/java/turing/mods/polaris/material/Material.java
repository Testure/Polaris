package turing.mods.polaris.material;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import turing.mods.polaris.item.ITintedItem;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class Material implements ITintedItem {
    public int mass;
    public int color;
    protected String name;
    protected String type;
    protected List<SubItem> subItems;
    protected List<GenerationFlags> flags;
    protected List<Tuple<Material, Integer>> components;
    public TextureSet textureSet;
    public Map<SubItem, Item> existingItems;
    protected ToolStats toolStats;
    protected FluidStats fluidStats;
    protected OreStats oreStats;
    protected Object cableStats;
    protected Object fluidPipeStats;
    protected Object itemPipeStats;
    protected boolean magnetic;

    public Material(String name, String type, int mass, int color, List<SubItem> subItems, List<GenerationFlags> flags, List<Tuple<Material, Integer>> components, ToolStats toolStats, FluidStats fluidStats, OreStats oreStats, TextureSet textureSet, boolean magnetic) {
        this.name = name;
        this.type = type;
        this.mass = mass;
        this.color = color;
        this.subItems = subItems;
        this.flags = flags;
        this.components = components;
        this.toolStats = toolStats;
        this.fluidStats = fluidStats;
        this.oreStats = oreStats;
        this.textureSet = textureSet;
        this.magnetic = magnetic;
    }

    public Material withExistingItems(Map<SubItem, Item> items) {
        this.existingItems = items;
        return this;
    }

    public FluidStats getFluidStats() {
        return fluidStats;
    }

    public ToolStats getToolStats() {
        return toolStats;
    }

    public List<GenerationFlags> getFlags() {
        return flags;
    }

    public List<SubItem> getSubItems() {
        return subItems;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<Tuple<Material, Integer>> getComponents() {
        return components;
    }

    public OreStats getOreStats() {
        return oreStats;
    }

    public boolean isMagnetic() {
        return magnetic;
    }

    @Override
    public int getColor(@Nonnull ItemStack stack, int layer) {
        SubItem subItem = SubItem.valueOf(stack.getItem().getRegistryName().getPath().replaceFirst(getName() + "_", "").toUpperCase());
        return layer == 0 ? (subItem.isLayer0Color() ? color : 0xFFFFFFFF) : (subItem.isLayer1Color() ? color : 0xFFFFFFFF);
    }
}
