package turing.mods.polaris.registry;

import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.world.RubberTreeFeature;
import turing.mods.polaris.world.VeinFeature;

public class FeatureRegistry {
    public static final RegistryObject<VeinFeature> VEIN_FEATURE = Registration.FEATURES.register("vein", VeinFeature::new);
    public static final RegistryObject<TreeFeature> RUBBER = Registration.FEATURES.register("rubber", RubberTreeFeature::new);

    public static void register() {

    }
}
