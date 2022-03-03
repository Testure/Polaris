package turing.mods.polaris.recipe;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import javax.annotation.ParametersAreNonnullByDefault;

import static turing.mods.polaris.Voltages.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CompressorRecipes {
    public static void add() {
        compressorRecipes();
    }

    private static void compressorRecipes() {
        //ice compression
        Recipes.COMPRESSOR.addRecipe("ice_compressing", MachineRecipeBuilder.builder()
                .inputs(new ItemStack(Items.ICE, 9))
                .outputs(new ItemStack(Items.PACKED_ICE))
                .EUt(getLossAdjusted(ULV))
                .duration(400)
                .build()
        );
        Recipes.COMPRESSOR.addRecipe("packed_ice_compressing", MachineRecipeBuilder.builder()
                .inputs(new ItemStack(Items.PACKED_ICE, 9))
                .outputs(new ItemStack(Items.BLUE_ICE))
                .EUt(getLossAdjusted(LV))
                .duration(600)
                .build()
        );

        //honey
        Recipes.COMPRESSOR.addRecipe("honey_comb_compressing", MachineRecipeBuilder.builder()
                .inputs(new ItemStack(Items.HONEYCOMB, 4))
                .outputs(new ItemStack(Items.HONEYCOMB_BLOCK))
                .EUt(getLossAdjusted(ULV))
                .duration(400)
                .build()
        );

        //slime
        Recipes.COMPRESSOR.addRecipe("slime_ball_compressing", MachineRecipeBuilder.builder()
                .inputs(new ItemStack(Items.SLIME_BALL, 9))
                .outputs(new ItemStack(Items.SLIME_BLOCK))
                .EUt(getLossAdjusted(LV))
                .duration(400)
                .build()
        );

        //bricks
        Recipes.COMPRESSOR.addRecipe("brick_compressing", MachineRecipeBuilder.builder()
                .inputs(new ItemStack(Items.BRICK, 4))
                .outputs(new ItemStack(Items.BRICKS))
                .EUt(getLossAdjusted(ULV))
                .duration(400)
                .build()
        );
        Recipes.COMPRESSOR.addRecipe("nether_brick_compressing", MachineRecipeBuilder.builder()
                .inputs(new ItemStack(Items.NETHER_BRICK, 4))
                .outputs(new ItemStack(Items.NETHER_BRICKS))
                .EUt(getLossAdjusted(LV))
                .duration(400)
                .build()
        );
    }
}
