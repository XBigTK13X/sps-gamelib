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

    public static class Contexts {
        public static final String Non_Free = "Non_Free";
        public static final String Free = "Free";
        public static final String All = "All";
    }

    public static class DrawDepths {
        public static final String DefaultText = "DefaultText";
        public static final String AnimatedTexture = "AnimatedTexture";
    }

    public static final int AnimationFps = 20;
}
