package sps.util;

public class Parse {
    public static int inte(String target) {
        return Integer.parseInt(target.trim());
    }

    public static boolean bool(String target) {
        return target.trim().toLowerCase().equalsIgnoreCase("true");
    }

    public static float floa(String target) {
        return Float.parseFloat(target.trim());
    }
}
