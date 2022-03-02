package turing.mods.polaris.util;

import java.util.function.BiConsumer;

/**
 * Same as {@link BiConsumer} but with a good name
 * @param <T1> first value
 * @param <T2> second value
 */
@FunctionalInterface
public interface Consumer2<T1, T2> extends BiConsumer<T1, T2> {}
