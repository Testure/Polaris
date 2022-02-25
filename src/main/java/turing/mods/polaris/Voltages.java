package turing.mods.polaris;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class Voltages {
    public static final Voltage[] VOLTAGES = new Voltage[]{
            new Voltage(8, "ulv", 0xFF555555),
            new Voltage(32, "lv", 0xFFAAAAAA),
            new Voltage(128, "mv", 0xFFFFAA00),
            new Voltage(512, "hv", 0xFFFFFF55),
            new Voltage(2048, "ev", 0xFFAA00AA),
            new Voltage(8192, "iv", 0xFF5555FF)
    };
    public static final long[] AMPS = new long[]{1L, 2L, 4L, 8L, 16L};

    public static final int ULV = 0;
    public static final int LV = 1;
    public static final int MV = 2;
    public static final int HV = 4;
    public static final int EV = 4;
    public static final int IV = 5;

    private static final int STORAGE_MULTIPLIER = 64;

    public static int getAmpIndex(long amps) {
        for (int i = 0; i < AMPS.length; i++) {
            if (AMPS[i] == amps) return i;
        }
        return 0;
    }

    @Nullable
    public static String voltageToString(int voltage) {
        for (int i = 0; i < VOLTAGES.length; i++) {
            if (voltage <= VOLTAGES[i].energy && ((i + 1) >= VOLTAGES.length || voltage < VOLTAGES[i + 1].energy)) return VOLTAGES[i].name;
        }
        return null;
    }

    public static String getVoltageTranslationKey(int voltage) {
        String str = voltageToString(voltage);
        return "voltage.polaris." + (str != null ? str : "ulv");
    }

    public static String getVoltageTierTranslationKey(int tier) {
        return getVoltageTranslationKey(VOLTAGES[tier].energy);
    }

    public static int getEnergyCapacity(int voltage) {
        return voltage * STORAGE_MULTIPLIER;
    }

    public static int getLossAdjusted(int voltageTier) {
        return voltageTier == 0 ? 7 : 30 * ((int) Math.pow(4, voltageTier - 1));
    }

    public static int roundVoltage(int voltage) {
        for (int i = 0; i < VOLTAGES.length; i++) {
            if (voltage <= VOLTAGES[i].energy && ((i + 1) >= VOLTAGES.length || voltage < VOLTAGES[i + 1].energy)) return VOLTAGES[i].energy;
        }
        return VOLTAGES[VOLTAGES.length - 1].energy;
    }

    public static class Voltage {
        public final int energy;
        public final String name;
        public final int capacity;
        public final int color;

        public Voltage(int energy, String name, int color) {
            this.energy = energy;
            this.name = name;
            this.capacity = getEnergyCapacity(energy);
            this.color = color;
        }
    }
}
