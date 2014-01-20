package sample.states;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.Commands;
import sps.bridge.DrawDepths;
import sps.display.Screen;
import sps.display.Window;
import sps.draw.BackgroundCache;
import sps.io.Input;
import sps.states.State;
import sps.text.TextEffects;
import sps.text.TextPool;
import sps.util.CoolDown;

public class SampleGameplay implements State {
    private CoolDown _timer;
    private Sprite _background;

    private void write() {
        TextPool.get().write("Sample Game", Screen.rand(10, 90, 10, 90), 3f, TextEffects.Fountain);
    }

    @Override
    public void create() {
        _timer = new CoolDown(.3f);
        _background = BackgroundCache.getRandom();

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
        if (Input.get().isActive(Commands.get("Confirm"))) {
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
