package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ThreadPool extends ThreadPoolExecutor {
    public static ThreadPool newPoolOfSize(int amount) {
        return new ThreadPool(amount, amount, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    public ThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public <T> EventFuture<T> submit(Callable<T> callable) {
        if (callable == null) throw new NullPointerException();
        EventFuture<T> task = taskFor(callable);
        execute(task);
        return task;
    }

    protected <T> EventFuture<T> taskFor(Callable<T> callable) {
        return new EventFuture<>(callable);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new EventFuture<>(callable);
    }

    @Override
    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new EventFuture<>(runnable, value);
    }
}
