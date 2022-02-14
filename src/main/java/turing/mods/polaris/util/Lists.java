package turing.mods.polaris.util;

import net.minecraft.util.Tuple;

import java.util.*;

public class Lists {
    public static <T> List<T> listOf(T... objects) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, objects);
        return list;
    }

    public static <K, V> Map<K, V> mapOf(Tuple<K, V>... values) {
        Map<K, V> map = new HashMap<>();
        Arrays.stream(values).forEach(tuple -> map.put(tuple.getA(), tuple.getB()));
        return map;
    }
}
