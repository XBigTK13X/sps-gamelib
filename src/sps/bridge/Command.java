package sps.bridge;

import sps.input.gamepad.GamepadInput;
import sps.input.Keys;

public class Command implements Comparable<Command> {
    private GamepadInput _gamepadInput;
    private Keys[] _keys;
    private String _name;
    public Context Context;

    public Command() {

    }

    public Command(String name, Context context) {
        Context = context;
        _name = name;
    }

    public void bind(GamepadInput gamepadInput, Keys... keys) {
        _gamepadInput = gamepadInput;
        _keys = keys;
        recalcPrettyId();
    }

    public GamepadInput controllerInput() {
        return _gamepadInput;
    }

    public Keys[] keys() {
        return _keys;
    }

    public String name() {
        return _name;
    }

    @Override
    public int hashCode() {
        return _name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Command c = (Command) obj;
        return c._name.equalsIgnoreCase(_name);
    }

    private void recalcPrettyId() {
        _prettyId = "";
        for (int ii = 0; ii < keys().length; ii++) {
            _prettyId += keys()[ii];
            if (ii < keys().length - 1) {
                _prettyId += "+";
            }
        }
        _prettyId = _prettyId.replaceAll("NUM_", "");
        _prettyId = "[" + _prettyId + "]";
    }

    private String _prettyId;

    @Override
    public String toString() {
        if (keys() == null) {
            return "[Undefined]";
        }

        return _prettyId;
    }

    @Override
    public int compareTo(Command o) {
        return name().compareTo(o.name());
    }
}
