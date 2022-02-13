package turing.mods.polaris.block;

import net.minecraft.client.renderer.RenderType;

public interface IRenderTypedBlock {
    default RenderType getRenderType() {
        return RenderType.solid();
    }
}
