package sps.util;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepths;
import sps.color.Color;
import sps.core.Point2;
import sps.display.Screen;
import sps.display.Window;
import sps.draw.Outline;
import sps.draw.ProcTextures;
import sps.draw.SpriteMaker;
import sps.draw.TextureManipulation;
import sps.util.Maths;

public class TimerGraphic {

    private static final Sprite[] _frames = new Sprite[101];

    private int _percent;
    private boolean _fillUp;
    private Point2 _position;
    private boolean _finished;
    private boolean _visible;
    private Color _color;
    private boolean _moveable = true;


    public TimerGraphic(boolean fillUp, Point2 position, Color color) {
        if (_frames[0] == null) {
            int radiusPixels = (int) Screen.width(5);
            Color[][] base;
            for (int ii = 0; ii <= 100; ii++) {
                float rotationMax = Maths.percentToValue(0, 360, ii);
                float c = .2f;
                base = ProcTextures.centeredCircleSegment(0, radiusPixels, 0, (int) rotationMax, new Color(c, c, c, 1), new Color(1 - c, 1 - c, 1 - c, 1));
                Outline.single(base, Color.WHITE, 2);
                base = TextureManipulation.blurStack(base, 2);
                _frames[ii] = SpriteMaker.fromColors(base);
                _frames[ii].setRotation(-90);
            }
        }
        _color = color;
        _fillUp = fillUp;
        _visible = true;
        reset();
        _position = position;
    }

    public void setPercent(int percent) {
        _percent = _fillUp ? percent : 100 - percent;
        _percent = Maths.clamp(_percent, 0, 100);
        _finished = (_fillUp ? (_percent == 100) : (_percent == 0));
    }

    public int getPercent() {
        return _fillUp ? _percent : 100 - _percent;
    }

    public void reset() {
        _percent = _fillUp ? 0 : 100;
        _finished = false;
    }

    public boolean isFinished() {
        return _finished;
    }

    public void setVisible(boolean visible) {
        _visible = visible;
    }


    public void draw() {
        if (_visible && _percent != 0) {
            _frames[_percent].setColor(_color.getGdxColor());
            _frames[_percent].setPosition(_position.X, _position.Y);
            Window.get(!_moveable).schedule(_frames[_percent], DrawDepths.get("BattleCoolDown"));
        }
    }

    public void setMoveable(boolean moveable) {
        _moveable = moveable;
    }


    public void setPosition(Point2 position) {
        _position.reset(position.X, position.Y);
    }
}
