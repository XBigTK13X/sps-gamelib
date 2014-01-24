package sample;

import sps.bridge.DrawDepths;
import sps.bridge.SpriteTypes;
import sps.display.Screen;
import sps.entity.AnimatedEntity;
import sps.input.InputWrapper;

public class SamplePlayer extends AnimatedEntity {
    public SamplePlayer() {
        super(Screen.rand(10, 90, 10, 90), SpriteTypes.get("Player Stand"), DrawDepths.get("Player"));
    }

    @Override
    public void update() {
        int velX = (InputWrapper.isActive("MoveRight") ? 100 : 0) + (InputWrapper.isActive("MoveLeft") ? -100 : 0);
        int velY = (InputWrapper.isActive("MoveUp") ? 100 : 0) + (InputWrapper.isActive("MoveDown") ? -100 : 0);
        move(velX, velY);
    }
}
