package sps.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import sps.bridge.DrawDepth;
import sps.bridge.DrawDepths;
import sps.color.Color;
import sps.core.Point2;
import sps.display.Assets;
import sps.display.Window;
import sps.states.State;
import sps.states.StateManager;

public class Text {
    public static final float NotTimed = Float.MIN_VALUE;

    private State _createdDuring;

    private boolean _canMove;
    private Point2 _position;
    private String _message;
    private float _scale;
    private boolean _visible = false;
    private float _lifeInSeconds;
    private TextEffect _effect;
    private float _xVel;
    private float _yVel;
    private float _dX;
    private float _dY;
    private Color _color;
    private DrawDepth _depth;

    private String _fontLabel;
    private int _fontPointSize;

    private BitmapFont.TextBounds _bounds;

    public Text() {
        _color = Color.WHITE;
        _position = new Point2(0, 0);
        _canMove = true;
    }

    public void reset(Point2 position, String message, String fontLabel, int fontPointSize, float scale, float lifeInSeconds, TextEffect effect) {
        _canMove = true;
        _createdDuring = StateManager.get().current();
        _position.copy(position);
        _scale = scale;
        _visible = true;
        _lifeInSeconds = lifeInSeconds;
        _effect = effect;
        _fontLabel = fontLabel;
        _fontPointSize = fontPointSize;
        _depth = DrawDepths.get("DefaultText");
        setMessage(message);
        effect.init(this);
    }

    public void hide() {
        _visible = false;
    }

    public void setVisible(boolean visible) {
        this._visible = visible;
    }

    public void update() {
        if (_lifeInSeconds != NotTimed && (_position.X != 0 || _position.Y != 0)) {
            _effect.update(this);
            _lifeInSeconds -= Gdx.graphics.getDeltaTime();
            if (_lifeInSeconds <= 0) {
                _visible = false;
            }
        }
    }

    public void draw() {
        Window.get(!_canMove).schedule(_message, _position, _color, _fontLabel, _fontPointSize, _scale, _depth);
    }

    public boolean isVisible() {
        return _visible && isCurrent();
    }

    public void setVel(float x, float y) {
        _xVel = x;
        _yVel = y;
    }

    public void accel() {
        _xVel += _dX;
        _yVel += _dY;
        _position.reset(_position.X + _xVel, _position.Y + _yVel);
    }

    public void setAccel(float dX, float dY) {
        this._dX = dX;
        this._dY = dY;
    }

    public void setColor(Color color) {
        _color = color;
    }

    public void setScale(float scale) {
        this._scale = scale;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
        recalcBounds();
    }

    public BitmapFont.TextBounds getBounds() {
        return _bounds;
    }

    public boolean createdDuring(State state) {
        return _createdDuring == state;
    }

    public boolean isCurrent() {
        return _createdDuring == StateManager.get().current();
    }

    public void setPosition(int x, int y) {
        _position.reset(x, y);
    }

    public void setMoveable(boolean moveable) {
        _canMove = moveable;
    }

    public void setFont(String fontLabel, int pointSize) {
        _fontPointSize = pointSize;
        _fontLabel = fontLabel;
        recalcBounds();
    }

    private void recalcBounds() {
        _bounds = Assets.get().fontPack().getFont(_fontLabel, _fontPointSize).getMultiLineBounds(_message);
    }

    public void setDepth(DrawDepth depth) {
        _depth = depth;
    }
}
