package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Lists {
    @SafeVarargs
    public static <T> List<T> listOf(T... objects) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, objects);
        return list;
    }

    public static <S, T> List<T> mapInto(Function<S, T> map, List<S> list) {
        List<T> newList = new ArrayList<>();
        list.forEach(s -> newList.add(map.apply(s)));
        return newList;
    }

    @SafeVarargs
    public static <S, T> T[] mapInto(Function<S, T> map, S... list) {
        Object[] newArray = new Object[list.length];
        for (int i = 0; i < list.length; i++) newArray[i] = map.apply(list[i]);
        return (T[]) newArray;
    }

    public static <T> T[] copyTo(T[] array, T[] array2, int start) {
        for (int i = start; i < array2.length + start; i++) array[i] = array2[i - start];
        return array;
    }

    @SafeVarargs
    public static <T> T[] combine(T[]... arrays) {
        int totalSize = 1;
        for (T[] array : arrays) totalSize += array.length;
        T[] newArray = (T[]) new Object[totalSize];
        for (int i = 0; i < arrays.length; i++) copyTo(newArray, arrays[i], i + arrays[i].length);
        return newArray;
    }

    @SafeVarargs
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
