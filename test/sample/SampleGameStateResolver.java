package sample;

import sample.states.SampleGameplay;
import sps.states.State;
import sps.states.StateResolver;

public class SampleGameStateResolver implements StateResolver {
    @Override
    public State createInitial() {
        return new SampleGameplay();
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
