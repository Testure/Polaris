package turing.mods.polaris.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.ui.ModularUI;
import turing.mods.polaris.ui.ModularUIBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class ModularUIs {
    public static final ModularUI[] UIS;
    private static List<ModularUI> list = new ArrayList<>();
    private static final Consumer<ModularUI> adder = (ui) -> list.add(ui);

    public static final ModularUI COMPRESSOR_UI = ModularUIBuilder.builder()
            .itemSlot(51, 26)
            .itemSlot(101, 26)
            .build(adder);

    static {
        UIS = list.toArray(new ModularUI[0]);
        list.removeIf((a) -> true);
        list = null;
    }
}
