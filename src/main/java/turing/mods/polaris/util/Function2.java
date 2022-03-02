package turing.mods.polaris.util;

import java.util.function.BiFunction;

/**
 * Same as {@link BiFunction} but with a good name
 * @param <T1> first argument
 * @param <T2> second argument
 * @param <R> result
 */
@FunctionalInterface
public interface Function2<T1, T2, R> extends BiFunction<T1, T2, R> {}
