package com.simplepathstudios.gamelib.desktop.bootstrap;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.simplepathstudios.gamelib.audio.MusicPlayer;
import com.simplepathstudios.gamelib.audio.SoundPlayer;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.data.SpsConfig;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.preload.DelayedPreloader;
import com.simplepathstudios.gamelib.preload.PreloadChain;
import com.simplepathstudios.gamelib.preload.PreloadChainLink;
import com.simplepathstudios.gamelib.preload.SpsEngineChain;
import com.simplepathstudios.gamelib.preload.gui.LoggerPreloadGui;
import com.simplepathstudios.gamelib.states.GlobalStateResolver;
import com.simplepathstudios.gamelib.states.StateManager;
import com.simplepathstudios.gamelib.states.StateResolver;
import com.simplepathstudios.gamelib.time.CoolDown;

public class GameWrapper implements ApplicationListener {
    private SpsEngineChain _engineChain;

    private CoolDown _persistResizeOperation;
    private boolean _firstResizeCall = true;

    public GameWrapper(final DelayedPreloader delayedPreloader, final StateResolver resolver) {
        PreloadChain kickstart = new PreloadChain(new LoggerPreloadGui()) {
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
        GdxNativesLoader.load();
        _engineChain = new SpsEngineChain();
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
            if (SpsConfig.get().displayLogging) {
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
                SpsConfig.get().resolutionWidth = Window.Width;
                SpsConfig.get().resolutionHeight = Window.Height;
                SpsConfig.getInstance().apply();
                SpsConfig.getInstance().save();
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
