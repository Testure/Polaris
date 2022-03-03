package turing.mods.polaris.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public interface IRenderTypedBlock {
    default RenderType getRenderType() {
        return RenderType.getSolid();
    }
}
