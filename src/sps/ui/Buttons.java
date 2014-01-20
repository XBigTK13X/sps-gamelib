package sps.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.bridge.Command;
import sps.bridge.DrawDepth;
import sps.bridge.DrawDepths;
import sps.color.Color;
import sps.display.Window;
import sps.entities.HitTest;
import sps.io.Input;
import sps.states.StateManager;
import sps.util.BoundingBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Buttons {
    public static abstract class User {
        private BoundingBox _bounds = BoundingBox.empty();
        private DrawDepth _depth;
        private boolean _active = true;
        private boolean _shouldDraw = true;
        private Command _command;

        public User() {
            _depth = DrawDepths.get("UIButton");
        }

        public void setDepth(DrawDepth depth) {
            _depth = depth;
        }

        public DrawDepth getDepth() {
            return _depth;
        }

        public void setCommand(Command command) {
            _command = command;
        }

        public Command getCommand() {
            return _command;
        }

        public abstract Sprite getSprite();

        public abstract void onClick();

        public void onMouseDown() {

        }

        public void normal() {
            getSprite().setColor(Color.GRAY.getGdxColor());
        }

        public void over() {
            getSprite().setColor(Color.LIGHT_GRAY.getGdxColor());
        }

        public BoundingBox getBounds() {
            BoundingBox.fromDimensions(_bounds, getSprite().getX(), getSprite().getY(), (int) getSprite().getWidth(), (int) getSprite().getHeight());
            return _bounds;
        }

        public void setActive(boolean active) {
            _active = active;
        }

        public boolean isActive() {
            return _active;
        }

        public void setShouldDraw(boolean shouldDraw) {
            _shouldDraw = shouldDraw;
        }

        public boolean shouldDraw() {
            return _shouldDraw;
        }

        public boolean isBeingClicked() {
            boolean mouseOver = HitTest.inBox(Input.get().x(), Input.get().y(), getBounds());
            boolean mouseDown = Input.get().isMouseDown(false);
            return mouseOver && mouseDown;
        }


    }

    private enum State {
        Outside,
        Over,
        Clicked
    }

    private static Buttons __instance;


    public static Buttons get() {
        if (__instance == null) {
            __instance = new Buttons();
        }
        return __instance;
    }

    public static void set(Buttons buttons) {
        __instance = buttons;
    }

    public static void reset() {
        __instance = new Buttons();
    }

    private List<User> _users;
    private Map<User, State> _states;

    private Buttons() {
        _states = new HashMap<>();
        _users = new ArrayList<>();
    }

    public void add(User user) {
        _users.add(user);
        _states.put(user, null);
    }

    private User _highest;

    public void update() {
        for (User user : _users) {
            if (user.isActive()) {
                boolean mouseOver = HitTest.inBox(Input.get().x(), Input.get().y(), user.getBounds());
                boolean mouseDown = Input.get().isMouseDown();
                if (mouseDown) {
                    user.onMouseDown();
                }
                if (!mouseOver || _states.get(user) == State.Clicked) {
                    _states.put(user, State.Outside);
                    user.normal();
                }
                if (mouseOver && !mouseDown) {
                    _states.put(user, State.Over);
                    user.over();
                }
                //FIXME Commands should only function for the highest, but that doesn't work.
                // Without this ugly one-liner, the exit prompt wouldn't disable keyboard input on menus
                // This has the downside of disabling keyboard controls for the exit menu
                boolean commandActive = (!StateManager.get().isSuspended() && (user.getCommand() != null && Input.get().isActive(user.getCommand())));
                if ((_states.get(user) == State.Over && mouseOver && mouseDown) || commandActive) {
                    if (_highest == null) {
                        _highest = user;
                    }
                    else if (_highest.getDepth().DrawDepth < user.getDepth().DrawDepth) {
                        _states.put(_highest, State.Outside);
                        _highest.normal();
                        _highest = user;
                    }
                }
            }
        }

        if (_highest != null) {
            _states.put(_highest, State.Clicked);
            _highest.onClick();
            Input.get().setMouseLock(true);
            _highest = null;
        }
    }

    public void draw() {
        for (User user : _users) {
            if (user.shouldDraw() && user.isActive()) {
                Window.get().schedule(user.getSprite(), user.getDepth());
            }
        }
    }
}
