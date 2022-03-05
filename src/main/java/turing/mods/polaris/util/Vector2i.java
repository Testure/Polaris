package turing.mods.polaris.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.math.vector.Vector2f;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Minecraft doesn't have a Vector2 for ints,
 * so here it is
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Vector2i {
    public static final Vector2i ZERO = new Vector2i(0, 0);
    public static final Vector2i ONE = new Vector2i(1, 1);
    public static final Vector2i MAX = new Vector2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
    public static final Vector2i MIN = new Vector2i(Integer.MIN_VALUE, Integer.MIN_VALUE);

    public int x;
    public int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2i fromFloat(Vector2f floatVector) {
        return new Vector2i((int) floatVector.x, (int) floatVector.y);
    }

    public Vector2i copy() {
        return new Vector2i(this.x, this.y);
    }

    public boolean equals(Vector2i other) {
        return this.x == other.x && this.y == other.y;
    }

    public Vector2f toFloat() {
        return new Vector2f((float) this.x, (float) this.y);
    }

    public Vector2i add(Vector2i other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2i add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2i sub(Vector2i other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2i sub(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2i mul(Vector2i other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public Vector2i mul(int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2i div(Vector2i other) {
        this.x /= other.x;
        this.y /= other.y;
        return this;
    }

    public Vector2i div(int x, int y) {
        this.x /= x;
        this.y /= y;
        return this;
    }
}
