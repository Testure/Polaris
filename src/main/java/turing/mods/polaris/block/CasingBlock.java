package turing.mods.polaris.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import turing.mods.polaris.Voltages;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CasingBlock extends Block {
    private final int tier;

    public CasingBlock(int tier) {
        super(Properties.of(Material.HEAVY_METAL)
                .sound(SoundType.NETHERITE_BLOCK)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .strength(2.5F, 5.0F)
                .requiresCorrectToolForDrops()
                .isValidSpawn((a, b, c, d) -> false)
        );
        this.tier = tier;
    }

    @Override
    @Nonnull
    public String getDescriptionId() {
        return "block.polaris.casing." + Voltages.VOLTAGES[tier].name;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> tooltips, ITooltipFlag flag) {
        tooltips.add(new TranslationTextComponent("tooltip.polaris.hull_spawn"));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.hull_disclaimer"));
        super.appendHoverText(stack, reader, tooltips, flag);
    }

    @Override
    @Nonnull
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getName() {
        return new TranslationTextComponent(getDescriptionId());
    }
}
