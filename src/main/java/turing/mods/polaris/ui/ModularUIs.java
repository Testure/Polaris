package turing.mods.polaris.ui;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.util.Vector2i;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ModularUIs {
    public static final DualTextureData FUEL_PROGRESS = new DualTextureData(Polaris.modLoc("textures/gui/modular/progress/fuel.png"), new Vector2i(13,  28), new Vector2i(13, 13), Vector2i.ZERO, new Vector2i(13, 14), new Vector2i(0, 15));
    public static final DualTextureData ARROW_PROGRESS = new DualTextureData(Polaris.modLoc("textures/gui/modular/progress/arrow.png"), new Vector2i(23, 34), new Vector2i(23, 15), Vector2i.ZERO, new Vector2i(23, 16), new Vector2i(0, 18));
    public static final DualTextureData COMPRESSOR_PROGRESS = new DualTextureData(Polaris.modLoc("textures/gui/modular/progress/compressor.png"), new Vector2i(19, 20), new Vector2i(19, 14), Vector2i.ZERO, new Vector2i(19, 3), new Vector2i(0, 18));
    public static final DualTextureData MACERATOR_PROGRESS = new DualTextureData(Polaris.modLoc("textures/gui/modular/progress/macerator.png"), new Vector2i(14, 26), new Vector2i(14, 12), Vector2i.ZERO, new Vector2i(14, 12), new Vector2i(0, 14));

    public static final ModularUI[] UIS;
    private static List<ModularUI> list = new ArrayList<>();
    private static final Consumer<ModularUI> adder = (ui) -> list.add(ui);

    public static final ModularUI COMPRESSOR_UI = ModularUIBuilder.builder()
            .itemSlot(51, 26)
            .itemSlot(101, 26)
            .addTitle("screen.polaris.compressor")
            .addProgressBar(COMPRESSOR_PROGRESS, new Vector2i(76, 25), new Vector2i(76, 34))
            .build(adder);

    static {
        UIS = list.toArray(new ModularUI[0]);
        list.removeIf((a) -> true);
        list = null;
    }
}
