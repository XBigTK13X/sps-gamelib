package com.simplepathstudios.gamelib.preload.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.data.SpsConfig;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.draw.SpriteMaker;

public class LightPreloadGui implements PreloadGui {
    BitmapFont _text;
    SpriteBatch _batch;
    String _message;
    Sprite _logo;
    float _logoRotation;

    @Override
    public void init() {
        _text = new BitmapFont();
        _batch = new SpriteBatch();
        _logo = SpriteMaker.fromGraphic("sps-gamelib-logo.png");
    }

    @Override
    public void update(int percent) {
        _logo.setPosition(SpsConfig.get().resolutionWidth / 2 - _logo.getWidth() / 2, SpsConfig.get().resolutionHeight / 2 - _logo.getHeight() / 2);
    }

    @Override
    public void update(String message) {
        _message = message;
        Logger.info(message);
    }

    @Override
    public void draw() {
        Window.clear();
        _batch.begin();
        _text.draw(_batch, _message, SpsConfig.get().resolutionWidth / 2 - _logo.getWidth() / 2, SpsConfig.get().resolutionHeight / 3);
        _logo.draw(_batch);
        _batch.end();
    }
}
