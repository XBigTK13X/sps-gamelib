package sps.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sps.core.Logger;
import sps.core.SpsConfig;
import sps.data.UserFiles;
import sps.preload.DelayedPreloader;
import sps.states.StateResolver;
import sps.data.DevConfig;

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
        cfg.useGL20 = true;
        __instance = new LwjglApplication(_game, cfg);
    }
}
