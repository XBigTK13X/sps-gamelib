package sps.bridge;

import sps.io.ControllerInput;
import sps.io.Keys;

public class Command implements Comparable<Command> {
    private ControllerInput _controllerInput;
    private Keys[] _keys;
    private String _name;
    public Context Context;

    public Command() {

    }

    public Command(String name, Context context) {
        Context = context;
        _name = name;
    }

    public void bind(ControllerInput controllerInput, Keys... keys) {
        _controllerInput = controllerInput;
        _keys = keys;
        recalcPrettyId();
    }

    public ControllerInput controllerInput() {
        return _controllerInput;
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
