package game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.simplepathstudios.gamelib.bridge.SpriteTypes;
import com.simplepathstudios.gamelib.bridge.Sps;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.core.RNG;
import com.simplepathstudios.gamelib.data.SpsConfig;
import com.simplepathstudios.gamelib.data.UserFiles;
import com.simplepathstudios.gamelib.display.Assets;
import com.simplepathstudios.gamelib.display.SpriteSheetManager;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.display.render.FrameStrategy;
import com.simplepathstudios.gamelib.input.Input;
import com.simplepathstudios.gamelib.input.InputBindings;
import com.simplepathstudios.gamelib.input.Players;
import com.simplepathstudios.gamelib.input.provider.DefaultStateProvider;

public class DummyApp {
    private ApplicationListener _appListener;
    private Application _app;

    public DummyApp(ApplicationListener sideloadedApp) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
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
        Players.init();
        Input.get().setup(new DefaultStateProvider());
        SpriteSheetManager.setup(SpriteTypes.getDefs());
        InputBindings.reload(UserFiles.input());
    }
}
