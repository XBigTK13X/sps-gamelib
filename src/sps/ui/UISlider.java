package sps.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.color.Color;
import sps.core.Point2;
import sps.io.Input;

public abstract class UISlider {
    private Meter _meter;
    private UIButton _knob;
    private int _height;
    private int _width;
    private Point2 _position;
    private int _percent;

    public UISlider(int widthPercent, int heightPercent, int x, int y) {
        _meter = new Meter(widthPercent, heightPercent, Color.WHITE, new Point2(x, y), false);
        _width = _meter.getBounds().Width;
        _height = _meter.getBounds().Height;
        _position = new Point2(x, y);

        _knob = new UIButton("") {
            @Override
            public void click() {
            }

            @Override
            public void mouseDown() {

            }
        };
        _knob.setBackgroundColors(Color.WHITE, Color.BLACK);
        _knob.setSize(1, heightPercent);
        _knob.setXY((int) _position.X - _width / 2, (int) _position.Y);

        Buttons.User buttonUser = new Buttons.User() {
            @Override
            public Sprite getSprite() {
                return _meter.getBackground();
            }

            @Override
            public void onClick() {
            }

            @Override
            public void onMouseDown() {
                if (isBeingClicked()) {
                    if (Input.get().x() >= _position.X && Input.get().x() <= _position.X + _width) {
                        _knob.setXY(Input.get().x() - _knob.getWidth() / 2, (int) _knob.getPosition().Y);
                        onSlide();
                    }
                }
            }
        };
        buttonUser.setShouldDraw(false);
        Buttons.get().add(buttonUser);
    }

    public abstract void onSlide();

    public void draw() {
        _meter.draw();
        _knob.draw();
    }

    public int getSliderPercent() {
        return (int) (((_knob.getPosition().X - _position.X) / (float) _width) * 100);
    }

    public void setSliderPercent(int percent) {
        _percent = percent;
        _knob.setXY((int) (_position.X + (_width * (percent / 100f))) - _knob.getWidth() / 2, (int) _knob.getPosition().Y);
    }
}
