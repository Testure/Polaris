package turing.mods.polaris.ui;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class TextureHelper {
    private final TextureManager manager;
    @Nullable
    private ResourceLocation currentTexture;

    public TextureHelper(TextureManager manager) {
        this.manager = manager;
    }

    @Nullable
    public ResourceLocation getCurrentTexture() {
        return currentTexture;
    }

    public ResourceLocation bindTexture(ResourceLocation texture) {
        if (texture.equals(getCurrentTexture())) return texture;
        manager.bindTexture(texture);
        currentTexture = texture;
        return texture;
    }
}
