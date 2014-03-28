package sps.states;

public interface State {
    void create();

    void update();

    void draw();

    String getName();

    void asyncUpdate();

    void load();

    void unload();

    void pause();
}
