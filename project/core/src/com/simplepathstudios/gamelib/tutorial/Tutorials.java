package com.simplepathstudios.gamelib.tutorial;

import com.simplepathstudios.gamelib.data.SpsConfig;
import com.simplepathstudios.gamelib.states.StateManager;

import java.util.HashMap;
import java.util.Map;

public class Tutorials {
    private static Tutorials __instance;

    public static Tutorials get() {
        if (__instance == null) {
            __instance = new Tutorials();
        }
        return __instance;
    }

    private Tutorials() {
        _tutorials = new HashMap<>();
        _completedTutorials = new HashMap<>();
    }

    private final Map<Class, Tutorial> _tutorials;
    private final Map<Tutorial, Boolean> _completedTutorials;
    private Tutorial _tutorial;

    public void add(Class state, Tutorial tutorial) {
        _tutorials.put(state, tutorial);
    }

    public void show() {
        show(false);
    }

    public void show(boolean ignoreConfig) {
        if (SpsConfig.get().tutorialEnabled || ignoreConfig) {
            Tutorial tutorial = _tutorials.get(StateManager.get().current().getClass());
            if (tutorial != null) {
                Boolean completed = _completedTutorials.get(tutorial);
                if (completed == null) {
                    completed = false;
                }
                if (!completed || ignoreConfig) {
                    _tutorial = tutorial;
                    _tutorial.load();
                    if (!completed) {
                        _completedTutorials.put(tutorial, true);
                    }
                }
            }
        }
    }

    public void clearCompletion() {
        for (Class state : _tutorials.keySet()) {
            Tutorial t = _tutorials.get(state);
            _completedTutorials.put(t, false);
        }
    }

    public void update() {
        if (_tutorial != null) {
            if (_tutorial.isFinished()) {
                _tutorial = null;
                StateManager.get().setSuspend(false);
            }
            else {
                _tutorial.update();
            }
        }
    }

    public boolean close() {
        if (_tutorial != null) {
            _tutorial.close();
            _tutorial = null;
            return true;
        }
        return false;
    }

    public boolean isActive() {
        return _tutorial != null;
    }
}
