package turing.mods.polaris.container;

import java.util.ArrayList;
import java.util.List;

public class SlotInfoProvider {
    private final List<SlotInfo> slots = new ArrayList<>();

    public SlotInfoProvider(SlotInfo... slots) {
        for (int i = 0; i < slots.length; i++) {
            this.slots.add(new SlotInfo(i, slots[i].x, slots[i].y));
        }
    }

    public List<SlotInfo> getSlots() {
        return slots;
    }

    public static class SlotInfo {
        public final int index;
        public final int x;
        public final int y;

        public SlotInfo(int index, int x, int y) {
            this.index = index;
            this.x = x;
            this.y = y;
        }
    }
}
