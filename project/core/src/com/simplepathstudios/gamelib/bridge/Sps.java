package com.simplepathstudios.gamelib.bridge;

import com.badlogic.gdx.Application;

public class Sps {
    private static Application _application;

    public static Application getApp() {
        return _application;
    }

    public static void setup(Application application, boolean enableGraphics) {
        _application = application;
        Bridge.setup(enableGraphics);
    }

    public static final int AnimationFps = 20;
}
