package turing.mods.polaris.registry;

import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.world.VeinFeature;

public class FeatureRegistry {
    public static final RegistryObject<VeinFeature> VEIN_FEATURE = Registration.FEATURES.register("vein", VeinFeature::new);

    public static void register() {

    }
}
