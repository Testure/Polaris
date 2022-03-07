package turing.mods.polaris.ui;

import com.mojang.blaze3d.matrix.MatrixStack;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.container.MachineContainer;
import turing.mods.polaris.registry.MaterialRegistry;
import turing.mods.polaris.registry.MaterialRegistryObject;
import turing.mods.polaris.screen.MachineScreen;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class Slot {
    public static final ResourceLocation SLOTS_TEXTURE = Polaris.modLoc("textures/gui/modular/slots.png");

    public static class ItemSlot implements ISlot<ItemStack> {
        private final UIPos pos;
        private final Vector2i texturePos = new Vector2i(0, 0);
        private final Vector2i textureSize = new Vector2i(36, 18);
        private final int index;

        public ItemSlot(int index, UIPos pos) {
            this.pos = pos;
            this.index = index;
        }

        @Override
        public Vector2i getPos() {
            return pos.pos;
        }

        @Override
        public UIPos getUIPos() {
            return pos;
        }

        @Override
        public Vector2i getTexturePos() {
            return texturePos;
        }

        @Override
        public Vector2i getTextureSize() {
            return textureSize;
        }

        @Override
        public ResourceLocation getTexture() {
            return SLOTS_TEXTURE;
        }

        @Override
        public <c extends MachineContainer> ItemStack getContained(c container) {
            return container.getStack(index) != null ? container.getStack(index) : ItemStack.EMPTY;
        }
    }

    public static class FluidSlot implements ISlot<FluidStack> {
        private final UIPos pos;
        private final int index;
        private final Vector2i texturePos = new Vector2i(18, 0);
        private final Vector2i textureSize = new Vector2i(36, 18);

        public FluidSlot(int index, UIPos pos) {
            this.index = index;
            this.pos = pos;
        }

        @Override
        public Vector2i getPos() {
            return pos.pos;
        }

        @Override
        public UIPos getUIPos() {
            return pos;
        }

        @Override
        public Vector2i getTexturePos() {
            return texturePos;
        }

        @Override
        public Vector2i getTextureSize() {
            return textureSize;
        }

        @Override
        public ResourceLocation getTexture() {
            return SLOTS_TEXTURE;
        }

        @Override
        public <T extends MachineScreen<? extends MachineContainer>> void addTooltips(List<ITextComponent> tooltips, T screen, Vector2i screenSize, MatrixStack matrixStack, int x, int y) {
            if (true) {
                FluidStack stack = getContained(screen.getContainer());
                if (!stack.isEmpty()) {
                    tooltips.add(new TranslationTextComponent(stack.getTranslationKey()));
                    if (Polaris.isMaterialItem(stack)) {
                        MaterialRegistryObject material = MaterialRegistry.getMaterials().get(stack.getFluid().getRegistryName().getPath());
                        if (material != null) {
                            tooltips.addAll(Arrays.asList(material.get().getFluidTooltips()));
                        }
                    }
                }
            }
        }

        @Override
        public <c extends MachineContainer> FluidStack getContained(c container) {
            return container.getFluidStack(index) != null ? container.getFluidStack(index) : FluidStack.EMPTY;
        }
    }
}
