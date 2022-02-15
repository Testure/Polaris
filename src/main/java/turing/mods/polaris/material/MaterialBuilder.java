package turing.mods.polaris.material;

import net.minecraft.item.Item;
import net.minecraft.util.Tuple;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    protected List<Tuple<Material, Integer>> components;
    public List<Item> existingItems;
    public Object cableStats;
    public Object fluidPipeStats;
    public Object itemPipeStats;

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
            default:
                break;
        }
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

    public MaterialBuilder withExistingItems(Item... items) {
        this.existingItems = Arrays.asList(items);
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
        this.subItems.add(SubItem.ORE);
        this.oreStats = OreStats.Builder.builder()
                .setSmeltAmount(smeltAmount != null ? smeltAmount : getDefaultSmeltAmount(this.type))
                .build();
        return this;
    }

    public MaterialBuilder toolStats(int durability, float enchantability, float miningSpeed, float attackDamage) {
        this.toolStats = ToolStats.Builder.builder()
                .durability(durability)
                .enchantability(enchantability)
                .miningSpeed(miningSpeed)
                .attackDamage(attackDamage)
                .build();
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

    public MaterialBuilder components(List<Material> materials, List<Integer> amounts) {
        for (int i = 0; i < materials.size(); i++) {
            Tuple<Material, Integer> tuple = new Tuple<>(materials.get(i), amounts.get(i));
            this.components.add(tuple);
        }
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
        return new Material(this.name, this.type, this.mass, this.color, this.subItems, this.flags, this.components, this.toolStats, this.fluidStats, this.oreStats, this.textureSet);
    }
}
