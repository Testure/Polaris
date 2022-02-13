package turing.mods.polaris.registry;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.item.BucketItem;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class BasicFluidRegistryObject extends FluidRegistryObject<ForgeFlowingFluid.Source, ForgeFlowingFluid.Flowing, FlowingFluidBlock, BucketItem> {
    public BasicFluidRegistryObject(String name) {
        super(name);
    }
}
