package sample;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepths;
import sps.bridge.SpriteTypes;
import sps.core.Logger;
import sps.display.Screen;
import sps.display.Window;
import sps.draw.BackgroundMaker;
import sps.entity.Entities;
import sps.input.InputWrapper;
import sps.states.State;
import sps.text.TextEffects;
import sps.text.TextPool;
import sps.time.CoolDown;

public class SampleGameplay implements State {
    private CoolDown _timer;
    private Sprite _background;

    private void write() {
        TextPool.get().write("Sample Game", Screen.rand(10, 90, 10, 90), 3f, TextEffects.Fountain);
    }

    @Override
    public void create() {
        _timer = new CoolDown(.3f);
        _background = BackgroundMaker.radialDark();
        if (SpriteTypes.get("Player Stand") != null) {
            Logger.info("Player added");
            Entities.get().add(new SamplePlayer());
        }
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
