package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import sps.display.Screen;
import sps.display.Window;
import sps.io.Input;
import sps.io.KeyCatcher;
import sps.io.Keys;
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
        _display = TextPool.get().write("Loaded", Screen.pos(5, 25));
        _catcher = new KeyCatcher() {
            @Override
            public void onDown(int keyCode) {
                _display.setMessage("Down: " + keyCode + ", " + Keys.find(keyCode));
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
        TextPool.get().update();
        TextPool.get().draw();
        //Macair screen was garbled without this clearing
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Window.get().processScheduledApiCalls();
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
