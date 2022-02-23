package turing.mods.polaris.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
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
public class ToolItemGenerated extends Item implements IMaterialToolItem {
    protected final Supplier<Material> materialSupplier;
    protected final SubItem subItem;
    private Ingredient repairIngredient;
    protected ToolType toolType;
    private final Multimap<Attribute, AttributeModifier> defaultAttributeModifiers;

    public ToolItemGenerated(Supplier<Material> materialSupplier, SubItem subItem, ToolType toolType) {
        super(new Properties().tab(Polaris.TOOLS).stacksTo(1).addToolType(toolType, 2));
        this.materialSupplier = materialSupplier;
        this.subItem = subItem;
        this.toolType = toolType;
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier", getAttackDamageBonus(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier", getAttackSpeed(), AttributeModifier.Operation.ADDITION));
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
        return speed;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltips, ITooltipFlag flag) {
        int damage = stack.getDamageValue();
        //I have forgotten how to calculate percentages.
        int percent = damage > 0 ? (int) ((float) (stack.getMaxDamage() / (stack.getMaxDamage() - damage))) : 100;
        tooltips.add(new TranslationTextComponent("tooltip.polaris.durability", (percent >= 25 && percent <= 70 ? YELLOW : (percent < 25 ? RED : GREEN)) + Formatting.formattedNumber(stack.getMaxDamage() - damage), GREEN + Formatting.formattedNumber(stack.getMaxDamage())));
        tooltips.add(new TranslationTextComponent("tooltip.polaris.mining_speed", BLUE + Float.toString(getSpeed())));
        super.appendHoverText(stack, world, tooltips, flag);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, PlayerEntity player) {
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public boolean canAttackBlock(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        return toolType != ToolType.get("sword") || !player.isCreative();
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return "tool.polaris." + getSubItem().name().toLowerCase();
    }

    @Nonnull
    @Override
    public String getDescriptionId(ItemStack stack) {
        return getDescriptionId();
    }

    @Nonnull
    @Override
    public ITextComponent getName(ItemStack stack) {
        return getDescription();
    }

    @Nonnull
    @Override
    public ITextComponent getDescription() {
        return new TranslationTextComponent(getDescriptionId(), new TranslationTextComponent("material.polaris." + getMaterial().getName()));
    }

    @Nonnull
    @Override
    protected String getOrCreateDescriptionId() {
        return getDescriptionId();
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        net.minecraft.block.material.Material blockMaterial = state.getMaterial();
        switch (toolType.getName()) {
            case "hammer":
                return blockMaterial != STONE && blockMaterial != METAL ? super.getDestroySpeed(stack, state) : getSpeed();
            case "axe":
            case "saw":
                return blockMaterial != WOOD && blockMaterial != NETHER_WOOD ? super.getDestroySpeed(stack, state) : getSpeed();
            case "wrench":
                return blockMaterial != METAL && blockMaterial != HEAVY_METAL && blockMaterial != PISTON ? super.getDestroySpeed(stack, state) : getSpeed();
            case "pickaxe":
                return blockMaterial != METAL && blockMaterial != HEAVY_METAL && blockMaterial != STONE && blockMaterial != ICE ? super.getDestroySpeed(stack, state) : getSpeed();
            case "sword":
                return blockMaterial != WEB && blockMaterial != BAMBOO ? super.getDestroySpeed(stack, state) : getSpeed();
            case "hoe":
                return blockMaterial != LEAVES ? super.getDestroySpeed(stack, state) : getSpeed();
            case "shovel":
                return blockMaterial != DIRT && blockMaterial != SAND && blockMaterial != GRASS && blockMaterial != CLAY && blockMaterial != TOP_SNOW && blockMaterial != SNOW ? super.getDestroySpeed(stack, state) : getSpeed();
            default:
                return super.getDestroySpeed(stack, state);
        }
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity livingEntity) {
        stack.hurtAndBreak(toolType == ToolType.get("sword") ? 1 : 2, livingEntity, (a) -> a.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        return true;
    }

    public boolean mineBlock(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (toolType == ToolType.get("sword") && state.getDestroySpeed(world, pos) != 0.0F) {
            stack.hurtAndBreak(2, entity, (a) -> a.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        } else if (!world.isClientSide && state.getDestroySpeed(world, pos) != 0.0F) {
            stack.hurtAndBreak(1, entity, (a) -> a.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
        }
        return true;
    }

    @Nonnull
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlotType slotType) {
        return slotType == EquipmentSlotType.MAINHAND ? this.defaultAttributeModifiers : super.getDefaultAttributeModifiers(slotType);
    }

    @Override
    public boolean canBeDepleted() {
        return true;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return getEnchantmentValue();
    }

    @Override
    public int getHarvestLevel(ItemStack stack, ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
        return tool == toolType ? getLevel() : 1;
    }

    @Nonnull
    @Override
    public Set<ToolType> getToolTypes(ItemStack stack) {
        return new HashSet<>(Collections.singletonList(toolType));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return getUses();
    }

    @Override
    public int getEnchantmentValue() {
        Material material = getMaterial();
        return material.getToolStats().getEnchantability();
    }

    @Override
    public int getUses() {
        Material material = getMaterial();
        return material.getToolStats().getDurability();
    }

    @Override
    public float getSpeed() {
        Material material = getMaterial();
        return material.getToolStats().getMiningSpeed();
    }

    @Override
    public float getAttackDamageBonus() {
        Material material = getMaterial();
        float damage = material.getToolStats().getAttackDamage();
        if (toolType == ToolType.get("sword") || toolType == Polaris.ToolTypes.CROWBAR || toolType == Polaris.ToolTypes.SAW) damage += 1.0F;
        if (toolType == ToolType.AXE || toolType == Polaris.ToolTypes.HAMMER) damage += 2.0F;
        if (toolType == Polaris.ToolTypes.SOFT_HAMMER || toolType == Polaris.ToolTypes.MORTAR) damage -= 1.0F;
        return damage;
    }

    @Override
    public int getLevel() {
        Material material = getMaterial();
        return material.mass >= 35 && material.mass <= 100 ? 2 : (material.mass < 35 ? 1 : 3);
    }

    @Nonnull
    @Override
    public Ingredient getRepairIngredient() {
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
        repairIngredient = Ingredient.of(ItemTags.bind(tag));
        return repairIngredient;
    }

    @Override
    public Material getMaterial() {
        return materialSupplier.get();
    }
}
