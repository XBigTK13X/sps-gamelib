package com.simplepathstudios.gamelib.preload.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.simplepathstudios.gamelib.core.Logger;
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
        _logo.setPosition(300, 300);
    }

    @Override
    public void update(int percent) {
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
        _text.draw(_batch, _message, 200, 100);
        _logo.draw(_batch);
        _batch.end();
    }
}
