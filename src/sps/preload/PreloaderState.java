package sps.preload;

import sps.preload.PreloadChain;
import sps.states.State;

public abstract class PreloaderState implements State {
    protected PreloadChain _preloadChain;

    public PreloaderState() {
    }

    @Override
    public void create() {
        _preloadChain = new PreloadChain() {
            @Override
            public void finish() {
                onFinish();
            }
        };
        onCreate();
    }

    @Override
    public void draw() {
        _preloadChain.draw();
    }

    @Override
    public void update() {
        _preloadChain.update();
    }

    @Override
    public void asyncUpdate() {

    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void pause() {

    }

    public abstract void onFinish();

    public abstract void onCreate();
}
