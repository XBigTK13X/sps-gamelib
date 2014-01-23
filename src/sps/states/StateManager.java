package sps.states;

import sps.audio.MusicPlayer;
import sps.core.Logger;
import sps.data.DevConfig;
import sps.data.GameConfig;
import sps.display.Window;
import sps.entities.EntityManager;
import sps.entities.LightEntities;
import sps.particles.ParticleEngine;
import sps.particles.ParticleWrapper;
import sps.text.TextPool;
import sps.ui.Buttons;
import sps.ui.Tooltips;
import sps.ui.UiElements;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StateManager {
    private static StateManager __instance = new StateManager();
    private static long lastMil = System.currentTimeMillis();
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
        stateTimes = new HashMap<String, Long>();
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
    private Map<State, StateDependentComponents> _components;
    private boolean _suspended = false;

    private StateManager() {
        _states = new Stack<>();
        _components = new HashMap<>();
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
        if (_components.containsKey(current())) {
            StateDependentComponents components = _components.get(current());
            EntityManager.set(components.EntityManager);
            LightEntities.set(components.LightEntities);
            ParticleEngine.set(components.ParticleEngine);
            TextPool.set(components.TextPool);
            Tooltips.set(components.Tooltips);
            Buttons.set(components.Buttons);
        }
        else {
            LightEntities.reset();
            EntityManager.reset();
            ParticleEngine.reset();
            TextPool.reset();
            UiElements.reset();
        }
        ParticleWrapper.get().release();
        current().load();
    }

    public void push(State state) {
        if (DevConfig.TimeStates) {
            Logger.info("Pushing: " + state.getName() + ". Time since last: " + ((System.currentTimeMillis() - lastMil)) / 1000f);
            lastMil = System.currentTimeMillis();
        }
        if (GameConfig.OptCollectMetaData) {
            if (lastMil != 0) {
                if (!stateTimes.containsKey(state.getName())) {
                    stateTimes.put(state.getName(), 0L);
                }
                stateTimes.put(state.getName(), stateTimes.get(state.getName()) + (System.currentTimeMillis() - lastMil));
            }
            lastMil = System.currentTimeMillis();
        }
        Window.get().screenEngine().resetCamera();
        boolean isNewState = false;
        if (_states.size() > 0) {
            _components.put(current(), new StateDependentComponents(LightEntities.get(), EntityManager.get(), ParticleEngine.get(), TextPool.get(), Tooltips.get(), Buttons.get()));
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
            TextPool.get().clear(dying);
            dying.unload();
            StateDependentComponents sdc = _components.get(dying);
            if (sdc != null) {
                _components.remove(dying);
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
