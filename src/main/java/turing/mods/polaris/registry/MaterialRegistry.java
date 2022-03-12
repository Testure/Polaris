package turing.mods.polaris.registry;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Tuple;
import net.minecraftforge.eventbus.api.IEventBus;
import turing.mods.polaris.material.*;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MaterialRegistry {
    private static final MaterialDeferredRegister MATERIAL_DEFERRED_REGISTER = new MaterialDeferredRegister();

    public static final Tuple<SubItem, Item>[] IRON_EXISTING = new Tuple[]{
            new Tuple<>(SubItem.INGOT, Items.IRON_INGOT),
            new Tuple<>(SubItem.BLOCK, Items.IRON_BLOCK),
            new Tuple<>(SubItem.AXE, Items.IRON_AXE),
            new Tuple<>(SubItem.NUGGET, Items.IRON_NUGGET),
            new Tuple<>(SubItem.HOE, Items.IRON_HOE),
            new Tuple<>(SubItem.PICKAXE, Items.IRON_PICKAXE),
            new Tuple<>(SubItem.SHOVEL, Items.IRON_SHOVEL),
            new Tuple<>(SubItem.SWORD, Items.IRON_SWORD)
    };

    public static final Tuple<SubItem, Item>[] GOLD_EXISTING = new Tuple[]{
            new Tuple<>(SubItem.INGOT, Items.GOLD_INGOT),
            new Tuple<>(SubItem.BLOCK, Items.GOLD_BLOCK),
            new Tuple<>(SubItem.AXE, Items.GOLDEN_AXE),
            new Tuple<>(SubItem.NUGGET, Items.GOLD_NUGGET),
            new Tuple<>(SubItem.HOE, Items.GOLDEN_HOE),
            new Tuple<>(SubItem.PICKAXE, Items.GOLDEN_PICKAXE),
            new Tuple<>(SubItem.SHOVEL, Items.GOLDEN_SHOVEL),
            new Tuple<>(SubItem.SWORD, Items.GOLDEN_SWORD)
    };

    public static MaterialRegistryObject register(String name, MaterialBuilder builder) {
        return MATERIAL_DEFERRED_REGISTER.register(name, builder);
    }

    public static void register(IEventBus bus) {
        Materials.register();
        MATERIAL_DEFERRED_REGISTER.register(bus);
    }

    public static Map<String, MaterialRegistryObject> getMaterials() {
        return MATERIAL_DEFERRED_REGISTER.getMaterials();
    }
}
