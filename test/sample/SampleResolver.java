package sample;

import sample.states.SampleGameplay;
import sps.pregame.PreloadMainMenu;
import sps.states.State;
import sps.states.StateResolver;

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
