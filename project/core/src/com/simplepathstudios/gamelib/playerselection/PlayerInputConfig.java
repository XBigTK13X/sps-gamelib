package com.simplepathstudios.gamelib.playerselection;

import com.simplepathstudios.gamelib.input.PlayerIndex;

import java.util.HashMap;
import java.util.Map;

public class PlayerInputConfig {
    private Map<Integer, PlayerIndex> _indices;

    public PlayerInputConfig() {
        _indices = new HashMap<>();
    }

    public void addPlayer(int playerNumber, PlayerIndex index) {
        _indices.put(playerNumber, index);
    }

    public Map<Integer, PlayerIndex> getAll() {
        return _indices;
    }

}
