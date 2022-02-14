package turing.mods.polaris.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class CreativePowerProvider extends Block {
    public CreativePowerProvider() {
        super(AbstractBlock.Properties.of(Material.METAL)
                .harvestTool(ToolType.PICKAXE)
                .strength(-1.0F, 50.0F)
                .sound(SoundType.NETHERITE_BLOCK)
                .harvestLevel(0)
                .noDrops()
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable IBlockReader reader, @Nonnull List<ITextComponent> tooltips, @Nonnull ITooltipFlag flag) {

        super.appendHoverText(stack, reader, tooltips, flag);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return super.hasTileEntity(state);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return super.createTileEntity(state, world);
    }
}
