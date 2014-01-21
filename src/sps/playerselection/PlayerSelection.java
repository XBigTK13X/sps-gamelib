package sps.playerselection;

import sps.io.PlayerIndex;
import sps.io.Players;
import sps.states.State;
import sps.data.GameConfig;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelection implements State {
    private List<AvailablePlayer> _availablePlayers;
    private List<AvailableInput> _availableInputs;
    private PlayerInputConfig _playerConfig;
    private SelectionHandler _handler;

    public PlayerSelection(SelectionHandler handler) {
        _handler = handler;
        _playerConfig = new PlayerInputConfig();
        _availableInputs = new ArrayList<>();
        _availablePlayers = new ArrayList<>();
    }

    @Override
    public void create() {
        int inputCount = 0;
        for (PlayerIndex index : Players.getAll()) {
            _availableInputs.add(new AvailableInput(index, inputCount++));
        }

        for (int ii = 0; ii < GameConfig.PlayersMax; ii++) {
            _availablePlayers.add(new AvailablePlayer(ii));
        }
    }

    @Override
    public void draw() {
        for (AvailablePlayer aPlayer : _availablePlayers) {
            aPlayer.draw();
        }

        for (AvailableInput aInput : _availableInputs) {
            aInput.draw();
        }
    }

    private boolean _allReady;
    private boolean _anyReady;

    private void checkReady() {
        _allReady = true;
        _anyReady = false;
        for (AvailableInput aInput : _availableInputs) {
            if (aInput.ready()) {
                _anyReady = true;
            }
            else {
                _allReady = false;
            }
        }
    }

    @Override
    public void update() {
        for (AvailablePlayer aPlayer : _availablePlayers) {
            aPlayer.update();
        }

        for (AvailableInput aInput : _availableInputs) {
            aInput.update();
        }

        checkReady();
        if (_anyReady) {
            for (AvailableInput aInput : _availableInputs) {
                if (aInput.confirmed()) {
                    _playerConfig.addPlayer(aInput.getPlayerColumn(), aInput.getPlayerIndex());
                }
            }
            _handler.anyReady();
        }

        else if (_allReady) {
            _handler.allReady();
        }
    }

    @Override
    public void asyncUpdate() {
    }

    @Override
    public void load() {
    }

    @Override
    public void unload() {
    }

    @Override
    public String getName() {
        return "PlayerSelection";
    }

    @Override
    public void pause() {
    }
}
