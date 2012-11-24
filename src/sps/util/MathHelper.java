package sps.util;

public class MathHelper {

    public static int clamp(float value, int min, int max) {
        return (int) Math.max(Math.min(value, max), min);
    }
}
