package sps.display.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface RenderStrategy {
    public OrthographicCamera createCamera();

    public void begin(OrthographicCamera camera, SpriteBatch batch, int offsetX, int offsetY);

    public void resize(int width, int height);

    public Vector2 getBuffer();
}
