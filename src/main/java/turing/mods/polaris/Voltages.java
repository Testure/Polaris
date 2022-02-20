package turing.mods.polaris;

import net.minecraft.util.Tuple;
import turing.mods.polaris.util.Lists;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Voltages {
    public static final List<Tuple<Integer, String>> VOLTAGE_LIST = Arrays.asList(
            new Tuple<>(8, "ulv"),
            new Tuple<>(32, "lv"),
            new Tuple<>(128, "mv"),
            new Tuple<>(512, "hv"),
            new Tuple<>(2048, "ev"),
            new Tuple<>(8192, "iv")
    );

    public static final int ULV = VOLTAGE_LIST.get(0).getA();
    public static final int LV = VOLTAGE_LIST.get(1).getA();
    public static final int MV = VOLTAGE_LIST.get(2).getA();
    public static final int HV = VOLTAGE_LIST.get(3).getA();
    public static final int EV = VOLTAGE_LIST.get(4).getA();
    public static final int IV = VOLTAGE_LIST.get(5).getA();

    private static final int STORAGE_MULTIPLIER = 64;

    @Nullable
    public static String voltageToString(int voltage) {
        for (int i = 0; i < VOLTAGE_LIST.size(); i++) {
            if (voltage <= VOLTAGE_LIST.get(i).getA() && ((i + 1) >= VOLTAGE_LIST.size() || voltage < VOLTAGE_LIST.get(i + 1).getA())) return VOLTAGE_LIST.get(i).getB();
        }
        return null;
    }

    public static String getVoltageTranslationKey(int voltage) {
        String str = voltageToString(voltage);
        return "voltage.polaris." + (str != null ? str : "ulv");
    }

    public static String getVoltageTierTranslationKey(int tier) {
        return getVoltageTranslationKey(VOLTAGE_LIST.get(tier).getA());
    }

    public static int getEnergyCapacity(int voltage) {
        return voltage * STORAGE_MULTIPLIER;
    }

    public static int getLossAdjusted(int voltageTier) {
        return voltageTier == 0 ? 7 : 30 * ((int) Math.pow(4, voltageTier - 1));
    }
}
