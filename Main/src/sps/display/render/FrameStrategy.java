package sps.display.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import sps.display.Screen;
import sps.display.render.RenderStrategy;

public class FrameStrategy implements RenderStrategy {
    private Rectangle viewport;

    @Override
    public OrthographicCamera createCamera() {
        viewport = new Rectangle(0, 0, 0, 0);
        return new OrthographicCamera(Screen.get().VirtualWidth, Screen.get().VirtualHeight);
    }

    @Override
    public void begin(OrthographicCamera camera, SpriteBatch batch, int offsetX, int offsetY) {
        camera.setToOrtho(false, Screen.get().VirtualWidth, Screen.get().VirtualHeight);
        camera.translate(offsetX, offsetY);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    Vector2 crop = new Vector2(0, 0);

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        float scale = (float) width / (float) Screen.get().VirtualWidth;
        crop.set(0, 0);

        if (aspectRatio > Screen.get().VirtualAspectRatio) {
            scale = (float) height / (float) Screen.get().VirtualHeight;
            crop.x = (width - Screen.get().VirtualWidth * scale) / 2f;
        }
        else
            if (aspectRatio < Screen.get().VirtualAspectRatio) {
                scale = (float) width / (float) Screen.get().VirtualWidth;
                crop.y = (height - Screen.get().VirtualHeight * scale) / 2f;
            }

        float w = (float) Screen.get().VirtualWidth * scale;
        float h = (float) Screen.get().VirtualHeight * scale;
        viewport.set(crop.x, crop.y, w, h);
        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
    }

    @Override
    public Vector2 getBuffer() {
        return crop;
    }
}
