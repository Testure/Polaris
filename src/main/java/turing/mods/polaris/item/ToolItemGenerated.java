package turing.mods.polaris.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.material.Material;
import turing.mods.polaris.material.SubItem;
import turing.mods.polaris.util.Formatting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static net.minecraft.block.material.Material.*;
import static net.minecraft.util.text.TextFormatting.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ToolItemGenerated extends Item implements IMaterialToolItem {
    protected final Supplier<Material> materialSupplier;
    protected final SubItem subItem;
    private Ingredient repairIngredient;
    protected ToolType toolType;
    private final Multimap<Attribute, AttributeModifier> defaultAttributeModifiers;

    public ToolItemGenerated(Supplier<Material> materialSupplier, SubItem subItem, ToolType toolType) {
        super(new Properties().group(Polaris.TOOLS).maxDamage(1).addToolType(toolType, 2));
        this.materialSupplier = materialSupplier;
        this.subItem = subItem;
        this.toolType = toolType;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", getAttackDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION));
        this.defaultAttributeModifiers = builder.build();
    }

    public SubItem getSubItem() {
        return subItem;
    }

    public float getAttackSpeed() {
        Material material = getMaterial();
        float speed = material.getToolStats().getAttackSpeed();
        if (toolType == ToolType.get("sword")) speed += 0.6F;
        if (toolType == ToolType.AXE || toolType == Polaris.ToolTypes.SAW || toolType == Polaris.ToolTypes.HAMMER) speed -= 0.2F;
        if (toolType == ToolType.PICKAXE) speed += 0.2F;
        if (toolType == Polaris.ToolTypes.CROWBAR) speed -= 0.5F;
        return speed;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        int damage = stack.getDamage();
        int durability = stack.getMaxDamage();
        double percent = ((double) damage / (double) durability * 100D);
        tooltips.add(new TranslationTextComponent("tooltip.polaris.durability", (percent >= 25 && percent <= 70 ? YELLOW : (percent < 25 ? GREEN : RED)) + Formatting.formattedNumber(durability - damage), GREEN + Formatting.formattedNumber(durability)));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.mining_speed", BLUE + Float.toString(getEfficiency())));
        super.addInformation(stack, world, tooltips, flag);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        return toolType != ToolType.get("sword") || !player.isCreative();
    }

    @Nonnull
    @Override
    public String getTranslationKey() {
        return "tool.polaris." + getSubItem().name().toLowerCase();
    }

    @Nonnull
    @Override
    public String getTranslationKey(ItemStack stack) {
        return getTranslationKey();
    }

    @Nonnull
    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return getName();
    }

    @Nonnull
    @Override
    public ITextComponent getName() {
        return new TranslationTextComponent(getTranslationKey(), new TranslationTextComponent("material.polaris." + getMaterial().getName()));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        net.minecraft.block.material.Material blockMaterial = state.getMaterial();
        switch (toolType.getName()) {
            case "crowbar":
                return blockMaterial != WEB && state.getBlock() != Blocks.CHAIN && state.getBlock() != Blocks.IRON_BARS && state.getBlock() != Blocks.IRON_DOOR ? super.getDestroySpeed(stack, state) : getEfficiency();
            case "hammer":
                return blockMaterial != ROCK && blockMaterial != IRON ? super.getDestroySpeed(stack, state) : getEfficiency();
            case "axe":
            case "saw":
                return blockMaterial != WOOD && blockMaterial != NETHER_WOOD ? super.getDestroySpeed(stack, state) : getEfficiency();
            case "wrench":
                return blockMaterial != IRON && blockMaterial != ANVIL && blockMaterial != PISTON ? super.getDestroySpeed(stack, state) : getEfficiency();
            case "pickaxe":
                return blockMaterial != IRON && blockMaterial != ANVIL && blockMaterial != ROCK && blockMaterial != ICE ? super.getDestroySpeed(stack, state) : getEfficiency();
            case "sword":
                return blockMaterial != WEB && blockMaterial != BAMBOO ? super.getDestroySpeed(stack, state) : getEfficiency();
            case "hoe":
                return blockMaterial != LEAVES ? super.getDestroySpeed(stack, state) : getEfficiency();
            case "shovel":
                return blockMaterial != EARTH && blockMaterial != SAND && blockMaterial != ORGANIC && blockMaterial != CLAY && blockMaterial != SNOW_BLOCK && blockMaterial != SNOW ? super.getDestroySpeed(stack, state) : getEfficiency();
            default:
                return super.getDestroySpeed(stack, state);
        }
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity livingEntity) {
        stack.damageItem(toolType == ToolType.get("sword") ? 1 : 2, livingEntity, (a) -> a.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (getDestroySpeed(stack, state) != getEfficiency()) stack.damageItem(2, entity, (a) -> a.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        else stack.damageItem(1, entity, (a) -> a.sendBreakAnimation(EquipmentSlotType.MAINHAND));
        return true;
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slotType) {
        return slotType == EquipmentSlotType.MAINHAND ? this.defaultAttributeModifiers : super.getAttributeModifiers(slotType);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return getEnchantability();
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        return tool == toolType ? getHarvestLevel() : 1;
    }

    @Nonnull
    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return new HashSet<>(Collections.singletonList(toolType));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getMaxUses();
    }

    @Override
    public int getEnchantability() {
        Material material = getMaterial();
        return material.getToolStats().getEnchantability();
    }

    @Override
    public int getMaxUses() {
        Material material = getMaterial();
        return material.getToolStats().getDurability();
    }

    @Override
    public float getEfficiency() {
        Material material = getMaterial();
        return material.getToolStats().getMiningSpeed();
    }

    @Override
    public float getAttackDamage() {
        Material material = getMaterial();
        float damage = material.getToolStats().getAttackDamage();
        if (toolType == ToolType.get("sword") || toolType == Polaris.ToolTypes.CROWBAR || toolType == Polaris.ToolTypes.SAW) damage += 1.0F;
        if (toolType == ToolType.AXE || toolType == Polaris.ToolTypes.HAMMER) damage += 3.0F;
        if (toolType == Polaris.ToolTypes.SOFT_HAMMER || toolType == Polaris.ToolTypes.MORTAR || toolType == ToolType.HOE) damage -= 1.0F;
        return damage;
    }

    @Override
    public int getHarvestLevel() {
        Material material = getMaterial();
        return material.mass >= 25 && material.mass <= 75 ? 2 : (material.mass < 25 ? 1 : 3);
    }

    @Nonnull
    @Override
    public Ingredient getRepairMaterial() {
        if (repairIngredient != null) return repairIngredient;
        Material material = getMaterial();
        String tag = "forge:ingots/iron";

        switch (material.getType()) {
            case "soft":
            case "ingot":
                if (material.getSubItems().contains(SubItem.INGOT)) tag = "forge:ingots/" + material.getName();
                break;
            case "gem":
                if (material.getSubItems().contains(SubItem.GEM)) tag = "forge:gems/" + material.getName();
                break;
            default:
                break;
        }
        repairIngredient = Ingredient.fromTag(ItemTags.makeWrapperTag(tag));
        return repairIngredient;
    }

    @Override
    public Material getMaterial() {
        return materialSupplier.get();
    }
}
