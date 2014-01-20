package sps.pregame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepths;
import sps.display.Window;
import sps.states.State;

public class OptionsState implements State {
    protected Sprite _background;

    public OptionsState(Sprite background) {
        _background = background;
    }

    @Override
    public void create() {

    }

    @Override
    public void draw() {
        Window.get().schedule(_background, DrawDepths.get("GameBackground"));
    }

    @Override
    public void update() {

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
        return "Options";
    }

    @Override
    public void pause() {

    }
}
