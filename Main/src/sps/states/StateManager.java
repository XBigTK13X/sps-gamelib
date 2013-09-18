package sps.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import sps.audio.MusicPlayer;
import sps.core.Logger;
import sps.core.SpsConfig;
import sps.display.Screen;
import sps.display.Window;
import sps.draw.ProcTextures;
import sps.draw.SpriteMaker;
import sps.entities.EntityManager;
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
    private static Sprite __pausedScreen;

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
    private boolean _paused = false;

    private StateManager() {
        _states = new Stack<>();
        _components = new HashMap<>();
    }

    public void setPaused(boolean value) {
        if (__pausedScreen == null) {
            Color[][] tbg = ProcTextures.monotone((int) Screen.width(50), (int) Screen.height(50), new Color(.5f, .1f, .5f, .7f));
            __pausedScreen = SpriteMaker.get().fromColors(tbg);
        }
        _paused = value;
    }

    public boolean isPaused() {
        return _paused;
    }

    private void loadCurrent() {
        //$$$ Logger.info("=== Loading new state: " + state.getName());
        if (_components.containsKey(current())) {
            StateDependentComponents components = _components.get(current());
            EntityManager.set(components.EntityManager);
            ParticleEngine.set(components.ParticleEngine);
            TextPool.set(components.TextPool);
            MusicPlayer.set(components.MusicPlayer);
            Tooltips.set(components.Tooltips);
            Buttons.set(components.Buttons);
        }
        else {
            EntityManager.reset();
            ParticleEngine.reset();
            TextPool.reset();
            MusicPlayer.reset();
            UiElements.reset();
        }
        ParticleWrapper.get().release();
        current().load();
    }

    public void push(State state) {
        if (SpsConfig.get().devTimeStates) {
            Logger.info("Pushing: " + state.getName() + ". Time since last: " + ((System.currentTimeMillis() - lastMil)) / 1000f);
            lastMil = System.currentTimeMillis();
        }
        if (SpsConfig.get().optCollectMetaData) {
            if (lastMil != 0) {
                if (!stateTimes.containsKey(state.getName())) {
                    stateTimes.put(state.getName(), 0L);
                }
                stateTimes.put(state.getName(), stateTimes.get(state.getName()) + (System.currentTimeMillis() - lastMil));
            }
            lastMil = System.currentTimeMillis();
        }
        Window.get().resetCamera();
        boolean isNewState = false;
        if (_states.size() > 0) {
            _components.put(current(), new StateDependentComponents(EntityManager.get(), ParticleEngine.get(), TextPool.get(), MusicPlayer.get(), Tooltips.get(), Buttons.get()));
        }
        if (!_states.contains(state)) {
            isNewState = true;
        }
        _states.push(state);
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
        if (!_paused) {
            current().draw();
        }
        else {
            Window.get().draw(__pausedScreen);
        }
    }

    public void update() {
        if (!_paused) {
            current().update();
        }
    }

    public void asyncUpdate() {
        if (!_paused) {
            current().asyncUpdate();
        }
    }

    public void resize(int width, int height) {
        current().resize(width, height);
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
