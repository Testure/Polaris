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
public class TextureData {
    private final ResourceLocation txt;
    private final Vector2i size;

    public TextureData(ResourceLocation txt, Vector2i size) {
        this.txt = txt;
        this.size = size;
    }

    public ResourceLocation getTxt() {
        return txt;
    }

    public Vector2i getSize() {
        return size;
    }
}
