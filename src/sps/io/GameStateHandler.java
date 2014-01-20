package sps.io;

public interface GameStateHandler {
    String getPersistable();
    GameState loadFromPersistable(String persistable);
    int getCurrentSaveFormatVersion();
}
