package sps.data;

import sps.core.Loader;

public class DevConfig {
    private static Boolean __canEnable;
    public static boolean canEnable() {
        if (__canEnable == null) {
            __canEnable = !Loader.get().data("release_build").exists();
        }
        return __canEnable;
    }

    public static boolean EndToEndStateTest = false;
    public static boolean BotEnabled = false;
    public static boolean TimeStates = false;
}
