package com.simplepathstudios.gamelib.preload.gui;

import com.simplepathstudios.gamelib.core.Logger;

public class LoggerPreloadGui implements PreloadGui {
    @Override
    public void init() {

    }

    @Override
    public void update(int percent) {

    }

    @Override
    public void update(String message) {
        Logger.info(message);
    }

    @Override
    public void draw() {

    }
}
