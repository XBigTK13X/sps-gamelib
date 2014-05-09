package com.simplepathstudios.gamelib.input;

import com.badlogic.gdx.controllers.Controllers;
import com.simplepathstudios.gamelib.input.gamepad.PreconfiguredGamepadInputs;

import java.util.ArrayList;
import java.util.List;

public class Players {
    private static List<PlayerIndex> _indices;

    private static int __playerIndexBase;
    private static int __keyboards;

    public static void init() {
        _indices = new ArrayList<>();
        addKeyboard();
        for (int ii = 0; ii < Controllers.getControllers().size; ii++) {
            addController(ii, PreconfiguredGamepadInputs.getTypeFromName(ii));
        }
    }

    public static void addKeyboard() {
        _indices.add(new PlayerIndex(__playerIndexBase++, __keyboards++, null, null));
    }

    public static void addController(int controllerId, String gamepadType) {
        _indices.add(new PlayerIndex(__playerIndexBase++, null, controllerId, gamepadType));
    }

    public static List<PlayerIndex> getAll() {
        return _indices;
    }

    public static PlayerIndex first() {
        return _indices.get(0);
    }
}
