package turing.mods.polaris.util;

import net.minecraft.item.ItemStack;
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

    public static <T> boolean compareLists(List<T> list1, List<T> list2) {
        if (list1 == null || list2 == null) return false;
        return list1.containsAll(list2);
    }

    public static boolean chancedOutputsEqual(List<Tuple<ItemStack, Integer>> list1, List<ItemStack> list2) {
        if (list1 == null || list2 == null) return false;
        for (Tuple<ItemStack, Integer> tuple : list1) {
            if (!list2.contains(tuple.getA())) return false;
        }
        return true;
    }
}
