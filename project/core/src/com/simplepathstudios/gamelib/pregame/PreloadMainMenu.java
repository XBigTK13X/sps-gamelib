package com.simplepathstudios.gamelib.pregame;

import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.data.Persistence;
import com.simplepathstudios.gamelib.draw.BackgroundCache;
import com.simplepathstudios.gamelib.draw.ProcTextures;
import com.simplepathstudios.gamelib.draw.SpriteMaker;
import com.simplepathstudios.gamelib.preload.PreloadChainLink;
import com.simplepathstudios.gamelib.preload.PreloaderState;
import com.simplepathstudios.gamelib.states.StateManager;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class PreloadMainMenu extends PreloaderState {
    private MainMenuPayload _payload;

    @Override
    public void onFinish() {
        StateManager.reset().push(new MainMenu(_payload));
    }

    @Override
    public void onCreate() {
        _payload = new MainMenuPayload();
        _preloadChain.add(new PreloadChainLink("Determining game version.") {
            @Override
            public void process() {
                _payload.Version = "Unknown";
                File versionDat = Loader.get().data("version.dat");
                if (versionDat.exists()) {
                    try {
                        _payload.Version = FileUtils.readFileToString(versionDat);
                    }
                    catch (Exception e) {
                        Logger.exception(e, false);
                    }
                }
            }
        });
        _preloadChain.add(new PreloadChainLink("Checking for existing save file.") {
            @Override
            public void process() {
                if (Persistence.get() != null) {
                    _payload.SaveFilePresent = Persistence.get().saveFileExists();
                }
            }
        });
        _preloadChain.add(new PreloadChainLink("Preparing the logo.") {
            @Override
            public void process() {
                if (Loader.get().graphics("game_logo.png").exists()) {
                    _payload.Logo = SpriteMaker.fromGraphic("game_logo.png");
                }
                else {
                    _payload.Logo = SpriteMaker.fromColors(ProcTextures.gradient(300, 100, Color.BLACK, Color.WHITE, false));
                }
            }
        });

        _preloadChain.add(new PreloadChainLink("Generating background image.") {
            @Override
            public void process() {
                _payload.Background = BackgroundCache.createMenuBackground();
            }
        });
    }
}
