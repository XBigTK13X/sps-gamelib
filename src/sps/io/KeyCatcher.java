package sps.io;

import com.badlogic.gdx.InputProcessor;
import sps.bridge.Sps;

public abstract class KeyCatcher implements InputProcessor {
    private InputProcessor originalInputProcessor;
    private boolean _active;

    public boolean isActive() {
        return _active;
    }

    public void setActive(boolean active) {
        if (active != _active) {
            if (active) {
                Input.disable();
                originalInputProcessor = Sps.getApp().getInput().getInputProcessor();
                Sps.getApp().getInput().setInputProcessor(this);
            }
            else {
                Sps.getApp().getInput().setInputProcessor(originalInputProcessor);
                Input.enable();
            }
        }
        _active = active;
    }

    public abstract void onDown(int keyCode);

    public void onUp(int keyCode) {
    }

    @Override
    public boolean keyDown(int i) {
        onDown(i);
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        onUp(i);
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i2, int i3, int i4) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i2, int i3) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i2) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }
}
