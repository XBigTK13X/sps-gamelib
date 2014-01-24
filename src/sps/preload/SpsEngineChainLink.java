package sps.preload;

public interface SpsEngineChainLink {
    void update();
    void draw();
    boolean isFinished();
}
