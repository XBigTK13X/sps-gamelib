package sps.main;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import sps.io.Options;
import sps.audio.MusicPlayer;
import sps.audio.SoundPlayer;
import sps.core.Logger;
import sps.core.SpsConfig;
import sps.display.Window;
import sps.util.CoolDown;

public class GameWrapper implements ApplicationListener {
    private SpsLoader _loader;
    private SpsGame _game;
    private CoolDown _persistResizeOperation;
    private boolean _firstResizeCall = true;

    public GameWrapper(SpsLoader loader, SpsGame game) {
        _loader = loader;
        _game = game;
    }

    @Override
    public void create() {
        _persistResizeOperation = new CoolDown(.1f);
        _persistResizeOperation.zeroOut();
    }

    @Override
    public void resize(int width, int height) {
        if (_firstResizeCall) {
            if (SpsConfig.get().displayLoggingEnabled) {
                Logger.info("Libgdx overrides the resolution set in config. Ignoring that resize call");
            }
            _firstResizeCall = false;
            return;
        }
        if (width != Window.Width || height != Window.Height) {
            _persistResizeOperation.reset();
            Window.resize(width, height, Gdx.graphics.isFullscreen());
        }
    }

    private void handleWindowQuirks() {
        if (!_persistResizeOperation.isCooled()) {
            if (_persistResizeOperation.updateAndCheck()) {
                Options.get().WindowResolutionX = Window.Width;
                Options.get().WindowResolutionY = Window.Height;
                Options.get().apply();
                Options.get().save();
                _persistResizeOperation.zeroOut();
            }
        }
    }

    @Override
    public void render() {
        try {
            handleWindowQuirks();
            if (_loader.isFinished()) {
                _game.update();
                _game.draw();
            }
            else {
                _loader.update();
                _loader.draw();
            }
        }
        catch (Exception e) {
            Logger.exception(e);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        SoundPlayer.get().dispose();
        MusicPlayer.get().dispose();
    }
}
