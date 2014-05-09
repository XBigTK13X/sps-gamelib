package com.simplepathstudios.gamelib.data;

public interface GameStateHandler {
    String getPersistable();
    GameState loadFromPersistable(String persistable);
    int getCurrentSaveFormatVersion();
}
