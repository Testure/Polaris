package turing.mods.polaris.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import turing.mods.polaris.Polaris;
import turing.mods.polaris.network.NetHandler;
import turing.mods.polaris.network.ProgramCircuitPacket;
import turing.mods.polaris.util.Vector2i;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class CircuitScreen extends Screen {
    protected int currentLevel = 0;

    protected CircuitScreen() {
        super(new TranslationTextComponent("screen.polaris.circuit"));
    }

    public CircuitScreen(int startLevel) {
        this();
        this.currentLevel = startLevel;
    }

    protected ResourceLocation getCircuitTexture() {
        return Polaris.modLoc("textures/item/circuit/" + (currentLevel + 1) + ".png");
    }

    protected void increase(int amount) {
        if ((currentLevel + amount) > 32) {
            currentLevel = 32;
            return;
        }
        this.currentLevel += amount;
    }

    protected void decrease(int amount) {
        if ((currentLevel - amount) < 0) {
            currentLevel = 0;
            return;
        }
        this.currentLevel -= amount;
    }

    @Override
    public void onClose() {
        super.onClose();
        NetHandler.sendToServer(new ProgramCircuitPacket(currentLevel));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isPointInRegion(25, 31, 18, 18, mouseX, mouseY)) buttonClick(1);
        else if (isPointInRegion(48, 31, 18, 18, mouseX, mouseY)) buttonClick(2);
        else if (isPointInRegion(100, 31, 18, 18, mouseX, mouseY)) buttonClick(3);
        else if (isPointInRegion(123, 31, 18, 18, mouseX, mouseY)) buttonClick(4);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    protected boolean isPointInRegion(int x, int y, int width, int height, double mouseX, double mouseY) {
        int i = (this.width - 176) / 2;
        int j = (this.height - 166) / 2;
        mouseX = mouseX - (double)i;
        mouseY = mouseY - (double)j;
        return mouseX >= (double)(x - 1) && mouseX < (double)(x + width + 1) && mouseY >= (double)(y - 1) && mouseY < (double)(y + height + 1);
    }

    protected void buttonClick(int button) {
        switch (button) {
            case 1:
                increase(5);
                playSound(Minecraft.getInstance().getSoundHandler());
                break;
            case 2:
                increase(1);
                playSound(Minecraft.getInstance().getSoundHandler());
                break;
            case 3:
                decrease(1);
                playSound(Minecraft.getInstance().getSoundHandler());
                break;
            case 4:
                decrease(5);
                playSound(Minecraft.getInstance().getSoundHandler());
                break;
            default:
        }
    }

    protected void playSound(SoundHandler handler) {
        handler.play(SimpleSound.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderBackground(matrixStack);
        Vector2i topLeft = new Vector2i((this.width - 176) / 2, (this.height - 166) / 2);
        RenderSystem.color4f(1, 1, 1, 1);
        Minecraft.getInstance().textureManager.bindTexture(Polaris.modLoc("textures/gui/circuit.png"));
        blit(matrixStack, topLeft.x, topLeft.y, 0, 0, 176, 166);

        drawString(matrixStack, Minecraft.getInstance().fontRenderer, this.title, topLeft.x + 5, topLeft.y + 5, 0xFFFFFF);
        drawString(matrixStack, Minecraft.getInstance().fontRenderer, "+5", topLeft.x + 30, topLeft.y + 36, 0xFFFFFF);
        drawString(matrixStack, Minecraft.getInstance().fontRenderer, "+1", topLeft.x + 53, topLeft.y + 36, 0xFFFFFF);
        drawString(matrixStack, Minecraft.getInstance().fontRenderer, "-1", topLeft.x + 105, topLeft.y + 36, 0xFFFFFF);
        drawString(matrixStack, Minecraft.getInstance().fontRenderer, "-5", topLeft.x + 128, topLeft.y + 36, 0xFFFFFF);

        Minecraft.getInstance().textureManager.bindTexture(getCircuitTexture());
        blit(matrixStack, topLeft.x + 77, topLeft.y + 32, 0, 0, 16, 16, 16, 16);
    }
}
