package turing.mods.polaris.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

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
}
