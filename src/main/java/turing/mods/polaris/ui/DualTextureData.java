package turing.mods.polaris.ui;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class DualTextureData extends TextureData {
    private final Vector2i firstSize;
    private final Vector2i firstPos;
    private final Vector2i secondSize;
    private final Vector2i secondPos;

    public DualTextureData(ResourceLocation txt, Vector2i size, Vector2i firstSize, Vector2i firstPos, Vector2i secondSize, Vector2i secondPos) {
        super(txt, size);
        this.firstPos = firstPos;
        this.firstSize = firstSize;
        this.secondPos = secondPos;
        this.secondSize = secondSize;
    }

    public Vector2i getFirstSize() {
        return firstSize;
    }

    public Vector2i getSecondSize() {
        return secondSize;
    }

    public Vector2i getFirstPos() {
        return firstPos;
    }

    public Vector2i getSecondPos() {
        return secondPos;
    }
}
