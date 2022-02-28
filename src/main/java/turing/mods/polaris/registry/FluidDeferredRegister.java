package turing.mods.polaris.registry;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IDispenseItemBehavior;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.item.BucketItemGenerated;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.function.Supplier;

public class FluidDeferredRegister {
    private final HashMap<String, FluidRegistryObject<?, ?, ?, ?>> fluids = new HashMap<>();
    private final DeferredRegister<Fluid> fluidDeferredRegister;
    private final DeferredRegister<Item> itemDeferredRegister;
    private final DeferredRegister<Block> blockDeferredRegister;

    private static final ResourceLocation OVERLAY = Polaris.mcLoc("block/water_overlay");
    private static final IDispenseItemBehavior BUCKET_DISPENSE_BEHAVIOR = new DefaultDispenseItemBehavior() {
        @Nonnull
        @Override
        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            World world = source.getWorld();
            BucketItem bucket = (BucketItem) stack.getItem();
            BlockPos pos = source.getBlockPos().offset(source.getBlockState().get(DispenserBlock.FACING));
            if (bucket.tryPlaceContainedLiquid(null, world, pos, null)) {
                bucket.onLiquidPlaced(world, stack, pos);
                return new ItemStack(Items.BUCKET);
            }
            return super.dispenseStack(source, stack);
        }
    };

    public FluidDeferredRegister() {
        blockDeferredRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, Polaris.MODID);
        itemDeferredRegister = DeferredRegister.create(ForgeRegistries.ITEMS, Polaris.MODID);
        fluidDeferredRegister = DeferredRegister.create(ForgeRegistries.FLUIDS, Polaris.MODID);
    }

    public HashMap<String, FluidRegistryObject<?, ?, ?, ?>> getFluids() {
        return fluids;
    }

    public BasicFluidRegistryObject register(String name, FluidAttributes.Builder builder, Supplier<turing.mods.polaris.material.Material> materialSupplier) {
        builder.overlay(OVERLAY);
        BasicFluidRegistryObject fluidRegistryObject = new BasicFluidRegistryObject(name);
        ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(fluidRegistryObject::getStill, fluidRegistryObject::getFlowing, builder).bucket(fluidRegistryObject::getBucket).block(fluidRegistryObject::getBlock);
        fluidRegistryObject.updateStill(fluidDeferredRegister.register(name, () -> new ForgeFlowingFluid.Source(properties)));
        fluidRegistryObject.updateFlowing(fluidDeferredRegister.register("flowing_" + name, () -> new ForgeFlowingFluid.Flowing(properties)));
        fluidRegistryObject.updateBucket(itemDeferredRegister.register(name + "_bucket", () -> new BucketItemGenerated(fluidRegistryObject::getStill, materialSupplier)));
        fluidRegistryObject.updateBlock(blockDeferredRegister.register(name, () -> new FlowingFluidBlock(fluidRegistryObject::getStill, AbstractBlock.Properties.create(Material.WATER).hardnessAndResistance(100.0F).notSolid().noDrops())));
        fluids.put(name, fluidRegistryObject);
        return fluidRegistryObject;
    }

    public BasicFluidRegistryObject register(String name, FluidAttributes.Builder builder) {
        return register(name, builder, () -> null);
    }

    public void register(IEventBus bus) {
        blockDeferredRegister.register(bus);
        itemDeferredRegister.register(bus);
        fluidDeferredRegister.register(bus);
    }

    public void registerBucketDispenseBehavior() {
        for (FluidRegistryObject<?, ?, ?, ?> fluidRegistryObject : getFluids().values()) {
            DispenserBlock.registerDispenseBehavior(fluidRegistryObject.getBucket(), BUCKET_DISPENSE_BEHAVIOR);
        }
    }
}
