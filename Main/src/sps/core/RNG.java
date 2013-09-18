package sps.core;

import java.util.List;
import java.util.Random;

public class RNG {
    private static Random SyncedRand;
    private static Random Rand;
    private static int lastSyncSeed;

    private static Random getRand(boolean synced) {
        if (SyncedRand == null) {
            seed((int) System.currentTimeMillis());
        }
        return synced ? SyncedRand : Rand;
    }

    public static void seed(int seed) {
        lastSyncSeed = seed;
        Rand = new Random(seed);
        SyncedRand = new Random(seed);
    }

    public static int next(int min, int max) {
        return next(min, max, true);
    }

    public static int next(int min, int max, boolean synced) {
        if (max - min > 0) {
            return getRand(synced).nextInt(max - min) + min;
        }
        if (max == min) {
            return min;
        }
        try {
            throw new RuntimeException("You cannot generate a random number if max is less than min!");
        }
        catch (Exception e) {
            Logger.exception(e);
        }
        return 0;
    }

    public static boolean percent(int percent) {
        return percent(percent, true);
    }

    public static boolean percent(int percent, boolean synced) {
        if (RNG.next(0, 100, synced) <= percent) {
            return true;
        }
        return false;
    }

    public static double angle() {
        return angle(true);
    }

    public static double angle(boolean synced) {
        return getRand(synced).nextInt(360) * Math.PI / 180;
    }

    public static boolean coinFlip() {
        return coinFlip(true);
    }

    public static boolean coinFlip(boolean synced) {
        return getRand(synced).nextInt(2) == 1;
    }

    public static Point2 point(int minX, int maxX, int minY, int maxY) {
        return new Point2(RNG.next(minX, maxX), RNG.next(minY, maxY));
    }

    public static double next(boolean synced) {
        return getRand(synced).nextDouble();
    }

    public static double next() {
        return next(true);
    }

    public static int next(int max) {
        return next(0, max);
    }

    public static <T> T pick(T[] choices) {
        return choices[RNG.next(choices.length)];
    }

    public static <T> T pick(List<T> choices) {
        return choices.get(RNG.next(choices.size()));
    }
}
