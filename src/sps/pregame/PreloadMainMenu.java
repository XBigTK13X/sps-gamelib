package sps.pregame;

import sps.draw.BackgroundCache;
import sps.preload.PreloaderState;
import sps.io.Persistence;
import org.apache.commons.io.FileUtils;
import sps.core.Loader;
import sps.core.Logger;
import sps.draw.SpriteMaker;
import sps.preload.PreloadChainLink;
import sps.states.StateManager;

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
                _payload.SaveFilePresent = Persistence.get().saveFileExists();
            }
        });
        _preloadChain.add(new PreloadChainLink("Preparing the logo.") {
            @Override
            public void process() {
                _payload.Logo = SpriteMaker.fromGraphic("game_logo.png");
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
