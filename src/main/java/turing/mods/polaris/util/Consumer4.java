package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Consumer that accepts 4 inputs
 * @param <T1> first value
 * @param <T2> second value
 * @param <T3> third value
 * @param <T4> fourth value
 */
@FunctionalInterface
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface Consumer4<T1, T2, T3, T4> {
    void accept(T1 value1, T2 value2, T3 value3, T4 value4);

    default Consumer4<T1, T2, T3, T4> andThen(Consumer4<? super T1, ? super T2, ? super T3, ? super T4> after) {
        Objects.requireNonNull(after);
        return (T1 value1, T2 value2, T3 value3, T4 value4) -> { accept(value1, value2, value3, value4); after.accept(value1, value2, value3, value4); };
    }
}
