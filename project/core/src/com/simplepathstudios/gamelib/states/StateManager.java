package com.simplepathstudios.gamelib.states;

import com.simplepathstudios.gamelib.audio.MusicPlayer;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.data.DevConfig;
import com.simplepathstudios.gamelib.data.GameConfig;
import com.simplepathstudios.gamelib.display.Window;
import com.simplepathstudios.gamelib.particle.ParticleWrapper;
import com.simplepathstudios.gamelib.text.TextPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class StateManager {
    private static StateManager __instance = new StateManager();
    private static long lastMil = java.lang.System.currentTimeMillis();
    private static Map<String, Long> stateTimes = new HashMap<>();

    public static StateManager get() {
        return __instance;
    }

    public static StateManager reset() {
        if (__instance != null) {
            __instance.removeAll();
        }
        __instance = new StateManager();
        return get();
    }

    public static void set(StateManager stateManager) {
        __instance = stateManager;
        clearTimes();
    }

    public static void clearTimes() {
        stateTimes = new HashMap<>();
    }

    public static String json() {
        String result = "\"stateTimes\":{";
        int c = 0;
        for (String k : stateTimes.keySet()) {
            result += "\"" + k + "\":\"" + stateTimes.get(k) + "\"";
            if (c++ < stateTimes.keySet().size() - 1) {
                result += ",";
            }
        }
        result += "}";
        return result;
    }

    private Stack<State> _states;
    private Map<State, List<GameSystem>> _systems;
    private boolean _suspended = false;

    private StateManager() {
        _states = new Stack<>();
        _systems = new HashMap<>();
    }

    public void setSuspend(boolean suspend) {
        _suspended = suspend;
        if (_suspended) {
            MusicPlayer.get().pause();
        }
        else {
            MusicPlayer.get().resume();
        }
    }

    public boolean isSuspended() {
        return _suspended;
    }

    private void loadCurrent() {
        //$$$ Logger.info("=== Loading new state: " + state.getName());
        if (_systems.containsKey(current())) {
            Systems.set(_systems.get(current()));
        }
        else {
            Systems.newInstances();
        }
        ParticleWrapper.get().release();
        current().load();
    }

    public void push(Class type) {
        push(GlobalStateResolver.get().get(type));
    }

    public void push(State state) {
        if (DevConfig.TimeStates) {
            Logger.info("Pushing: " + state.getName() + ". Time since last: " + ((java.lang.System.currentTimeMillis() - lastMil)) / 1000f);
            lastMil = java.lang.System.currentTimeMillis();
        }
        if (GameConfig.OptCollectMetaData) {
            if (lastMil != 0) {
                if (!stateTimes.containsKey(state.getName())) {
                    stateTimes.put(state.getName(), 0L);
                }
                stateTimes.put(state.getName(), stateTimes.get(state.getName()) + (java.lang.System.currentTimeMillis() - lastMil));
            }
            lastMil = java.lang.System.currentTimeMillis();
        }
        Window.get().screenEngine().resetCamera();
        boolean isNewState = false;
        if (_states.size() > 0) {
            _systems.put(current(), Systems.getAll());
        }
        if (!_states.contains(state)) {
            isNewState = true;
        }
        _states.push(state);
        MusicPlayer.get().stop();
        loadCurrent();
        if (isNewState) {
            state.create();
        }
    }

    public void removeAll() {
        while (_states.size() > 0) {
            pop(true);
        }
    }

    public void rollBackTo(Class state) {
        while (current().getClass() != state && _states.size() > 1) {
            pop(true);
        }
        if (current().getClass() != state) {
            throw new RuntimeException("Attempted to rollback to a state that isn't on the stack: " + state);
        }
        loadCurrent();
    }

    public void pop() {
        pop(false);
    }

    public void pop(boolean force) {
        if (_states.size() > 1 || (force && _states.size() > 0)) {
            State dying = _states.pop();
            Systems.get(TextPool.class).clear(dying);
            dying.unload();
            List<GameSystem> systems = _systems.get(dying);
            if (systems != null) {
                _systems.remove(dying);
            }
            if (_states.size() > 0 && !force) {
                loadCurrent();
            }
        }
    }

    public void draw() {
        current().draw();
    }

    public void update() {
        if (!_suspended) {
            current().update();
        }
    }

    public void asyncUpdate() {
        if (!_suspended) {
            current().asyncUpdate();
        }
    }

    public State current() {
        if (_states.size() == 0) {
            return null;
        }
        return _states.peek();
    }

    public boolean hasAny(Class state) {
        for (State s : _states.subList(0, _states.size())) {
            if (s.getClass() == state) {
                return true;
            }
        }
        return false;
    }
}
