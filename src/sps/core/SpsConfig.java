package sps.core;

import org.apache.commons.io.FileUtils;
import sps.util.Parse;

import java.util.HashMap;

public class SpsConfig {
    private static SpsConfig __instance;

    public static SpsConfig get() {
        if (__instance == null) {
            __instance = new SpsConfig();
        }
        return __instance;
    }

    private HashMap<String, String> _settings = new HashMap<String, String>();

    public boolean musicEnabled;
    public boolean soundEnabled;

    public final int spriteHeight;
    public final int spriteWidth;
    public final int tileMapHeight;
    public final int tileMapWidth;
    public final int resolutionHeight;
    public final int resolutionWidth;
    public final int virtualHeight;
    public final int virtualWidth;
    public final boolean fullScreen;
    public final boolean vSyncEnabled;
    public final boolean displayLoggingEnabled;

    public final boolean entityGridEnabled;

    public final boolean viewPaths;
    public final boolean devConsoleEnabled;
    public final boolean controllersEnabled;

    public final int particleEffectPoolLimit;
    public final int particleEffectPoolStartSize;
    public final int maxColorLookupSize;


    private SpsConfig() {
        try {
            Logger.info("Parsing sps-gamelib.cfg");
            for (String line : FileUtils.readLines(Loader.get().data("sps-gamelib.cfg"))) {
                if (!line.contains("##") && line.length() > 1) {
                    String key = line.split("=")[0];
                    String value = line.split("=")[1];
                    _settings.put(key, value);
                }
            }
        }
        catch (Exception e) {
            Logger.exception(e);
        }

        // Audio
        musicEnabled = Parse.bool(_settings.get("musicEnabled"));
        soundEnabled = Parse.bool(_settings.get("soundEnabled"));

        // Display
        spriteHeight = Parse.inte(_settings.get("spriteHeight"));
        spriteWidth = Parse.inte(_settings.get("spriteWidth"));
        tileMapHeight = Parse.inte(_settings.get("tileMapHeight"));
        tileMapWidth = Parse.inte(_settings.get("tileMapWidth"));
        resolutionHeight = Parse.inte(_settings.get("resolutionHeight"));
        resolutionWidth = Parse.inte(_settings.get("resolutionWidth"));
        virtualHeight = Parse.inte(_settings.get("virtualHeight"));
        virtualWidth = Parse.inte(_settings.get("virtualWidth"));
        fullScreen = Parse.bool(_settings.get("fullScreen"));
        vSyncEnabled = Parse.bool(_settings.get("vSyncEnabled"));
        displayLoggingEnabled = Parse.bool(_settings.get("displayLoggingEnabled"));

        entityGridEnabled = Parse.bool(_settings.get("entityGridEnabled"));

        particleEffectPoolLimit = Parse.inte(_settings.get("particleEffectPoolLimit"));
        particleEffectPoolStartSize = Parse.inte(_settings.get("particleEffectPoolStartSize"));
        maxColorLookupSize = Parse.inte(_settings.get("maxColorLookupSize"));

        // Dev
        viewPaths = Parse.bool(_settings.get("viewPaths"));
        devConsoleEnabled = Parse.bool(_settings.get("devConsoleEnabled"));
        controllersEnabled = Parse.bool(_settings.get("controllersEnabled"));
    }
}