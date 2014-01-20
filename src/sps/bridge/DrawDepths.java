package sps.bridge;

import sps.core.Logger;

import java.util.HashMap;
import java.util.Map;

public class DrawDepths {

    private static DrawDepths instance;

    public static DrawDepth get(String name) {
        return instance.resolve(name);
    }

    public static void add(DrawDepth DrawDepth) {
        if (instance == null) {
            instance = new DrawDepths();
        }
        instance.put(DrawDepth);
    }

    private Map<String, DrawDepth> drawDepths = new HashMap<>();

    private DrawDepths() {
    }

    public DrawDepth resolve(String name) {
        if (!drawDepths.containsKey(name)) {
            Logger.exception(new Exception("DrawDepth not found: " + name));
        }
        return drawDepths.get(name);
    }

    public void put(DrawDepth DrawDepth) {
        drawDepths.put(DrawDepth.Name, DrawDepth);
    }
}
