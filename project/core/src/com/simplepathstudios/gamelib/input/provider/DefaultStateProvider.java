package com.simplepathstudios.gamelib.input.provider;

import com.simplepathstudios.gamelib.bridge.Command;
import com.simplepathstudios.gamelib.bridge.Commands;
import com.simplepathstudios.gamelib.input.*;

public class DefaultStateProvider implements StateProvider {
    private final CommandState state = new CommandState();

    @Override
    public boolean isActive(Command command, PlayerIndex playerIndex) {
        return state.isActive(playerIndex, command);
    }

    @Override
    public void setState(Command command, PlayerIndex playerIndex, boolean isActive) {
        state.setState(playerIndex, command, isActive);
    }

    @Override
    public void pollLocalState() {
        for (Command command : Commands.values()) {
            for (PlayerIndex player : Players.getAll()) {
                setState(command, player, Input.get().detectState(command, player));
            }
        }
    }
}
