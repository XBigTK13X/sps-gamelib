package game.states;

import sps.core.Logger;
import sps.display.Screen;
import sps.states.State;
import sps.text.TextPool;

public class GameLoop implements State {
    @Override
    public void create() {
        TextPool.get().write("Hello World!", Screen.pos(50, 50));
    }

    @Override
    public void draw() {
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
        return null;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }
}
