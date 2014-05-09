package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.input.Input;
import com.simplepathstudios.gamelib.input.InputWrapper;
import com.simplepathstudios.gamelib.particle.ParticleWrapper;

public class ParticleChecker implements ApplicationListener {
    private static DummyApp _context;

    public static void main(String[] args) {
        _context = new DummyApp(new ParticleChecker());
    }

    public ParticleChecker() {
    }

    @Override
    public void create() {
        _context.create();
    }

    @Override
    public void resize(int i, int i2) {
    }

    @Override
    public void render() {
        Input.get().update();

        if (InputWrapper.isActive("Confirm")) {
            Logger.info("Emitting...");
            ParticleEffect effect = ParticleWrapper.get().emit("vaporize", Screen.pos(30, 30));
            ParticleWrapper.setSquareBounds(effect, 50);
            effect.start();
        }

        ParticleWrapper.get().update();
        ParticleWrapper.get().draw();

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
