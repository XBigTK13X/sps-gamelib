package sps.draw;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import sps.color.Color;

public class DrawAPI {
    private static DrawAPI __instance;

    public static DrawAPI get() {
        if (__instance == null) {
            __instance = new DrawAPI();
        }
        return __instance;
    }

    private ShapeRenderer _renderer;

    private DrawAPI() {
        _renderer = new ShapeRenderer();
        setColor(Color.WHITE);
    }

    public void setColor(Color color) {
        _renderer.setColor(color.getGdxColor());
    }

    public void update(Matrix4 transform, Matrix4 projection) {
        _renderer.setTransformMatrix(transform);
        _renderer.setProjectionMatrix(projection);
    }

    public void line(float x, float y, float x2, float y2) {
        _renderer.begin(ShapeRenderer.ShapeType.Line);
        _renderer.identity();
        _renderer.line(x, y, x2, y2);
        _renderer.end();
    }

    public void circle(float x, float y, float radius) {
        _renderer.begin(ShapeRenderer.ShapeType.Filled);
        _renderer.identity();
        _renderer.translate(x, y, 0);
        _renderer.circle(0, 0, radius);
        _renderer.end();
    }
}


