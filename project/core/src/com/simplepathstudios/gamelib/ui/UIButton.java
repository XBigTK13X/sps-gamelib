package com.simplepathstudios.gamelib.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.audio.RandomSoundPlayer;
import com.simplepathstudios.gamelib.bridge.Command;
import com.simplepathstudios.gamelib.bridge.DrawDepth;
import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.core.Point2;
import com.simplepathstudios.gamelib.core.SpsConfig;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.draw.Outline;
import com.simplepathstudios.gamelib.draw.ProcTextures;
import com.simplepathstudios.gamelib.draw.SpriteMaker;
import com.simplepathstudios.gamelib.input.InputWrapper;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.Text;
import com.simplepathstudios.gamelib.text.TextPool;
import org.apache.commons.lang3.StringUtils;

public abstract class UIButton {
    private static final Point2 __default = Screen.pos(20, 20);
    private static final Point2 __padding = Screen.pos(1, 1);

    public static int x(int column) {
        return (int) (column * __padding.X + column * __default.X);
    }

    public static int y(int row) {
        return (int) (row * __padding.Y + row * __default.Y);
    }

    private Buttons.User _buttonUser;
    private Text _message;
    private Text _commandMessage;
    private Sprite _sprite;
    private int _width;
    private int _height;
    private Command _command;
    private boolean _visible = true;
    private DrawDepth _depth;
    private Point2 _position;
    private Color _start;
    private Color _end;
    private boolean _moveable = true;
    private String _originalMessage;

    public UIButton(String text) {
        this(text, 0, 0);
    }

    public UIButton(String text, Command command) {
        this(text, 0, 0, command);
    }

    public UIButton(String text, int x, int y) {
        this(text, x, y, null);
    }

    public UIButton(String text, int x, int y, Command command) {
        _start = Color.WHITE;
        _end = Color.GRAY;
        _depth = DrawDepths.get("UIButton");
        _command = command;
        _position = new Point2(0, 0);

        if (_command != null && SpsConfig.get().uiButtonKeyboardLabelsEnabled) {
            _commandMessage = Systems.get(TextPool.class).write(_command.toString(), new Point2(0, 0));
            _commandMessage.setDepth(DrawDepths.get("UIButtonText"));
        }

        _message = Systems.get(TextPool.class).write(text, new Point2(0, 0));
        setFont("UIButton", 60);
        _message.setDepth(DrawDepths.get("UIButtonText"));

        _originalMessage = text;

        setSize(20, 20);

        _buttonUser = new Buttons.User() {
            @Override
            public Sprite getSprite() {
                return _sprite;
            }

            @Override
            public void onClick() {
                RandomSoundPlayer.play("click");
                click();
            }

            @Override
            public void onMouseDown() {
                mouseDown();
            }
        };
        _buttonUser.setCommand(command);
        Systems.get(Buttons.class).add(_buttonUser);

        setMessage(text);
        setXY(x, y);
    }

    public Point2 getPosition() {
        return _position;
    }

    public void setBackgroundColors(Color start, Color end) {
        _start = start;
        _end = end;
        rebuildBackground();
    }

    private void rebuildBackground() {
        Color[][] base = ProcTextures.gradient(_width, _height, _start, _end, false);
        Outline.single(base, Color.WHITE, 1);
        _sprite = SpriteMaker.fromColors(base);
        _sprite.setPosition(_position.X, _position.Y);
    }

    public void setPixelSize(int width, int height) {
        _width = width;
        _height = height;
        rebuildBackground();
        layout();
    }

    public void setSize(int width, int height) {
        setPixelSize((int) Screen.width(width), (int) (Screen.height(height)));
    }

    public void setMessage(String message) {
        int maxLines = 5;
        _originalMessage = message;
        _message.setMessage(message);
        int longestLineLength = 0;
        while (_width < _message.getBounds().width && maxLines-- > 0) {
            _message.setMessage(_message.getMessage().replace(" ", "\n"));
        }
        _message.setMessage(_message.getMessage().trim());
        for (String line : _message.getMessage().split("\n")) {
            if (line.length() > longestLineLength) {
                longestLineLength = line.length();
            }
        }
        for (String line : _message.getMessage().split("\n")) {
            if (line.length() < longestLineLength) {
                int offset = (longestLineLength - line.length());
                _message.setMessage(_message.getMessage().replace(line, StringUtils.repeat(" ", offset) + line));
            }
        }
    }

    public void setXY(int x, int y) {
        int mW = (int) _message.getBounds().width;
        int mH = (int) _message.getBounds().height;
        int mX = x + (_width - mW) / 2;
        int mY = y + mH + (_height - mH) / 2;
        _message.setPosition(mX, mY);

        if (_commandMessage != null) {
            int cW = (int) _commandMessage.getBounds().width;
            int cH = (int) _commandMessage.getBounds().height;
            int cX = x + (_width - cW) - (int) (Screen.width(1) / 2);
            int cY = y + cH + (int) Screen.height(1);
            _commandMessage.setPosition(cX, cY);
        }
        _sprite.setPosition(x, y);
        _position.reset(x, y);
    }

    public void layout() {
        //FIXME Is it possible to have a worse piece of code than this?
        //TODO Figure out why if this isn't called, text isn't centered properly.
        setMessage(_originalMessage);
        setXY((int) _position.X, (int) _position.Y);
    }

    public void setScreenPercent(int x, int y) {
        setXY((int) Screen.width(x), (int) Screen.height(y));
    }

    public void setColRow(int col, int row) {
        setXY(UIButton.x(col), UIButton.y(row));
    }

    public void draw() {
        if (_visible) {
            if (_command != null) {
                if (InputWrapper.isActive(_command)) {
                    click();
                }
            }
            Window.get(!_moveable).schedule(_sprite, _depth);
        }
    }

    public abstract void click();

    public void mouseDown() {

    }

    public boolean beingClicked() {
        return _buttonUser.isBeingClicked();
    }

    public void setVisible(boolean visible) {
        _message.setVisible(visible);
        if (_commandMessage != null) {
            _commandMessage.setVisible(visible);
        }
        _visible = visible;
        _buttonUser.setActive(visible);
    }

    public void setDepth(DrawDepth depth) {
        _depth = depth;
        DrawDepth textDepth = new DrawDepth("UIButtonTextTemp", depth.DrawDepth + 1);
        _message.setDepth(textDepth);
        if (_commandMessage != null) {
            _commandMessage.setDepth(textDepth);
        }
        _buttonUser.setDepth(depth);
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public Sprite getSprite() {
        return _sprite;
    }

    public Text getMessage() {
        return _message;
    }

    public void setMoveable(boolean moveable) {
        _moveable = moveable;
        _message.setMoveable(moveable);
        if (_commandMessage != null) {
            _commandMessage.setMoveable(moveable);
        }
    }

    public void setFont(String fontLabel, int pointSize) {
        _message.setFont(fontLabel, pointSize);
        if (_commandMessage != null) {
            _commandMessage.setFont(fontLabel, pointSize / 2);
        }
    }
}
