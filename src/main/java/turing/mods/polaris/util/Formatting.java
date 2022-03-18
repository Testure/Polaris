package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.material.ComponentStack;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.util.text.TextFormatting.*;

import java.text.NumberFormat;
import java.util.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Formatting {
    public static final String[] SUBSCRIPT_NUMBERS = new String[]{ "subscript.0", "subscript.1", "subscript.2", "subscript.3", "subscript.4", "subscript.5", "subscript.6", "subscript.7", "subscript.8", "subscript.9" };

    public static List<ITextComponent> stringsToTextComponents(List<String> strings) {
        List<ITextComponent> list = new ArrayList<>();

        strings.forEach(string -> list.add(new StringTextComponent(string)));

        return list;
    }

    public static List<ITextComponent> stringsToTranslatedComponents(List<String> strings) {
        List<ITextComponent> list = new ArrayList<>();

        strings.forEach(string -> list.add(new TranslationTextComponent(string)));;

        return list;
    }

    public static List<ITextComponent> stringsToTranslatedComponents(Map<String, List<String>> map) {
        List<ITextComponent> list = new ArrayList<>();

        map.forEach((key, values) -> list.add(new TranslationTextComponent(key, values)));
        map.clear();

        return list;
    }

    public static String formattedNumber(long number) {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(true);
        return format.format(number);
    }

    public static String getSubscript(int number) {
        if (number < 2) return "";
        if (number < 10) return SUBSCRIPT_NUMBERS[number];
        else {
            String toString = Integer.toString(number);
            StringBuilder builder = new StringBuilder(toString.length());

            for (int i = 0; i < toString.length(); i++) {
                builder.append(SUBSCRIPT_NUMBERS[Integer.parseInt(toString.substring(i, i + 1))]);
            }

            return builder.toString();
        }
    }

    public static List<String> createChemicalFormula(@Nullable ComponentStack... stacks) {
        if (stacks == null || stacks.length <= 0) return null;

        List<String> strings = new ArrayList<>();

        for (ComponentStack stack : stacks) {
            StringBuilder builder = new StringBuilder();
            String subscript = getSubscript(stack.getCount());
            if (stack.getComponent().isCombination() && stacks.length > 1) builder.append("(");
            if (stack.getComponent().isCombination() && stack.getComponent().getMadeOf() != null) {
                for (ComponentStack madeOf : stack.getComponent().getMadeOf()) {
                    if (madeOf.getComponent().isCombination() && madeOf.getComponent().getMadeOf() != null){
                        for (String str : createChemicalFormula()) builder.append(str);
                        builder.append(getSubscript(madeOf.getCount()));
                    } else {
                        builder.append(madeOf.getComponent().getChemicalName());
                        builder.append(getSubscript(madeOf.getCount()));
                    }
                }
            } else builder.append(stack.getComponent().getChemicalName());
            if (stack.getComponent().isCombination() && stacks.length > 1) builder.append(")");
            strings.add(builder.append(subscript).toString());
        }

        return strings;
    }

    /**
     * @param key
     * Translation key to use in TranslationTextComponent
     * @param voltageTier
     * Voltage tier to use in tooltip
     * @param color
     * Color to format voltage amount in, defaults to green if null
     * @return TranslationTextComponent of the given key with the first value as the voltage amount formatted with the given color, and the second value as the voltage name
     */
    public static TranslationTextComponent createVoltageTooltip(String key, int voltageTier, @Nullable TextFormatting color) {
        Voltages.Voltage voltage = Voltages.VOLTAGES[voltageTier];
        return new TranslationTextComponent(key, (color != null ? color : GREEN) + formattedNumber(voltage.energy), I18n.format(Voltages.getVoltageTierTranslationKey(voltageTier)));
    }
}
