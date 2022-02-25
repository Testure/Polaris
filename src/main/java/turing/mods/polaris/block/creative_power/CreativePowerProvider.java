package turing.mods.polaris.block.creative_power;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import turing.mods.polaris.Polaris;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
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
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (world.isClientSide) return ActionResultType.FAIL;
        ItemStack stack = player.getItemInHand(hand);
        TileEntity te = world.getBlockEntity(pos);
        if (!stack.isEmpty() && (stack.getToolTypes().contains(Polaris.ToolTypes.HAMMER) || stack.getToolTypes().contains(Polaris.ToolTypes.SOFT_HAMMER))) {
            if (te instanceof CreativePowerProviderTile) {
                CreativePowerProviderTile tile = (CreativePowerProviderTile) te;
                if (stack.getToolTypes().contains(Polaris.ToolTypes.SOFT_HAMMER)) tile.adjustVoltage();
                else tile.adjustAmps();
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.FAIL;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CreativePowerProviderTile();
    }
}
