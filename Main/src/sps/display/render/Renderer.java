package sps.display.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import sps.bridge.DrawDepth;
import sps.core.Point2;
import sps.core.SpsConfig;
import sps.display.Assets;
import sps.display.DrawAPICall;
import sps.display.Screen;
import sps.draw.DrawAPI;
import sps.entities.HitTest;

import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private List<RenderCall> _todo;
    private boolean _queueListening = false;

    private List<DrawAPICall> _drawApiCalls;

    private SpriteBatch _batch;
    private OrthographicCamera _camera;
    private RenderStrategy _strategy;
    private Point2 _offset = new Point2(0, 0);


    public Renderer(int width, int height) {
        _drawApiCalls = new ArrayList<>();
        _todo = new ArrayList<>();
        _batch = new SpriteBatch();
        _strategy = new StretchStrategy();
        resize(width, height);
        setShader(Assets.get().defaultShaders());
    }

    public void setShader(ShaderProgram shader) {
        _batch.setShader(shader);
    }

    public void setStrategy(RenderStrategy strategy) {
        this._strategy = strategy;
        _camera = strategy.createCamera();

    }

    public void begin() {
        _camera.update();
        DrawAPI.get().update(_camera.combined, _batch.getProjectionMatrix());
        _strategy.begin(_camera, _batch, (int) _offset.X, (int) _offset.Y);
        _batch.begin();
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

    public void setListening(boolean listening) {
        _queueListening = listening;
    }

    public void processDelayedCalls() {
        //Delayed renders
        setListening(false);
        begin();
        for (RenderCall command : _todo) {
            if (command.Sprite != null) {
                render(command.Sprite, command.Location, command.Filter, command.Width, command.Height, command.ScaleX, command.ScaleY);
            }
            if (command.Content != null) {
                render(command.Content, command.Location, command.Filter, command.FontLabel, command.PointSize, command.Scale);
            }
        }
        end();
        _todo.clear();
        setListening(true);
    }

    public void processDrawAPICalls() {
        //Draw API calls
        for (DrawAPICall call : _drawApiCalls) {
            DrawAPI.get().setColor(call.Color);
            if (call.Radius == null) {
                DrawAPI.get().line(call.X, call.Y, call.X2, call.Y2);
            }
            else {
                DrawAPI.get().circle(call.X, call.Y, call.Radius);
            }
        }
        _drawApiCalls.clear();
    }

    public void schedule(DrawAPICall apiCall) {
        _drawApiCalls.add(apiCall);
    }


    // Sprite rendering
    public void draw(Sprite sprite, Point2 position, DrawDepth depth, Color color) {
        render(sprite, position, color, SpsConfig.get().spriteWidth, SpsConfig.get().spriteHeight, 1, 1);
    }

    public void draw(Sprite sprite, Point2 position, DrawDepth depth, Color color, boolean flipX, boolean flipY) {
        render(sprite, position, color, sprite.getWidth(), sprite.getHeight(), flipX ? -1 : 1, flipY ? -1 : 1);
    }

    public void draw(Sprite sprite, Point2 position, DrawDepth depth, Color color, float width, float height) {
        render(sprite, position, color, width, height, 1, 1);
    }

    Point2 pos = new Point2(0, 0);

    public void draw(Sprite sprite) {
        pos.reset(sprite.getX(), sprite.getY());
        render(sprite, pos, sprite.getColor(), sprite.getWidth(), sprite.getHeight(), 1, 1);
    }

    private void render(Sprite sprite, Point2 position, Color color, float width, float height, float scaleX, float scaleY) {
        if (_queueListening) {
            _todo.add(new RenderCall(sprite, position, color, width, height, scaleX, scaleY));
        }
        else {
            sprite.setColor(color);
            sprite.setSize(width, height);
            sprite.setScale(scaleX, scaleY);
            sprite.setPosition(position.X, position.Y);
            sprite.draw(_batch);
        }

    }

    // String rendering
    public void draw(String content, Point2 location, Color filter, String fontLabel, int pointSize, float scale) {
        if (content.contains("\n")) {
            int line = 0;
            if (pointSize == 0) {
                pointSize = Assets.get().fontPack().getDefaultPointSize();
            }
            for (String s : content.split("\n")) {
                render(s, location.add(0, line++ * -pointSize), filter, fontLabel, pointSize, scale);
            }
        }
        else {
            render(content, location, filter, fontLabel, pointSize, scale);
        }
    }

    private BitmapFont _nextToWrite;

    private void render(String content, Point2 location, Color filter, String fontLabel, int pointSize, float scale) {
        if (_queueListening) {
            _todo.add(new RenderCall(content, location, filter, fontLabel, pointSize, scale));
        }
        else {
            _nextToWrite = Assets.get().fontPack().getFont(fontLabel, pointSize);
            _nextToWrite.setScale(scale);

            if (SpsConfig.get().optEnableFontOutlines) {
                //FIXME Really messy. Distance field fonts might remove the need for an outline.
                _nextToWrite.setColor(0, 0, 0, 1);

                int offset = 2;
                _nextToWrite.draw(_batch, content, location.X + offset, location.Y);
                _nextToWrite.draw(_batch, content, location.X - offset, location.Y);
                _nextToWrite.draw(_batch, content, location.X, location.Y + offset);
                _nextToWrite.draw(_batch, content, location.X, location.Y - offset);
            }

            _nextToWrite.setColor(filter);
            _nextToWrite.draw(_batch, content, location.X, location.Y);
        }
    }
}
