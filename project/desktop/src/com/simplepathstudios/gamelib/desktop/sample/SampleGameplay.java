package com.simplepathstudios.gamelib.desktop.sample;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.bridge.SpriteTypes;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.draw.BackgroundMaker;
import com.simplepathstudios.gamelib.entity.Entities;
import com.simplepathstudios.gamelib.input.InputWrapper;
import com.simplepathstudios.gamelib.states.State;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.TextEffects;
import com.simplepathstudios.gamelib.text.TextPool;
import com.simplepathstudios.gamelib.time.CoolDown;

public class SampleGameplay implements State {
    private CoolDown _timer;
    private Sprite _background;

    private void write() {
        Systems.get(TextPool.class).write("Sample Game", Screen.rand(10, 90, 10, 90), 3f, TextEffects.Fountain);
    }

    @Override
    public void create() {
        _timer = new CoolDown(.3f);
        _background = BackgroundMaker.radialDark();
        Systems.get(Entities.class).add(new SamplePlayer());
    }

    @Override
    public void draw() {
        Window.get().schedule(_background, DrawDepths.get("Default"));
    }

    @Override
    public void update() {
        if (_timer.updateAndCheck()) {
            write();
        }
        if (InputWrapper.isActive("Confirm")) {
            write();
        }
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
        return null;
    }

    @Override
    public void pause() {

    }
}
