package sps.bridge;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.utils.GdxNativesLoader;
import sps.core.SpsConfig;

public class Sps {
    private static Application _application;

    public static Application getApp() {
        return _application;
    }

    public static void setup(Application application, boolean enableGraphics) {
        _application = application;
        GdxNativesLoader.load();
        Bridge.setup(enableGraphics);
    }

    public static class Entities {
        public static final String Actor = "Actor";
        public static final String Floor = "Floor";
    }

    public static class Actors {
        public static final String Player = "Player";
    }

    public static class ActorGroups {
        public static final String Non_Player = "Non_Player";
        public static final String Friendly = "Friendly";
    }

    public static class Contexts {
        public static final String Non_Free = "Non_Free";
        public static final String Free = "Free";
        public static final String All = "All";
    }

    public static class DrawDepths {
        public static final String Debug = "Debug";
        public static final String DefaultText = "DefaultText";
        public static final String AnimatedTexture = "AnimatedTexture";
    }

    public static final float SpriteRadius = (float) Math.sqrt(Math.pow(SpsConfig.get().spriteHeight / 2, 2) + Math.pow(SpsConfig.get().spriteWidth, 2));
    public static final int AnimationFps = 20;
}
