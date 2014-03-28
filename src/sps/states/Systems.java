package sps.states;

import sps.core.Logger;
import sps.util.GenericFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Systems {
    private static Map<Class, GameSystem> __instances;
    private static List<Class> __registeredSystems;

    public static <T extends GameSystem> void register(Class<T> type) {
        if (__registeredSystems == null) {
            __registeredSystems = new LinkedList<>();
        }
        __registeredSystems.add(type);
    }

    public static <T> T get(Class<T> type) {
        return type.cast(__instances.get(type));
    }

    public static void set(List<GameSystem> instances) {
        for (GameSystem system : instances) {
            __instances.put(system.getClass(), system);
        }
    }

    public static void newInstances() {
        __instances = new HashMap<>();
        for (Class type : __registeredSystems) {
            try {
                __instances.put(type, GenericFactory.newGameSystem(type));
            } catch (Exception e) {
                Logger.exception(e, false);
            }
        }
    }

    public static List<GameSystem> getAll() {
        List result = new LinkedList<GameSystem>();
        for(GameSystem system:__instances.values()){
            result.add(system);
        }
        return result;
    }

    public static void update() {
        for (Class type : __instances.keySet()) {
            __instances.get(type).update();
        }
    }

    public static void draw() {
        for (Class type : __instances.keySet()) {
            __instances.get(type).draw();
        }
    }
}
