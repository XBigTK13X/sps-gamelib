package com.simplepathstudios.gamelib.time;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.core.Point2;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.draw.Outline;
import com.simplepathstudios.gamelib.draw.ProcTextures;
import com.simplepathstudios.gamelib.draw.SpriteMaker;
import com.simplepathstudios.gamelib.draw.TextureManipulation;
import com.simplepathstudios.gamelib.util.Maths;

public class TimerGraphic {

    private static final Sprite[] _frames = new Sprite[101];

    private int _percent;
    private boolean _startAnimationFull;
    private Point2 _position;
    private boolean _finished;
    private boolean _visible;
    private Color _color;
    private boolean _moveable = true;
    private CoolDown _timer;

    public TimerGraphic(boolean fillUp, Point2 position, Color color, float timeInSeconds) {
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
        _timer = new CoolDown(timeInSeconds);
        _color = color;
        _startAnimationFull = !fillUp;
        _visible = true;
        reset();
        _position = position;
    }

    public void setPercent(int percent) {
        _percent = _startAnimationFull ? percent : 100 - percent;
        _percent = Maths.clamp(_percent, 0, 100);
        _finished = (_startAnimationFull ? (_percent == 100) : (_percent == 0));
    }

    public int getPercent() {
        return _startAnimationFull ? _percent : 100 - _percent;
    }

    public void reset() {
        _percent = _startAnimationFull ? 0 : 100;
        _finished = false;
    }

    public boolean isFinished() {
        return _finished;
    }

    public void setVisible(boolean visible) {
        _visible = visible;
    }


    public boolean update() {
        boolean cooled = _timer.updateAndCheck();
        setPercent(_timer.getPercentCompletion());
        return cooled;
    }

    public CoolDown getTimer() {
        setPercent(_timer.getPercentCompletion());
        return _timer;
    }

    public void draw() {
        if (_visible && _percent != 0) {
            _frames[_percent].setColor(_color.getGdxColor());
            _frames[_percent].setPosition(_position.X, _position.Y);
            Window.get(!_moveable).schedule(_frames[_percent], DrawDepths.get("TimerGraphic"));
        }
    }

    public void setMoveable(boolean moveable) {
        _moveable = moveable;
    }


    public void setPosition(Point2 position) {
        _position.reset(position.X, position.Y);
    }
}
