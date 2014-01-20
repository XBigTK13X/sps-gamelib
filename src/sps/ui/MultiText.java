package sps.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepth;
import sps.bridge.DrawDepths;
import sps.color.Color;
import sps.core.Point2;
import sps.display.Window;
import sps.draw.Outline;
import sps.draw.ProcTextures;
import sps.draw.SpriteMaker;
import sps.text.Text;
import sps.text.TextPool;

public class MultiText {
    public static void setDefaultFont(String fontLabel, int pointSize) {
        __defaultFontLabel = fontLabel;
        __defaultFontPointSize = pointSize;
    }

    private static String __defaultFontLabel = "Default";
    private static int __defaultFontPointSize = 24;

    private int _messageLimit;
    private final Text[] _contents;
    private String _fontLabel;
    private int _pointSize;
    private Sprite _background;
    private boolean _active;
    private int _index = 0;
    private int _width;
    private int _height;
    private Point2 _position;
    private boolean _doubleSpaced;

    private DrawDepth _textDepth;
    private DrawDepth _backgroundDepth;

    public MultiText(Point2 position, int messageLimit, Color background, int width, int height) {
        _messageLimit = messageLimit;
        _contents = new Text[_messageLimit];
        _active = true;
        _width = width;
        _height = height;
        _position = position;

        setBackgroundDepth(DrawDepths.get("DefaultTextBG"));
        setTextDepth(DrawDepths.get("DefaultText"));
        setFont(__defaultFontLabel, __defaultFontPointSize);

        Color[][] base = ProcTextures.monotone(_width, _height, background);
        Outline.single(base, Color.WHITE, 2);
        _background = SpriteMaker.fromColors(base);
    }

    private int getY(int index) {
        //TODO automatically compensate for multiline wrap
        return (int) (_height - (index * _pointSize) + _position.Y - _pointSize / 2);
    }

    private void add(String message, boolean checkDoubleSpaced) {
        if (_index < _contents.length) {
            _contents[_index] = TextPool.get().write(message, new Point2(_position.X + _pointSize / 2, getY(_index)));
            _contents[_index].setFont(_fontLabel, _pointSize);
            _contents[_index].setMoveable(false);
            _contents[_index].setVisible(_active);
            _contents[_index].setDepth(_textDepth);
            _index++;
        }
        else {
            for (int ii = 0; ii < _contents.length - 1; ii++) {
                _contents[ii].setMessage(_contents[ii + 1].getMessage());
            }
            _contents[_contents.length - 1].setMessage(message);
        }
        if (checkDoubleSpaced && _doubleSpaced) {
            add("", false);
        }
    }

    public void add(String message) {
        add(message, true);
    }

    public void draw() {
        if (_active) {
            _background.setPosition(_position.X, _position.Y);
            Window.get(true).schedule(_background, _backgroundDepth);
            for (Text _content : _contents) {
                if (_content != null) {
                    _content.draw();
                }
            }
        }
    }

    public void setFont(String fontLabel, int pointSize) {
        _fontLabel = fontLabel;
        _pointSize = pointSize;
        for (int ii = 0; ii < _contents.length - 1; ii++) {
            if (_contents[ii] != null) {
                _contents[ii].setFont(_fontLabel, _pointSize);
            }
        }
    }

    public void setVisible(boolean visible) {
        _active = visible;

        for (int ii = 0; ii < _contents.length; ii++) {
            if (_contents[ii] != null) {
                _contents[ii].setVisible(visible);
            }
        }
    }

    public void setBackgroundDepth(DrawDepth depth) {
        _backgroundDepth = depth;
    }

    public void setTextDepth(DrawDepth depth) {
        _textDepth = depth;
        for (Text content : _contents) {
            if (content != null) {
                content.setDepth(_textDepth);
            }
        }
    }

    public void setDoubleSpaced(boolean doubleSpaced) {
        _doubleSpaced = doubleSpaced;
    }
}

