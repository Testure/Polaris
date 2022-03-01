package turing.mods.polaris.block;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.material.Material;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SubBlockGenerated extends Block implements IRenderTypedBlock, ITintedBlock {
    public final String name;
    private final Supplier<Material> material;
    private final SubItem subItem;

    public SubBlockGenerated(String name, Supplier<Material> material, SubItem subItem, net.minecraft.block.material.Material blockMaterial, int harvestLevel) {
        super(AbstractBlock.Properties.create(blockMaterial)
                .sound(blockMaterial == net.minecraft.block.material.Material.IRON ? SoundType.METAL : SoundType.STONE)
                .harvestLevel(harvestLevel)
                .harvestTool(ToolType.PICKAXE)
                .hardnessAndResistance((float) harvestLevel + 0.5F, (float) harvestLevel + 1.5F)
        );
        this.name = name;
        this.material = material;
        this.subItem = subItem;
    }

    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        ItemStack tool = builder.get(LootParameters.TOOL);
        if (tool != null && tool.getToolTypes().contains(Polaris.ToolTypes.HAMMER)) {
            if (subItem == SubItem.ORE && getMaterial().getSubItems().contains(SubItem.CRUSHED_ORE)) {
                MaterialRegistryObject registryObject = MaterialRegistry.getMaterials().get(getMaterial().getName());
                Item crushedOre = registryObject.getItemFromSubItem(SubItem.CRUSHED_ORE);
                if (crushedOre != null) return Collections.singletonList(new ItemStack(crushedOre, 1));
            } else if (subItem == SubItem.BLOCK && getMaterial().getSubItems().contains(SubItem.DUST)) {
                MaterialRegistryObject registryObject = MaterialRegistry.getMaterials().get(getMaterial().getName());
                Item dust = registryObject.getItemFromSubItem(SubItem.DUST);
                if (dust != null) return Collections.singletonList(new ItemStack(dust, 8));
            }
        }
        return super.getDrops(state, builder);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public int getColor(@Nonnull BlockState blockState, @Nullable IBlockDisplayReader reader, @Nullable BlockPos pos, int layer) {
        return subItem == SubItem.ORE ? (layer == 1 ? getMaterial().color : 0xFFFFFFFF) : getMaterial().color;
    }

    @Override
    public RenderType getRenderType() {
        return RenderType.getCutoutMipped();
    }

    public SubItem getSubItem() {
        return subItem;
    }

    public Material getMaterial() {
        return material.get();
    }

    @Nonnull
    @Override
    public String getTranslationKey() {
        return "block.polaris." + subItem.name().toLowerCase();
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public IFormattableTextComponent getTranslatedName() {
        return new TranslationTextComponent(getTranslationKey(), new TranslationTextComponent("material.polaris." + getMaterial().getName()));
    }
}
