package sps.audio;

import com.badlogic.gdx.audio.Music;
import sps.core.SpsConfig;

public abstract class MusicPlayer {
    private static MusicPlayer __instance;
    private static MusicPlayer togglePlayer;

    public static void set(MusicPlayer musicPlayer) {
        reset();
        if (SpsConfig.get().musicEnabled) {
            __instance = musicPlayer;
        }
        else {
            __instance = new MuteMusicPlayer();
        }
    }

    public static void reset() {
        if (__instance != null) {
            __instance.stop();
        }
        __instance = null;
    }

    public static MusicPlayer get(MusicPlayer player) {

        if (!SpsConfig.get().musicEnabled) {
            __instance = new MuteMusicPlayer();
        }
        else {
            if (__instance == null) {
                if (SpsConfig.get().musicEnabled && player != null) {
                    __instance = player;
                }
                else {
                    __instance = new MuteMusicPlayer();
                }
            }
            else {
                if (SpsConfig.get().musicEnabled) {
                    __instance.stop();
                    __instance = player;
                }
            }
        }
        return __instance;
    }

    public static MusicPlayer get() {
        if (__instance == null) {
            __instance = new MuteMusicPlayer();
        }
        return __instance;
    }

    public static void toggle() {
        __instance.stop();
        if (togglePlayer == null) {
            togglePlayer = __instance;
            __instance = new MuteMusicPlayer();
        }
        else {
            __instance = togglePlayer;
            togglePlayer = null;
        }
        __instance.start();
    }

    protected Music _song;

    public abstract void start();

    public abstract void stop();

    public Music getMusic() {
        return _song;
    }
}
