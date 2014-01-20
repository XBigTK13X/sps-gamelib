package sps.display.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import sps.console.DevConsole;
import sps.core.SpsConfig;
import sps.display.Screen;

public class StretchStrategy implements RenderStrategy {
    @Override
    public OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Screen.get().VirtualWidth, Screen.get().VirtualHeight);
        Gdx.gl.glViewport(0, 0, Screen.get().VirtualWidth, Screen.get().VirtualHeight);
        return camera;
    }

    @Override
    public void begin(OrthographicCamera camera, SpriteBatch batch, int offsetX, int offsetY) {
        camera.translate(offsetX, offsetY);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        if (SpsConfig.get().displayLoggingEnabled) {
            DevConsole.get().add("Resizing with Stretch strategy: " + width + ", " + height);
        }
    }

    @Override
    public Vector2 getBuffer() {
        return Vector2.Zero;
    }
}
