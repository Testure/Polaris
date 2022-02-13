package turing.mods.polaris.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lists {
    public static <T> List<T> listOf(T... objects) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, objects);
        return list;
    }
}
