package com.simplepathstudios.gamelib.preload;

import com.simplepathstudios.gamelib.preload.gui.HeavyPreloadGui;
import com.simplepathstudios.gamelib.preload.gui.PreloadGui;

import java.util.LinkedList;

public abstract class PreloadChain implements SpsEngineChainLink {
    private LinkedList<PreloadChainLink> _preloadChain;
    private boolean processStep = false;
    private int _preloadedItemsTarget = 0;
    private int _preloadedItems = 0;
    private boolean _finished;
    private boolean _guiInitialized = false;

    private PreloadGui _gui;

    public PreloadChain(PreloadGui gui) {
        _gui = gui;
        _preloadChain = new LinkedList<>();
    }

    public PreloadChain() {
        this(new HeavyPreloadGui());
    }

    public void add(PreloadChainLink link) {
        _preloadChain.add(link);
        _preloadedItemsTarget += link.getRepetitions();
    }

    private String _lastMessage;
    private int _lastPercent;

    public void update() {
        if(!_guiInitialized){
            _gui.init();
        }
        PreloadChainLink link = _preloadChain.peek();
        if (!processStep) {
            if (link == null) {
                _finished = true;
                finish();
            }
            else {
                int percent = (int) ((_preloadedItems / (float) _preloadedItemsTarget) * 100);
                if(percent != _lastPercent){
                    _lastPercent = percent;
                    _gui.update(percent);
                }
                if (_lastMessage == null || !_lastMessage.equals(link.getMessage())) {
                    _lastMessage = link.getMessage();
                    _gui.update(_lastMessage);
                }
            }
        }
        else {
            link.process();
            link.useLink();
            if (link.allLinksRun()) {
                _preloadChain.pop();
            }
            _preloadedItems++;
        }
        processStep = !processStep;
    }

    public void draw() {
        _gui.draw();
    }

    public boolean isFinished() {
        return _finished;
    }

    public abstract void finish();
}
