package turing.mods.polaris.util;

public class MathUtil {
    public static boolean isWithinBounds(int i, int min, int max, boolean exclusiveMin, boolean exclusiveMax) {
        return (exclusiveMin ? i > min : i >= min) && (exclusiveMax ? i < max : i <= max);
    }

    public static boolean isWithinBounds(int i, int min, int max, boolean exclusive) {
        return isWithinBounds(i, min, max, exclusive, exclusive);
    }

    public static boolean isWithinBounds(int i, int min, int max) {
        return isWithinBounds(i, min, max, false);
    }

    public static boolean isWithinBoundsF(float f, float min, float max) {
        return f >= min && f <= max;
    }
}
