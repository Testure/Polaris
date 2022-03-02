package turing.mods.polaris.util;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Consumer that accepts 3 inputs
 * @param <T1> first value
 * @param <T2> second value
 * @param <T3> third value
 */
@FunctionalInterface
public interface Consumer3<T1, T2, T3> {
    void accept(T1 value1, T2 value2, T3 value3);

    default Consumer3<T1, T2, T3> andThen(@Nonnull Consumer3<? super T1, ? super T2, ? super T3> after) {
        Objects.requireNonNull(after);
        return (T1 value1, T2 value2, T3 value3) -> { accept(value1, value2, value3); after.accept(value1, value2, value3); };
    }
}
