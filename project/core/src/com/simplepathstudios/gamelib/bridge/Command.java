package com.simplepathstudios.gamelib.bridge;

import com.simplepathstudios.gamelib.input.Keys;
import com.simplepathstudios.gamelib.input.gamepad.GamepadInput;

import java.util.*;

public class Command implements Comparable<Command> {
    private Map<String, List<GamepadInput>> _gamepadInput = new HashMap<>();
    private List<Keys> _keys = new ArrayList<>();
    private String _name;
    public Context Context;

    public Command() {

    }

    public Command(String name, Context context) {
        Context = context;
        _name = name;
    }

    public void bind(List<Keys> keys) {
        _keys = keys;
        recalcPrettyId();
    }

    public void bind(String gamepadType, List<GamepadInput> inputs) {
        _gamepadInput.put(gamepadType, inputs);
        recalcPrettyId();
    }

    public List<GamepadInput> controllerInput(String gamepadType) {
        return _gamepadInput.get(gamepadType);
    }

    public List<Keys> keys() {
        return _keys;
    }

    public Collection<String> supportedGamepads(){
        return _gamepadInput.keySet();
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
        for (int ii = 0; ii < keys().size(); ii++) {
            _prettyId += _keys.get(ii);
            if (ii < keys().size() - 1) {
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
