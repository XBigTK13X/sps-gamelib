package com.simplepathstudios.gamelib.draw;

import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.color.RGBA;

public enum Biome {
    Land(true, Map.C / 2, 180),
    Desert(true, 180, Map.C),
    Water(false, Map.C / 3, Map.C / 2),
    Ice(true, 0, Map.C / 3);

    private static final int __colorMin = 10;
    private static final int __colorMax = 245;

    public final boolean Habitable;
    public final int ElevationMax;
    public final int ElevationMin;

    private Biome(boolean habitable, int elevationMin, int elevationMax) {
        Habitable = habitable;
        ElevationMax = elevationMax;
        ElevationMin = elevationMin;
    }

    public static Biome fromElevation(int elevation) {
        for (Biome b : values()) {
            if (b.ElevationMin <= elevation && b.ElevationMax >= elevation) {
                return b;
            }
        }
        throw new RuntimeException("Undefined biome elevation!");
    }

    public static Color getColor(int elevation) {
        int r = 0;
        int g = 0;
        int b = 0;
        switch (fromElevation(elevation)) {
            case Land:
                g = elevation;
                break;
            case Desert:
                r = g = elevation;
                b = elevation - 50;
                break;
            case Water:
                b = Map.C - elevation;
                break;
            case Ice:
                r = g = Map.C - elevation / 3 - 30;
                b = Map.C - elevation / 2 + 30;
                break;
        }

        return new RGBA(cap(r), cap(g), cap(b), 255).toColor();
    }

    private static int cap(int colorComponent) {
        return Math.min(Math.max(__colorMin, colorComponent), __colorMax);
    }
}
