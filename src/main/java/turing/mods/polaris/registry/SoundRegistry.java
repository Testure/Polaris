package turing.mods.polaris.registry;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import turing.mods.polaris.Polaris;

public class SoundRegistry {
    public static final RegistryObject<SoundEvent> SOFT_HAMMER = Registration.SOUNDS.register("soft_hammer", () -> new SoundEvent(Polaris.modLoc("tool.soft_hammer.sound")));
    public static final RegistryObject<SoundEvent> WRENCH = Registration.SOUNDS.register("wrench", () -> new SoundEvent(Polaris.modLoc("tool.wrench.sound")));
    public static final RegistryObject<SoundEvent> SCREWDRIVER = Registration.SOUNDS.register("screwdriver", () -> new SoundEvent(Polaris.modLoc("tool.screwdriver.sound")));
    public static final RegistryObject<SoundEvent> WIRE_CUTTER = Registration.SOUNDS.register("wire_cutter", () -> new SoundEvent(Polaris.modLoc("tool.wire_cutter.sound")));

    public static void register() {}
}
