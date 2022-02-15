package turing.mods.polaris.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import turing.mods.polaris.material.Material;
import turing.mods.polaris.material.SubItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SubBlockGenerated extends Block implements IRenderTypedBlock, ITintedBlock {
    public final String name;
    private final Supplier<Material> material;
    private final SubItem subItem;

    public SubBlockGenerated(String name, Supplier<Material> material, SubItem subItem, net.minecraft.block.material.Material blockMaterial, int harvestLevel) {
        super(AbstractBlock.Properties.of(blockMaterial)
                .sound(blockMaterial == net.minecraft.block.material.Material.METAL ? SoundType.METAL : SoundType.STONE)
                .harvestLevel(harvestLevel)
                .harvestTool(ToolType.PICKAXE)
                .strength((float) harvestLevel + 1.5F, (float) harvestLevel + 3.0F)
                .requiresCorrectToolForDrops()
        );
        this.name = name;
        this.material = material;
        this.subItem = subItem;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getColor(@Nonnull BlockState blockState, @Nullable IBlockDisplayReader reader, @Nullable BlockPos pos, int layer) {
        return getMaterial().color;
    }

    @Override
    public RenderType getRenderType() {
        if (subItem == SubItem.ORE) return RenderType.cutoutMipped();
        return IRenderTypedBlock.super.getRenderType();
    }

    public SubItem getSubItem() {
        return subItem;
    }

    public Material getMaterial() {
        return material.get();
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return "block.polaris." + subItem.name().toLowerCase();
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getName() {
        return new TranslationTextComponent(getDescriptionId(), new TranslationTextComponent("material.polaris." + getMaterial().getName()));
    }
}
