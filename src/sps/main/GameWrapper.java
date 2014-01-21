package sps.main;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import sps.audio.MusicPlayer;
import sps.audio.SoundPlayer;
import sps.core.Logger;
import sps.core.SpsConfig;
import sps.core.SpsEngineChain;
import sps.display.Window;
import sps.data.Options;
import sps.preload.*;
import sps.states.GlobalStateResolver;
import sps.states.StateManager;
import sps.states.StateResolver;
import sps.time.CoolDown;

public class GameWrapper implements ApplicationListener {
    private SpsEngineChain _engineChain;

    private CoolDown _persistResizeOperation;
    private boolean _firstResizeCall = true;

    public GameWrapper(final DelayedPreloader delayedPreloader, final StateResolver resolver) {
        PreloadChain kickstart = new PreloadChain(false) {
            @Override
            public void finish() {

            }
        };
        kickstart.add(new PreloadChainLink("Launching initial game state.") {
            @Override
            public void process() {
                GlobalStateResolver.set(resolver);
                StateManager.get().push(GlobalStateResolver.get().createInitial());
            }
        });

        _engineChain = new SpsEngineChain();
        _engineChain.add(new SpsBootstrap());
        _engineChain.add(SpsInitializer.getChain());
        _engineChain.add(delayedPreloader);
        _engineChain.add(kickstart);
        _engineChain.add(new LoadedGame());
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
            if (_engineChain.isLinkAvailable()) {
                _engineChain.update();
                _engineChain.draw();
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
