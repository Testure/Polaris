package turing.mods.polaris;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class Voltages {
    public static final Voltage[] VOLTAGES = new Voltage[]{
            new Voltage(8, "ulv"),
            new Voltage(32, "lv"),
            new Voltage(128, "mv"),
            new Voltage(512, "hv"),
            new Voltage(2048, "ev"),
            new Voltage(8192, "iv")
    };

    public static final int ULV = 0;
    public static final int LV = 1;
    public static final int MV = 2;
    public static final int HV = 4;
    public static final int EV = 4;
    public static final int IV = 5;

    private static final int STORAGE_MULTIPLIER = 64;

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

        public Voltage(int energy, String name) {
            this.energy = energy;
            this.name = name;
            this.capacity = getEnergyCapacity(energy);
        }
    }
}
