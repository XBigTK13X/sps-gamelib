package com.simplepathstudios.gamelib.display.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.core.Point2;
import com.simplepathstudios.gamelib.data.GameConfig;
import com.simplepathstudios.gamelib.display.Assets;
import com.simplepathstudios.gamelib.display.DrawApiCall;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.draw.DrawAPI;
import com.simplepathstudios.gamelib.particle.ParticleLease;
import com.simplepathstudios.gamelib.util.HitTest;
import com.simplepathstudios.gamelib.util.Maths;

//TODO Handle multiple shaders. ie: Text shader, sprite shader
public class ScreenRenderEngine {
    private ShaderBatch _batch;
    private OrthographicCamera _camera;
    private RenderStrategy _strategy;

    private Point2 _offset = new Point2(0, 0);

    public ScreenRenderEngine(int width, int height) {
        _batch = new ShaderBatch(Assets.get().defaultShaders());
        _strategy = new StretchStrategy();
        resize(width, height);
    }

    public void render(Sprite sprite) {
        sprite.draw(_batch);
    }

    private BitmapFont _nextToWrite;

    public void render(String content, Point2 location, Color filter, String fontLabel, int pointSize, float scale) {
        _nextToWrite = Assets.get().fontPack().getFont(fontLabel, pointSize);
        _nextToWrite.setScale(scale);
        content = content.replaceAll("\t", "    ");

        if (GameConfig.OptEnableFontOutlines) {
            //FIXME Really messy. Distance field fonts might remove the need for an outline.
            _nextToWrite.setColor(0, 0, 0, 1);

            int offset = 2;
            _nextToWrite.draw(_batch, content, location.X + offset, location.Y);
            _nextToWrite.draw(_batch, content, location.X - offset, location.Y);
            _nextToWrite.draw(_batch, content, location.X, location.Y + offset);
            _nextToWrite.draw(_batch, content, location.X, location.Y - offset);
        }

        _nextToWrite.setColor(filter.getGdxColor());
        _nextToWrite.draw(_batch, content, location.X, location.Y);
    }

    public void render(ParticleLease lease) {
        lease.Effect.draw(getBatch(), Gdx.graphics.getDeltaTime());
    }

    public void render(DrawApiCall call) {
        DrawAPI.get().setColor(call.Color);
        if (call.Radius == null) {
            DrawAPI.get().line(call.X, call.Y, call.X2, call.Y2);
        }
        else {
            DrawAPI.get().circle(call.X, call.Y, call.Radius);
        }
    }

    public void setShader(ShaderProgram shader) {
        _batch.setShader(shader);
    }

    public void setStrategy(RenderStrategy strategy) {
        _strategy = strategy;
        _camera = strategy.createCamera();
    }

    public void begin() {
        _camera.update();
        DrawAPI.get().update(_camera.combined, _batch.getProjectionMatrix());
        _strategy.begin(_camera, _batch, (int) _offset.X, (int) _offset.Y);

        _batch.begin();
        _batch.pushUniforms();
    }

    public void end() {
        _batch.end();
    }

    public void resize(int width, int height) {
        _strategy.resize(width, height);
    }

    public void moveCamera(int x, int y) {
        if (x != 0 || y != 0) {
            _offset.X = _offset.X + (int) (x * Gdx.graphics.getDeltaTime());
            _offset.Y = _offset.Y + (int) (y * Gdx.graphics.getDeltaTime());
        }
    }

    public Point2 getCameraPosition() {
        return _offset;
    }

    public boolean canMoveCamera(float x, float y) {
        float x2 = _camera.position.x + x;
        float y2 = _camera.position.y + y;
        return HitTest.inBox((int) x2, (int) y2, 0, 0, Screen.get().VirtualWidth, Screen.get().VirtualHeight);
    }

    public void setCameraX(int x) {
        _offset.setX(x);
    }

    public void setCameraY(int y) {
        _offset.setY(y);
    }

    public void resetCamera() {
        _offset.setY(0);
        _offset.setX(0);
    }

    public OrthographicCamera getCamera() {
        return _camera;
    }

    public SpriteBatch getBatch() {
        return _batch;
    }

    public Vector2 getBuffer() {
        return _strategy.getBuffer();
    }

    public void setBrightness(int brightnessPercent) {
        _batch.setBrightness(Maths.percentDecimal(brightnessPercent));
    }

    public void setContrast(int contrastPercent) {
        _batch.setContrast(Maths.percentDecimal(contrastPercent));
    }
}
