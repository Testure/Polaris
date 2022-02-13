package turing.mods.polaris.registry;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import turing.mods.polaris.Polaris;

public class FluidRegistryObject<STILL extends Fluid, FLOWING extends Fluid, BLOCK extends FlowingFluidBlock, BUCKET extends BucketItem> {
    private RegistryObject<STILL> stillRegistryObject;
    private RegistryObject<FLOWING> flowingRegistryObject;
    private RegistryObject<BLOCK> blockRegistryObject;
    private RegistryObject<BUCKET> bucketRegistryObject;
    private String name;

    public FluidRegistryObject(String name) {
        this.name = name;
        this.stillRegistryObject = RegistryObject.of(Polaris.modLoc(name), ForgeRegistries.FLUIDS);
        this.flowingRegistryObject = RegistryObject.of(Polaris.modLoc(name), ForgeRegistries.FLUIDS);
        this.blockRegistryObject = RegistryObject.of(Polaris.modLoc(name), ForgeRegistries.BLOCKS);
        this.bucketRegistryObject = RegistryObject.of(Polaris.modLoc(name + "_bucket"), ForgeRegistries.ITEMS);
    }

    public String getName() {
        return name;
    }

    public BLOCK getBlock() {
        return blockRegistryObject.get();
    }

    public BUCKET getBucket() {
        return bucketRegistryObject.get();
    }

    public FLOWING getFlowing() {
        return flowingRegistryObject.get();
    }

    public STILL getStill() {
        return stillRegistryObject.get();
    }

    public STILL getFluid() {
        return stillRegistryObject.get();
    }

    void updateStill(RegistryObject<STILL> still) {
        this.stillRegistryObject = still;
    }

    void updateFlowing(RegistryObject<FLOWING> flowing) {
        this.flowingRegistryObject = flowing;
    }

    void updateBucket(RegistryObject<BUCKET> bucket) {
        this.bucketRegistryObject = bucket;
    }

    void updateBlock(RegistryObject<BLOCK> block) {
        this.blockRegistryObject = block;
    }
}
