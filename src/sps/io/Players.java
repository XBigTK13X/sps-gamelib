package sps.io;

import com.badlogic.gdx.controllers.Controllers;

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
            addController(ii);
        }
    }

    public static void addKeyboard() {
        _indices.add(new PlayerIndex(__playerIndexBase++, __keyboards++, null));
    }

    public static void addController(int controllerId) {
        _indices.add(new PlayerIndex(__playerIndexBase++, null, controllerId));
    }

    public static List<PlayerIndex> getAll() {
        return _indices;
    }
}
