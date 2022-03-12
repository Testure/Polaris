package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;
import turing.mods.polaris.Polaris;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * A {@link FutureTask<T>} that supports event-driven handling of it's result
 * @param <T> The result of this future
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EventFuture<T> extends FutureTask<T> {
    protected List<Consumer<T>> eventFunctions = new ArrayList<>();

    public EventFuture(Callable<T> callable) {
        super(callable);
    }

    public EventFuture(Runnable runnable, T result) {
        super(runnable, result);
    }

    @Override
    protected void set(T t) {
        super.set(t);
    }

    @Override
    protected void done() {
        if (isCancelled()) return;
        try {
            fire(get());
        } catch (InterruptedException | ExecutionException e) {
            Polaris.LOGGER.fatal(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Connects a {@link Consumer<T>} to this future if it hasn't completed yet
     * @param func The consumer to be fired upon completion of this future
     */
    public void connect(Consumer<T> func) {
        if (isDone()) return;
        eventFunctions.add(func);
    }

    /**
     * Calls all connected {@link Consumer<T>}s and removes all connections if this future hasn't completed yet
     */
    protected void fire(T val) {
        if (isCancelled()) return;
        if (!isDone()) return;
        eventFunctions.forEach(func -> func.accept(val));
        eventFunctions.clear();
        eventFunctions = null;
    }
}
