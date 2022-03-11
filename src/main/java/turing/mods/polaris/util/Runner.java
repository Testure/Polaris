package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A {@link FunctionalInterface} that neither accepts nor returns.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@FunctionalInterface
public interface Runner extends Runnable {
    void run();
}
