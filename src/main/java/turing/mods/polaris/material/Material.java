package turing.mods.polaris.material;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import turing.mods.polaris.item.ITintedItem;
import turing.mods.polaris.registry.MaterialRegistryObject;
import turing.mods.polaris.util.Formatting;

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
    protected ComponentStack[] components;
    public TextureSet textureSet;
    public Map<SubItem, Item> existingItems;
    protected ToolStats toolStats;
    protected FluidStats fluidStats;
    protected OreStats oreStats;
    protected Object cableStats;
    protected Object fluidPipeStats;
    protected Object itemPipeStats;
    protected MaterialRegistryObject magnetic;
    protected ITextComponent formulaTooltip;
    protected ITextComponent[] fluidTooltips;

    public Material(String name, String type, int mass, int color, List<SubItem> subItems, List<GenerationFlags> flags, ComponentStack[] components, ToolStats toolStats, FluidStats fluidStats, OreStats oreStats, TextureSet textureSet, MaterialRegistryObject magnetic) {
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
        createFormulaTooltip();
        if (fluidStats != null) createFluidTooltip();
    }

    private void createFluidTooltip() {
        this.fluidTooltips = new ITextComponent[3];
        this.fluidTooltips[0] = formulaTooltip;
        this.fluidTooltips[1] = new TranslationTextComponent("tooltip.polaris.fluid_state." + (this.fluidStats.isGaseous() ? "gas" : "liquid"));
        this.fluidTooltips[2] = new TranslationTextComponent("tooltip.polaris.fluid_temp", TextFormatting.RED + Formatting.formattedNumber(this.fluidStats.temp));
    }

    private void createFormulaTooltip() {
        Tuple<String, Map<Integer, TranslationTextComponent>> formula = Formatting.createChemicalFormula(this.components);
        if (formula != null) {
            StringTextComponent tooltip = new StringTextComponent("");
            int finalI = 0;
            for (int i = 0; i < formula.getA().length() + formula.getB().size(); i++) {
                if (formula.getB().get(i) != null) {
                    tooltip.append(formula.getB().get(i));
                    finalI -= 1;
                } else if (formula.getA().length() >= finalI) tooltip.appendString(TextFormatting.YELLOW + String.valueOf(formula.getA().charAt(finalI)));
                finalI += 1;
            }
            this.formulaTooltip = tooltip;
        } else this.formulaTooltip = new StringTextComponent(TextFormatting.YELLOW + "Oops! something went wrong creating this tooltip!");
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

    public ComponentStack[] getComponents() {
        return components;
    }

    public OreStats getOreStats() {
        return oreStats;
    }

    public MaterialRegistryObject getMagneticOf() {
        return magnetic;
    }

    public ITextComponent getFormulaTooltip() {
        return formulaTooltip;
    }

    public ITextComponent[] getFluidTooltips() {
        return fluidTooltips;
    }

    @Override
    public int getColor(@Nonnull ItemStack stack, int layer) {
        SubItem subItem = SubItem.valueOf(stack.getItem().getRegistryName().getPath().replaceFirst(getName() + "_", "").toUpperCase());
        return layer == 0 ? (subItem.isLayer0Color() ? color : 0xFFFFFFFF) : (subItem.isLayer1Color() ? color : 0xFFFFFFFF);
    }
}
