package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import sps.core.Logger;
import sps.display.Screen;
import sps.display.Window;
import sps.input.Input;
import sps.input.KeyCatcher;
import sps.input.Keys;
import sps.states.Systems;
import sps.text.Text;
import sps.text.TextPool;

public class KeyboardTest implements ApplicationListener {
    private static DummyApp _context;

    public static void main(String[] args) {
        _context = new DummyApp(new KeyboardTest());
    }

    private Text _display;
    private KeyCatcher _catcher;

    @Override
    public void create() {
        _context.create();
        _display = Systems.get(TextPool.class).write("Loaded", Screen.pos(5, 25));
        _catcher = new KeyCatcher() {
            @Override
            public void onDown(int keyCode) {
                _display.setMessage("Down: " + keyCode + ", " + Keys.fromCode(keyCode));
                Logger.info("Down: " + keyCode + ", " + Keys.fromCode(keyCode));
            }
        };
        _catcher.setActive(true);
    }

    @Override
    public void resize(int i, int i2) {

    }

    @Override
    public void render() {
        Input.get().update();
        Systems.get(TextPool.class).update();
        Systems.get(TextPool.class).draw();
        //Macair screen was garbled without this clearing
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Window.processDrawCalls();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
