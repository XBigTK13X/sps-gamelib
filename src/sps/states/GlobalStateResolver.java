package sps.states;

public class GlobalStateResolver {
    private static StateResolver _resolver;

    public static void set(StateResolver resolver){
        _resolver = resolver;
    }

    public static StateResolver get(){
        return _resolver;
    }
}
