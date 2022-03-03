package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import turing.mods.polaris.material.Material;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RenderUtil {
    public static void addFluidTooltips(Material material, List<ITextComponent> tooltips, boolean fluidStats) {
        if (material.getFluidTooltips() == null) return;
        if (!fluidStats) tooltips.addAll(Arrays.asList(material.getFluidTooltips()));
        else {
            tooltips.add(material.getFluidTooltips()[0]);
            tooltips.add(new TranslationTextComponent("tooltip.polaris.fluid_stats"));
            for (int i = 1; i < material.getFluidTooltips().length; i++) tooltips.add(new StringTextComponent(" ").append(material.getFluidTooltips()[i]));
        }
    }
}
