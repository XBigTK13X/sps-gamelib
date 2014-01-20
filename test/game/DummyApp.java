package game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import sps.bridge.SpriteTypes;
import sps.bridge.Sps;
import sps.color.Color;
import sps.core.RNG;
import sps.core.SpsConfig;
import sps.display.Assets;
import sps.display.SpriteSheetManager;
import sps.display.Window;
import sps.display.render.FrameStrategy;
import sps.io.DefaultStateProvider;
import sps.io.Input;

public class DummyApp {
    private ApplicationListener _appListener;
    private Application _app;

    public DummyApp(ApplicationListener sideloadedApp) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.useGL20 = true;
        cfg.width = SpsConfig.get().resolutionWidth;
        cfg.height = SpsConfig.get().resolutionHeight;
        _app = new LwjglApplication(sideloadedApp, cfg);
        _appListener = sideloadedApp;
    }

    public void create() {
        RNG.seed((int) System.currentTimeMillis());
        Sps.setup(_app, true);
        Assets.get().fontPack().setDefault("Aller/Aller_Rg.ttf", 24);
        Window.setWindowBackground(Color.BLACK);
        Window.get(false).screenEngine().setStrategy(new FrameStrategy());
        Window.get(true).screenEngine().setStrategy(new FrameStrategy());
        Input.get().setup(new DefaultStateProvider());
        SpriteSheetManager.setup(SpriteTypes.getDefs());
    }
}
