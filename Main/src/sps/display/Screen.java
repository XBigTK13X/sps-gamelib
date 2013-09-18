package sps.display;

import sps.core.Point2;
import sps.core.RNG;
import sps.core.SpsConfig;

public class Screen {
    public static float height(int percent) {
        return Screen.get().VirtualHeight * ((float) percent / 100);
    }

    public static float width(int percent) {
        return Screen.get().VirtualWidth * ((float) percent / 100);
    }

    public static Point2 pos(int widthPercent, int heightPercent) {
        return new Point2(width(widthPercent), height(heightPercent));
    }

    public static Point2 rand(int widthPercentMin, int widthPercentMax, int heightPercentMin, int heightPercentMax) {
        return new Point2(width(RNG.next(widthPercentMin, widthPercentMax)), height(RNG.next(heightPercentMin, heightPercentMax)));
    }

    private static Screen __instance;

    public static Screen get() {
        if (__instance == null) {
            __instance = new Screen(SpsConfig.get().virtualWidth, SpsConfig.get().virtualHeight);
        }
        return __instance;
    }

    // This is the resolution used by the game internally
    public final int VirtualHeight;
    public final int VirtualWidth;
    public final float VirtualAspectRatio;

    private Screen(int width, int height) {
        VirtualWidth = width;
        VirtualHeight = height;
        VirtualAspectRatio = (float) width / (float) height;
    }

    public boolean isInView(int x, int y) {
        return x >= Window.get().getCameraPosition().X && y >= Window.get().getCameraPosition().Y && x <= VirtualWidth + Window.get().getCameraPosition().X && y <= VirtualHeight + Window.get().getCameraPosition().Y;
    }

    public boolean isInView(float x, float y) {
        return isInView((int) x, (int) y);
    }
}
