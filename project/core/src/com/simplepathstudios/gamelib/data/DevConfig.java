package com.simplepathstudios.gamelib.data;

import com.simplepathstudios.gamelib.core.Loader;

public class DevConfig {
    private static Boolean __canEnable;

    public static boolean canEnable() {
        if (__canEnable == null) {
            __canEnable = !Loader.get().data("release_build").exists();
        }
        return __canEnable;
    }

    public static boolean EndToEndStateTest = false && canEnable();
    public static boolean BotEnabled = false && canEnable();
    public static boolean TimeStates = false && canEnable();
    public static boolean ShortcutsEnabled = true && canEnable();
}
