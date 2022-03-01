package turing.mods.polaris.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.material.ComponentStack;

import javax.annotation.Nullable;

import static net.minecraft.util.text.TextFormatting.*;

import java.text.NumberFormat;
import java.util.*;

public class Formatting {
    public static final String[] SUBSCRIPT_NUMBERS = new String[]{ "subscript.0", "subscript.1", "subscript.2", "subscript.3", "subscript.4", "subscript.5", "subscript.6", "subscript.7", "subscript.8", "subscript.9" };

    public static List<ITextComponent> stringsToTextComponents(List<String> strings) {
        List<ITextComponent> list = new ArrayList<>();

        strings.forEach(string -> list.add(new StringTextComponent(string)));
        strings.removeIf(s -> true);

        return list;
    }

    public static List<ITextComponent> stringsToTranslatedComponents(List<String> strings) {
        List<ITextComponent> list = new ArrayList<>();

        strings.forEach(string -> list.add(new TranslationTextComponent(string)));
        strings.removeIf(s -> true);

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
                builder.append(SUBSCRIPT_NUMBERS[Integer.getInteger(toString.substring(i, i))]);
            }

            return builder.toString();
        }
    }

    public static Tuple<String, Map<Integer, TranslationTextComponent>> createChemicalFormula(ComponentStack... stacks) {
        if (stacks == null || stacks.length <= 0) return null;
        int characterCount = 1;
        for (ComponentStack stack : stacks) {
            characterCount += (stack.getComponent().getChemicalName().length() + stack.getCount());
            if (stack.getComponent().isCombination()) characterCount += 2;
        }

        StringBuilder builder = new StringBuilder(characterCount);
        Map<Integer, TranslationTextComponent> subscripts = new HashMap<>();

        for (ComponentStack stack : stacks) {
            String subscript = getSubscript(stack.getCount());
            if (stack.getComponent().isCombination()) builder.append("(");
            if (stack.getComponent().isCombination() && stack.getComponent().getMadeOf() != null && stack.getComponent().getMadeOf().length > 0) {
                Tuple<String, Map<Integer, TranslationTextComponent>> formula = createChemicalFormula(stack.getComponent().getMadeOf());
                int add = builder.length();
                builder.append(formula.getA());
                formula.getB().forEach((key, value) -> subscripts.put(key + add, value));
            } else builder.append(stack.getComponent().getChemicalName());
            if (!Objects.equals(subscript, "")) subscripts.put(builder.length(), new TranslationTextComponent(subscript));
            if (stack.getComponent().isCombination()) builder.append(")");
        }

        return new Tuple<>(builder.toString(), subscripts);
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
