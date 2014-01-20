package sps.states;

public class GlobalStateResolver {
    private static StateResolver _resolver;

    public static void set(StateResolver resolver){
        _resolver = resolver;
    }
    public static State create() {
        return _resolver.createInitial();
    }

    public static State loadGame() {
        return _resolver.loadGame();
    }

    public static State newGame(){
        return _resolver.newGame();
    }
}
