package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import sps.bridge.Commands;
import sps.core.Logger;
import sps.display.Screen;
import sps.display.Window;
import sps.io.Input;
import sps.particles.ParticleWrapper;

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

        if (Input.get().isActive(Commands.get("Confirm"))) {
            Logger.info("Emitting...");
            ParticleEffect effect = ParticleWrapper.get().emit("vaporize", Screen.pos(30, 30));
            ParticleWrapper.setSquareBounds(effect, 50);
            effect.start();
        }

        ParticleWrapper.get().update();
        ParticleWrapper.get().draw();

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
