package sps.states;

public interface StateResolver {
    State createInitial();
    State get(Class target);
    State loadGame();
    State newGame();
}
