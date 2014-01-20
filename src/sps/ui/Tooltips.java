package sps.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.DrawDepths;
import sps.color.Color;
import sps.core.Point2;
import sps.display.Screen;
import sps.display.Window;
import sps.draw.ProcTextures;
import sps.draw.SpriteMaker;
import sps.io.Input;
import sps.text.Text;
import sps.text.TextPool;

import java.util.ArrayList;
import java.util.List;

public class Tooltips {
    public interface User {
        boolean isActive();

        String message();
    }

    private static final Point2 __tooltipOffset = Screen.pos(1, 1);
    private static Sprite __bg;

    private static Tooltips __instance;

    public static Tooltips get() {
        if (__instance == null) {
            __instance = new Tooltips();
        }
        return __instance;
    }

    public static void set(Tooltips tooltips) {
        __instance = tooltips;
    }

    public static void reset() {
        __instance = new Tooltips();
    }

    private Text _message;
    private Point2 _position;
    private boolean _active;
    private float _messageWidth;
    private float _messageHeight;

    private List<User> _users;

    private Tooltips() {
        if (__bg == null) {
            Color[][] tbg = ProcTextures.monotone(2, 2, new Color(.1f, .1f, .1f, .85f));
            __bg = SpriteMaker.fromColors(tbg);
        }
        _position = new Point2(0, 0);
        _message = TextPool.get().write("NOTHING", _position);
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

    public void draw() {
        if (_active) {
            int fontWidthOffset = 0;//-__fontWidth / 2
            int fontHeightOffset = -(int) (_messageHeight * .45);
            __bg.setPosition(_position.X + fontWidthOffset, _position.Y + fontHeightOffset);
            __bg.setSize(_messageWidth, _messageHeight);
            Window.get().schedule(__bg, DrawDepths.get("TooltipBackground"));
        }
    }

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
