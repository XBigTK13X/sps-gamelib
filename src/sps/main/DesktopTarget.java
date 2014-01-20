package sps.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sps.core.Logger;
import sps.core.SpsConfig;
import sps.util.DevConfig;

import java.util.UUID;

public class DesktopTarget {
    private static LwjglApplication instance;

    private String _title;
    private GameWrapper _game;
    public DesktopTarget(String title, GameWrapper game){
        _title = title;
        _game = game;
    }

    public void start(String[] args) {
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
        instance = new LwjglApplication(_game, cfg);
    }

    public static Application get(){
        return instance;
    }
}
