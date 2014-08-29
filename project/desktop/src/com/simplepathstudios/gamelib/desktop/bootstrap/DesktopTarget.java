package com.simplepathstudios.gamelib.desktop.bootstrap;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.data.SpsConfig;
import com.simplepathstudios.gamelib.data.DevConfig;
import com.simplepathstudios.gamelib.data.UserFiles;
import com.simplepathstudios.gamelib.preload.DelayedPreloader;
import com.simplepathstudios.gamelib.states.StateResolver;

import java.util.UUID;

public class DesktopTarget {
    private static LwjglApplication __instance;

    public static Application get() {
        return __instance;
    }

    public static void start(String title, DelayedPreloader preloader, StateResolver resolver, String[] args) {
        UserFiles.setGameId(title);
        GameWrapper wrapper = new GameWrapper(preloader, resolver);
        DesktopTarget target = new DesktopTarget(title, wrapper);
        target.start(args);
    }

    private String _title;
    private GameWrapper _game;

    private DesktopTarget(String title, GameWrapper game) {
        _title = title;
        _game = game;
    }

    private void start(String[] args) {
        if (args.length > 0) {
            for (String s : args) {
                if (s.equalsIgnoreCase("--play-as-bot")) {
                    DevConfig.BotEnabled = true;
                }
            }
        }
        if (DevConfig.BotEnabled) {
            Logger.setLogFile("bot-" + UUID.randomUUID().toString() + ".log");
        }
        else {
            Logger.setLogFile("game.log");
        }

        Logger.info("Launching the main game loop");
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = _title;
        if (SpsConfig.get().fullScreen) {
            cfg.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
            cfg.fullscreen = SpsConfig.get().fullScreen;
        }
        else {
            cfg.width = SpsConfig.get().resolutionWidth;
            cfg.height = SpsConfig.get().resolutionHeight;
        }
        cfg.vSyncEnabled = SpsConfig.get().vSyncEnabled;
        __instance = new LwjglApplication(_game, cfg);
    }
}
