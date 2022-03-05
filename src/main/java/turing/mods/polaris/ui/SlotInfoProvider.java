package turing.mods.polaris.ui;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@OnlyIn(Dist.CLIENT)
public class SlotInfoProvider {
    private final ISlot<?>[] slots;

    public SlotInfoProvider(ISlot<?>... slots) {
        this.slots = new ISlot[slots.length];
        for (int i = 0; i < slots.length; i++) {
            this.slots[i] = slots[i];
        }
    }

    public ISlot<?>[] getSlots() {
        return slots;
    }
}
