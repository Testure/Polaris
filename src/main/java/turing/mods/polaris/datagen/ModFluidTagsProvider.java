package turing.mods.polaris.datagen;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.registry.FluidRegistry;
import turing.mods.polaris.registry.FluidRegistryObject;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper fileHelper) {
        super(dataGenerator, Polaris.MODID, fileHelper);
    }

    @Override
    protected void registerTags() {
        for (FluidRegistryObject<?, ?, ?, ?> fluidRegistryObject : FluidRegistry.getFluids().values()) {
            getOrCreateBuilder(PolarisTags.Fluids.forge(fluidRegistryObject.getName())).add(fluidRegistryObject.getStill()).add(fluidRegistryObject.getFlowing());
            getOrCreateBuilder(PolarisTags.Fluids.mc("water")).add(fluidRegistryObject.getStill()).add(fluidRegistryObject.getFlowing());
        }
    }
}
