package com.simplepathstudios.gamelib.preload.gui;

public interface PreloadGui {
    public void init();
    public void update(int percent);
    public void update(String message);
    public void draw();
}
