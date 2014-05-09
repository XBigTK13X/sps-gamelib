package com.simplepathstudios.gamelib.preload;

import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.TextPool;

public abstract class DelayedPreloader implements SpsEngineChainLink {
    private PreloadChain _chain;

    @Override
    public void update() {
        if (_chain == null) {
            _chain = createChain();
        }
        _chain.update();
    }

    @Override
    public void draw() {
        if (_chain == null) {
            return;
        }
        _chain.draw();
        Systems.get(TextPool.class).draw();
        Window.processDrawCalls();
    }

    @Override
    public boolean isFinished() {
        if (_chain == null) {
            return false;
        }
        return _chain.isFinished();
    }

    public abstract PreloadChain createChain();
}
