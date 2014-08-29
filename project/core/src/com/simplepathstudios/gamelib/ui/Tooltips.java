package com.simplepathstudios.gamelib.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.DrawDepths;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.display.Point2;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.draw.ProcTextures;
import com.simplepathstudios.gamelib.draw.SpriteMaker;
import com.simplepathstudios.gamelib.input.Input;
import com.simplepathstudios.gamelib.states.GameSystem;
import com.simplepathstudios.gamelib.states.Systems;
import com.simplepathstudios.gamelib.text.Text;
import com.simplepathstudios.gamelib.text.TextPool;

import java.util.ArrayList;
import java.util.List;

public class Tooltips implements GameSystem {
    public interface User {
        boolean isActive();

        String message();
    }

    private static final Point2 __tooltipOffset = Screen.pos(1, 1);
    private static Sprite __bg;

    private Text _message;
    private Point2 _position;
    private boolean _active;
    private float _messageWidth;
    private float _messageHeight;

    private List<User> _users;

    public Tooltips() {
        if (__bg == null) {
            Color[][] tbg = ProcTextures.monotone(2, 2, new Color(.1f, .1f, .1f, .85f));
            __bg = SpriteMaker.fromColors(tbg);
        }
        _position = new Point2(0, 0);
        _message = Systems.get(TextPool.class).write("NOTHING", _position);
        _message.setDepth(DrawDepths.get("TooltipBackground"));
        _message.hide();
        _users = new ArrayList<>();
    }

    public void display(String message) {
        _message.setMessage(message);
        _messageWidth = _message.getBounds().width;
        //TODO This is nasty. Height should work the same as width. Libgdx not returning correct value?
        _messageHeight = _message.getBounds().height * 3;
        _position.reset(Input.get().x() + (int) __tooltipOffset.X, Input.get().y() + (int) __tooltipOffset.Y);
        _message.setPosition((int) _position.X, (int) _position.Y);
    }

    @Override
    public void draw() {
        if (_active) {
            int fontWidthOffset = 0;//-__fontWidth / 2
            int fontHeightOffset = -(int) (_messageHeight * .45);
            __bg.setPosition(_position.X + fontWidthOffset, _position.Y + fontHeightOffset);
            __bg.setSize(_messageWidth, _messageHeight);
            Window.get().schedule(__bg, DrawDepths.get("TooltipBackground"));
        }
    }

    @Override
    public void update() {
        _active = false;
        for (User user : _users) {
            if (user.isActive()) {
                _active = true;
                display(user.message());
            }
        }
        _message.setVisible(_active);
    }

    public void add(User user) {
        _users.add(user);
    }
}
