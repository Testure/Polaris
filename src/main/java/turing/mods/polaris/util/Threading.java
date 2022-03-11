package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Threading {
    /**
     * Runs a parallel thread on either an existing or new thread pool that runs the given function with the given input
     * and accepts the result into the given consumer.
     * @param pool an existing thread pool to run the thread on. set to null to create a new pool instead.
     * @param resultHandler consumer that accepts the function's result.
     * @param func function to run.
     * @param input value to pass to the function.
     */
    public static <I, R> void parallelGetter(@Nullable ExecutorService pool, Consumer<R> resultHandler, Function<I, R> func, @Nullable I input) {
        if (pool == null) pool = Executors.newFixedThreadPool(1);
        pool.submit(() -> resultHandler.accept(func.apply(input)));
    }

    /**
     * parallelGetter with 2 function arguments
     * @see Threading#parallelGetter(ExecutorService, Consumer, Function, Object)
     */
    public static <I1, I2, R> void parallelGetter(@Nullable ExecutorService pool, Consumer<R> resultHandler, Function2<I1, I2, R> func, @Nullable I1 input1, @Nullable I2 input2) {
        if (pool == null) pool = Executors.newFixedThreadPool(1);
        pool.submit(() -> resultHandler.accept(func.apply(input1, input2)));
    }
}
