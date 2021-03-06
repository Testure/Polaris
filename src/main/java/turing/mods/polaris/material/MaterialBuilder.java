package turing.mods.polaris.material;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.item.Item;
import net.minecraft.util.Tuple;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;
import turing.mods.polaris.util.Lists;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MaterialBuilder {
    protected int mass = 20;
    public int color = 0xFFFFFFFF;
    protected String name;
    protected String type;
    protected List<GenerationFlags> flags = new ArrayList<>();
    public List<SubItem> subItems = new ArrayList<>();
    public ToolStats toolStats;
    public FluidStats fluidStats;
    public OreStats oreStats;
    public TextureSet textureSet = TextureSet.METAL;
    protected ComponentStack[] components;
    public Map<SubItem, Item> existingItems;
    public Object cableStats;
    public Object fluidPipeStats;
    public Object itemPipeStats;
    protected MaterialRegistryObject magnetic;

    private static int getDefaultSmeltAmount(@Nullable String type) {
        switch (type != null ? type : "") {
            case "dust":
                return 2;
            case "gem":
                return 6;
            case "ingot":
            default:
                return 1;
        }
    }

    private void processFlag(GenerationFlags flag) {
        switch (flag) {
            case GENERATE_ROD:
                this.subItems.add(SubItem.ROD);
                break;
            case GENERATE_SCREW:
                this.subItems.add(SubItem.BOLT);
                this.subItems.add(SubItem.SCREW);
                break;
            case GENERATE_GEAR:
                this.subItems.add(SubItem.GEAR);
                break;
            case GENERATE_SMALL_GEAR:
                this.subItems.add(SubItem.SMALL_GEAR);
                break;
            case GENERATE_SPRING:
                this.subItems.add(SubItem.SPRING);
                this.subItems.add(SubItem.SMALL_SPRING);
                break;
            case GENERATE_PLATE:
                this.subItems.add(SubItem.PLATE);
                break;
            case GENERATE_LENS:
                this.subItems.add(SubItem.LENS);
                break;
            case NO_COMPRESSION:
                this.subItems.remove(SubItem.BLOCK);
                this.subItems.remove(SubItem.SMALL_DUST);
                this.subItems.remove(SubItem.TINY_DUST);
                this.subItems.remove(SubItem.NUGGET);
                break;
            case IS_SOFT:
                this.removeAllTools(Arrays.asList(
                        SubItem.MORTAR,
                        SubItem.HAMMER,
                        SubItem.FILE,
                        SubItem.WRENCH,
                        SubItem.SCREWDRIVER,
                        SubItem.CROWBAR,
                        SubItem.SAW,
                        SubItem.WIRE_CUTTER
                ));
                if (!this.subItems.contains(SubItem.SOFT_HAMMER)) this.addTool(SubItem.SOFT_HAMMER);
                break;
            case NO_MORTAR:
                this.removeTool(SubItem.MORTAR);
                break;
            case NO_HAMMER:
                this.removeTool(SubItem.HAMMER);
                this.removeTool(SubItem.SOFT_HAMMER);
                break;
            case NO_VANILLA_TOOLS:
                this.removeAllTools(Arrays.asList(
                        SubItem.SWORD,
                        SubItem.AXE,
                        SubItem.PICKAXE,
                        SubItem.SHOVEL,
                        SubItem.HOE
                ));
                break;
            case NO_BLOCK:
                this.subItems.remove(SubItem.BLOCK);
                break;
            case GENERATE_FOIL:
                this.subItems.add(SubItem.FOIL);
                break;
            case NO_PLATE:
                this.subItems.remove(SubItem.PLATE);
                break;
            case NO_ROD:
                this.subItems.remove(SubItem.ROD);
                break;
            case NO_CROWBAR:
                this.removeTool(SubItem.CROWBAR);
                break;
            case NO_WRENCH:
                this.removeTool(SubItem.WRENCH);
                break;
            case NO_FILE:
                this.removeTool(SubItem.FILE);
                break;
            case NO_SCREWDRIVER:
                this.removeTool(SubItem.SCREWDRIVER);
                break;
            case NO_SAW:
                this.removeTool(SubItem.SAW);
                break;
            case NO_WIRE_CUTTER:
                this.removeTool(SubItem.WIRE_CUTTER);
                break;
            case ONLY_MORTAR:
                this.removeAllTools(Arrays.asList(
                        SubItem.SOFT_HAMMER,
                        SubItem.HAMMER,
                        SubItem.FILE,
                        SubItem.WRENCH,
                        SubItem.SCREWDRIVER,
                        SubItem.CROWBAR,
                        SubItem.SAW,
                        SubItem.WIRE_CUTTER
                ));
                if (!this.subItems.contains(SubItem.MORTAR)) this.addTool(SubItem.MORTAR);
                break;
            default:
                break;
        }
    }

    protected void addTool(SubItem tool) {
        this.subItems.add(tool);
        if (tool.hasHead()) this.subItems.add(tool.getHead());
    }

    protected void addAllTools(List<SubItem> tools) {
        this.subItems.addAll(tools);
        for (SubItem tool : tools) if (tool.hasHead()) this.subItems.add(tool.getHead());
    }

    protected void removeTool(SubItem tool) {
        this.subItems.remove(tool);
        if (tool.hasHead()) this.subItems.remove(tool.getHead());
    }

    protected void removeAllTools(List<SubItem> tools) {
        this.subItems.removeAll(tools);
        for (SubItem tool : tools) if (tool.hasHead()) this.subItems.remove(tool.getHead());
    }

    protected MaterialBuilder(String name) {
        this.name = name;
    }

    public static MaterialBuilder builder(String name) {
        return new MaterialBuilder(name);
    }

    public MaterialBuilder ingot() {
        this.type = "ingot";
        this.subItems.addAll(Arrays.asList(
                SubItem.INGOT,
                SubItem.DUST,
                SubItem.SMALL_DUST,
                SubItem.TINY_DUST,
                SubItem.PLATE,
                SubItem.ROD,
                SubItem.NUGGET,
                SubItem.BLOCK
        ));
        return this;
    }

    public MaterialBuilder soft() {
        this.type = "soft";
        this.subItems.addAll(Arrays.asList(
                SubItem.INGOT,
                SubItem.DUST,
                SubItem.SMALL_DUST,
                SubItem.TINY_DUST,
                SubItem.PLATE,
                SubItem.BLOCK
        ));
        return this;
    }

    @SafeVarargs
    public final MaterialBuilder withExistingItems(Tuple<SubItem, Item>... items) {
        this.existingItems = new HashMap<>();
        Arrays.stream(items).forEach(item -> this.existingItems.put(item.getA(), item.getB()));
        return this;
    }

    public MaterialBuilder magnetic(MaterialRegistryObject magneticOf) {
        this.magnetic = magneticOf;
        return this;
    }

    public MaterialBuilder dust() {
        this.type = "dust";
        this.subItems.addAll(Arrays.asList(
                SubItem.DUST,
                SubItem.SMALL_DUST,
                SubItem.TINY_DUST,
                SubItem.BLOCK
        ));
        return this;
    }

    public MaterialBuilder gem() {
        this.type = "gem";
        this.subItems.addAll(Arrays.asList(
                SubItem.DUST,
                SubItem.SMALL_DUST,
                SubItem.TINY_DUST,
                SubItem.BLOCK,
                SubItem.GEM,
                SubItem.PLATE,
                SubItem.ROD
        ));
        return this;
    }

    public MaterialBuilder ore(@Nullable Integer smeltAmount) {
        return ore(smeltAmount, null, null);
    }

    public MaterialBuilder ore(@Nullable Integer smeltAmount, @Nullable SubItem smeltItem) {
        return ore(smeltAmount, smeltItem, null);
    }

    public MaterialBuilder ore(@Nullable Integer smeltAmount, @Nullable SubItem smeltItem, @Nullable MaterialRegistryObject smeltMaterial) {
        this.subItems.add(SubItem.ORE);
        this.oreStats = OreStats.Builder.builder()
                .setSmeltAmount(smeltAmount != null ? smeltAmount : getDefaultSmeltAmount(this.type))
                .setCustomMaterial(smeltMaterial)
                .setCustomSubItem(smeltItem)
                .build();
        return this;
    }

    public MaterialBuilder toolStats(int durability, int enchantability, float miningSpeed, float attackDamage, float attackSpeed, EnchantmentData... enchantments) {
        this.toolStats = ToolStats.Builder.builder()
                .durability(durability)
                .enchantability(enchantability)
                .miningSpeed(miningSpeed)
                .attackDamage(attackDamage)
                .attackSpeed(attackSpeed)
                .withDefaultEnchantments(enchantments)
                .build();
        this.addAllTools(Arrays.asList(
                SubItem.SWORD,
                SubItem.PICKAXE,
                SubItem.SHOVEL,
                SubItem.AXE,
                SubItem.HOE,
                SubItem.HAMMER,
                SubItem.MORTAR,
                SubItem.SAW,
                SubItem.SCREWDRIVER,
                SubItem.FILE
        ));
        if (Objects.equals(this.type, "ingot")) this.addAllTools(Arrays.asList(SubItem.WRENCH, SubItem.CROWBAR, SubItem.WIRE_CUTTER));
        return this;
    }

    public MaterialBuilder fluid(boolean gaseous, int temp) {
        this.fluidStats = FluidStats.Builder.builder()
                .isGaseous(gaseous)
                .temperature(temp)
                .build();
        return this;
    }

    public MaterialBuilder textureSet(TextureSet set) {
        this.textureSet = set;
        return this;
    }

    public MaterialBuilder component(Component component) {
        this.components = new ComponentStack[]{new ComponentStack(component, 1)};
        return this;
    }

    public MaterialBuilder components(ComponentStack... stacks) {
        this.components = stacks;
        return this;
    }

    public MaterialBuilder components(Component... components) {
        this.components = Arrays.stream(components).map(ComponentStack::new).toArray(ComponentStack[]::new);
        return this;
    }

    public MaterialBuilder addFlags(GenerationFlags... flags) {
        Arrays.stream(flags).forEach(flag -> {if (!this.flags.contains(flag)) this.flags.add(flag);});
        return this;
    }

    public MaterialBuilder color(int color) {
        this.color = color;
        return this;
    }

    public MaterialBuilder mass(int mass) {
        this.mass = mass;
        return this;
    }

    public MaterialRegistryObject buildAndRegister() {
        return MaterialRegistry.register(this.name, this);
    }

    public Material build() {
        for (GenerationFlags flag : this.flags) processFlag(flag);
        Material material = new Material(this.name, this.type, this.mass, this.color, this.subItems, this.flags, this.components, this.toolStats, this.fluidStats, this.oreStats, this.textureSet, this.magnetic);
        return (existingItems != null && !existingItems.isEmpty()) ? material.withExistingItems(existingItems) : material;
    }
}
