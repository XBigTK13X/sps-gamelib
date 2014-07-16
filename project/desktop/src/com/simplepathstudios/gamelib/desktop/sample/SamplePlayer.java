package com.simplepathstudios.gamelib.desktop.sample;

import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.SpriteManager;
import com.simplepathstudios.gamelib.entity.AnimatedEntity;
import com.simplepathstudios.gamelib.input.InputWrapper;

public class SamplePlayer extends AnimatedEntity {
    public SamplePlayer() {
        super(Screen.rand(10, 90, 10, 90), SpriteManager.get("p1.walk"), DrawDepths.get("Player"));

    }

    @Override
    public void update() {
        int velX = (InputWrapper.isActive("MoveRight") ? 100 : 0) + (InputWrapper.isActive("MoveLeft") ? -100 : 0);
        int velY = (InputWrapper.isActive("MoveUp") ? 100 : 0) + (InputWrapper.isActive("MoveDown") ? -100 : 0);
        move(velX, velY);
    }
}
