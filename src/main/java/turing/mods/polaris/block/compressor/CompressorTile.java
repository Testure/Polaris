package turing.mods.polaris.block.compressor;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.Voltages;
import turing.mods.polaris.recipe.IMachineRecipe;
import turing.mods.polaris.recipe.Recipes;
import turing.mods.polaris.registry.MachineRegistry;
import turing.mods.polaris.tile.IInventoryTile;
import turing.mods.polaris.tile.MachineEnergyHandler;
import turing.mods.polaris.tile.MachineTile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CompressorTile extends MachineTile implements ITickableTileEntity, IInventoryTile {
    private final int tier;
    private int time = -2;
    private int timeGoal = -1;
    private int energyUse = 0;
    private int ticks = 0;
    private boolean canOverclock;
    private final ItemStackHandler itemStackHandler;
    private final LazyOptional<IItemHandler> inventory;
    private ItemStack currentInput = null;
    private IMachineRecipe currentRecipe = null;
    private CompoundNBT recipeSearchDetails = null;

    public CompressorTile(int tier) {
        super(MachineRegistry.COMPRESSOR.getTiles().get(tier).get());
        this.tier = tier;
        this.itemStackHandler = createHandler();
        this.inventory = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void tick() {
        if (level == null || level.isClientSide) return;
        if (recipeSearchDetails != null && !Polaris.tagsResolved) return;

        if (recipeSearchDetails != null) {
            IMachineRecipe recipe = findRecipeFromTag(recipeSearchDetails);
            if (recipe != null) {
                currentRecipe = recipe;
                recipeSearchDetails = null;
            }
        }

        boolean hasRecipe = currentRecipe != null;
        boolean isRecipeDone = time >= timeGoal;

        if (!hasRecipe && !isRecipeDone && !this.workingDisabled) {
            ItemStack stack = this.itemStackHandler.getStackInSlot(0);
            IMachineRecipe recipe = null;

            if (!stack.isEmpty() && currentInput != null && currentInput.sameItem(stack)) {
                recipe = findRecipe(stack);

                if (recipe != null && energyHandler.getEnergy() > (recipe.getEUt() * 4L)) {
                    startRecipe(recipe);
                }
            }
            if (recipe == null && timeGoal > 0) {
                timeGoal = -1;
                time = -2;
            }
            hasRecipe = currentRecipe != null;
            isRecipeDone = time >= timeGoal;
        }

        if (!isRecipeDone && hasRecipe && !this.workingDisabled) {
            long energy = energyHandler.getEnergy();

            if (energy > (energyUse * 4L)) {
                recipeTick();
                hasRecipe = currentRecipe != null;
                isRecipeDone = time >= timeGoal;
            }
        }

        if (hasRecipe && isRecipeDone) {
            finishRecipe();
        }
    }

    private void readTag(CompoundNBT tag) {
        readProgress(tag);
        this.energyUse = tag.getInt("energyUse");
        this.canOverclock = tag.getBoolean("overclock");
        setDisabled(tag.getBoolean("disabled"));
        if (tag.contains("recipe")) recipeSearchDetails = tag.getCompound("recipe");
    }

    public void readProgress(CompoundNBT tag) {
        this.time = tag.getInt("time");
        this.timeGoal = tag.getInt("timeGoal");
    }

    private CompoundNBT writeTag(CompoundNBT tag) {
        tag.putInt("time", this.time);
        tag.putInt("timeGoal", this.timeGoal);
        tag.putInt("energyUse", this.energyUse);
        tag.putBoolean("overclock", this.canOverclock);
        tag.putBoolean("disabled", this.workingDisabled);
        if (currentRecipe != null && currentInput != null) tag.put("recipe", serializeRecipe(currentInput, currentRecipe.getEUt()));
        return tag;
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        return writeTag(super.save(tag));
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        readTag(tag);
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
        readTag(tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return writeTag(super.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        readTag(pkt.getTag());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getBlockPos(), -1, writeTag(new CompoundNBT()));
    }

    @Override
    public void handleDisabled(boolean previousValue) {
        if (level == null) return;
        if (!previousValue && this.workingDisabled && currentRecipe != null) updateState(level.getBlockState(getBlockPos()), 0);
        else if (previousValue && !this.workingDisabled && currentRecipe != null) updateState(level.getBlockState(getBlockPos()), -1);
    }

    private static CompoundNBT serializeRecipe(ItemStack input, int voltage) {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("voltage", voltage);
        tag.put("input", input.serializeNBT());
        return tag;
    }

    @Nullable
    private static IMachineRecipe findRecipeFromTag(CompoundNBT tag) {
        return Recipes.COMPRESSOR.findRecipe(new ItemStack[]{ItemStack.of(tag.getCompound("input"))}, null, tag.getInt("voltage"), -1, -1, true);
    }

    @Nullable
    private IMachineRecipe findRecipe(ItemStack stack) {
        return Recipes.COMPRESSOR.findRecipe(new ItemStack[]{stack}, null, Voltages.VOLTAGES[tier].energy, -1, -1, false);
    }

    private void recipeTick() {
        int energyToRemove = Math.min(energyUse, Voltages.VOLTAGES[tier].energy);

        if (time >= 0)
            if (energyHandler.removeEnergy(energyToRemove) < energyToRemove) {
                time = -3;
                setChanged();
                return;
                //TODO out of energy sound
            }

        time += 1;
        setChanged();
    }

    private void finishRecipe() {
        ItemStack output = currentRecipe.getOutputs().get(0);
        ItemStack currentOutput = this.itemStackHandler.getStackInSlot(1);
        int amountAfterOutput = output.getCount() + currentOutput.getCount();

        timeGoal = -1;

        if (isSlotFree(1, output)) {
            time = -2;
            this.itemStackHandler.setStackInSlot(1, new ItemStack(output.getItem(), amountAfterOutput));
            if (this.itemStackHandler.getStackInSlot(0).isEmpty()) {
                currentInput = null;
                currentRecipe = null;
                updateState(level.getBlockState(worldPosition), -1);
            } else if (this.itemStackHandler.getStackInSlot(0).sameItem(currentInput)) {
                startRecipe(currentRecipe);
            }
            setChanged();
        }
    }

    private void startRecipe(IMachineRecipe recipe) {
        ItemStack newStack = this.currentInput.copy();
        newStack.setCount(newStack.getCount() - 1);
        this.currentRecipe = recipe;
        this.time = 0;
        this.timeGoal = recipe.getDuration();
        this.energyUse = recipe.getEUt();
        this.itemStackHandler.setStackInSlot(0, newStack);
        updateState(level.getBlockState(worldPosition), -1);
    }

    private void updateState(BlockState blockState, int override) {
        boolean isPowered = blockState.getValue(BlockStateProperties.POWERED);
        boolean shouldUpdateState = override > -1 ? (override == 1) : isPowered == (currentRecipe == null);
        int blockFlags = Constants.BlockFlags.NOTIFY_NEIGHBORS + Constants.BlockFlags.BLOCK_UPDATE;

        if (shouldUpdateState)
            level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, override > -1 ? (override == 1) : currentRecipe != null), blockFlags);
        if (blockState.getValue(BlockStateProperties.POWERED)) /*TODO machine sounds*/;
    }

    private boolean isSlotFree(int slot, ItemStack wantedStack) {
        ItemStack currentStack = this.itemStackHandler.getStackInSlot(slot);
        return currentStack == ItemStack.EMPTY || (currentStack.sameItem(wantedStack) && (currentStack.getCount() + wantedStack.getCount()) <= this.itemStackHandler.getSlotLimit(slot) && (currentStack.getCount() + wantedStack.getCount()) <= currentStack.getMaxStackSize());
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inventory.invalidate();
    }

    public void encodeExtraData(PacketBuffer buffer) {
        buffer.writeBlockPos(getBlockPos());
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.itemStackHandler.getSlots(); i++)
            if (!this.itemStackHandler.getStackInSlot(i).isEmpty()) return false;
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.itemStackHandler.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return this.itemStackHandler.extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = this.itemStackHandler.getStackInSlot(slot);
        this.itemStackHandler.setStackInSlot(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.itemStackHandler.setStackInSlot(slot, stack);
    }

    @Override
    public World getWorld() {
        return getLevel();
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.itemStackHandler.getSlots() - 1; i++) {
            removeItemNoUpdate(i);
        }
        setItem(this.itemStackHandler.getSlots() - 1, ItemStack.EMPTY);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return isUsableByPlayer(player);
    }

    @Override
    public void clearContent() {
        clear();
    }

    private ItemStackHandler createHandler() {
        ItemStackHandler handler = new ItemHandler();
        handler.setSize(2);
        return handler;
    }

    @Override
    protected MachineEnergyHandler createEnergyHandler() {
        Voltages.Voltage voltage = Voltages.VOLTAGES[tier + 1];
        return new MachineEnergyHandler(this, voltage.capacity, 2L, voltage.energy, false, true);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return inventory.cast();
        return super.getCapability(cap, side);
    }

    protected class ItemHandler extends ItemStackHandler {
        public ItemHandler() {
            super(2);
        }

        @Override
        protected void onContentsChanged(int slot) {
            ItemStack newStack = this.getStackInSlot(0);

            if (currentInput == null || (!newStack.sameItem(currentInput) && !newStack.isEmpty())) {
                currentInput = newStack;
            }

            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return slot == 0;
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!canInsertIntoSlot(stack, slot)) return stack;
            return super.insertItem(slot, stack, simulate);
        }
    }
}
