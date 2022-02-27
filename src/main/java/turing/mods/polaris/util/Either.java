package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.util.NonNullConsumer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Either<A, B> {
    protected A a;
    protected B b;

    public Either(@Nullable A a, @Nullable B b) {
        this.a = b == null ? a : null;
        this.b = a == null ? b : null;
    }

    public void setA(@Nullable A a) {
        this.a = a;
        this.b = null;
    }

    public void setB(@Nullable B b) {
        this.b = b;
        this.a = null;
    }

    @Nullable
    public A getA() {
        return a;
    }

    @Nullable
    public B getB() {
        return b;
    }

    public boolean isA() {
        return getA() != null;
    }

    public boolean isB() {
        return getB() != null;
    }

    public void ifA(NonNullConsumer<A> consumer) {
        if (isA()) consumer.accept(Objects.requireNonNull(getA()));
    }

    public void ifB(NonNullConsumer<B> consumer) {
        if (isB()) consumer.accept(Objects.requireNonNull(getB()));
    }
}
