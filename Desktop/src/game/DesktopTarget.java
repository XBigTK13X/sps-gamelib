package game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sps.core.ILogger;
import sps.core.Logger;
import sps.core.SpsConfig;

public class DesktopTarget {
    public static void main(String[] args) {
        ILogger logger = new DesktopLogger();
        Logger.set(logger);
        Logger.setLogFile("game.log");
        Logger.info("Launching the main game loop");
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = SpsConfig.get().title;
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
        new LwjglApplication(new Game(logger), cfg);
    }
}
