package com.simplepathstudios.gamelib.desktop.sample;

import com.simplepathstudios.gamelib.pregame.PreloadMainMenu;
import com.simplepathstudios.gamelib.states.State;
import com.simplepathstudios.gamelib.states.StateResolver;

public class SampleResolver implements StateResolver {
    @Override
    public State createInitial() {
        return new PreloadMainMenu();
    }

    @Override
    public State get(Class target) {
        return new SampleGameplay();
    }

    @Override
    public State loadGame() {
        return new SampleGameplay();
    }

    @Override
    public State newGame() {
        return new SampleGameplay();
    }
}
