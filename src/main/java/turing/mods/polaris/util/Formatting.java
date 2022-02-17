package turing.mods.polaris.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import turing.mods.polaris.Polaris;

import javax.annotation.Nullable;

import static net.minecraft.util.text.TextFormatting.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Formatting {
    public static List<ITextComponent> stringsToTextComponents(List<String> strings) {
        List<ITextComponent> list = new ArrayList<>();

        strings.forEach(string -> {
            list.add(new StringTextComponent(string));
        });
        strings.removeIf(s -> true);

        return list;
    }

    public static List<ITextComponent> stringsToTranslatedComponents(List<String> strings) {
        List<ITextComponent> list = new ArrayList<>();

        strings.forEach(string -> {
            list.add(new TranslationTextComponent(string));
        });
        strings.removeIf(s -> true);

        return list;
    }

    public static List<ITextComponent> stringsToTranslatedComponents(Map<String, List<String>> map) {
        List<ITextComponent> list = new ArrayList<>();

        map.forEach((key, values) -> {
            list.add(new TranslationTextComponent(key, values));
        });
        map.clear();

        return list;
    }

    public static String formattedNumber(long number) {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(true);
        return format.format(number);
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
        Tuple<Integer, String> voltage = Polaris.VOLTAGES.VOLTAGE_LIST.get(voltageTier);
        return new TranslationTextComponent(key, (color != null ? color : GREEN) + formattedNumber(voltage.getA()), I18n.get(Polaris.VOLTAGES.getVoltageTierTranslationKey(voltageTier)));
    }
}
