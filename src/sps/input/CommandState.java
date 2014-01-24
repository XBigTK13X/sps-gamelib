package sps.input;

import sps.bridge.Command;
import sps.bridge.Commands;

import java.util.HashMap;

public class CommandState {
    private final HashMap<PlayerIndex, HashMap<Command, Boolean>> _state = new HashMap<>();

    public CommandState() {
        for (PlayerIndex index : Players.getAll()) {
            _state.put(index, new HashMap<Command, Boolean>());
            for (Command command : Commands.values()) {
                _state.get(index).put(command, false);
            }
        }
    }

    public boolean isActive(PlayerIndex player, Command command) {
        initPlayer(player, command);
        return _state.get(player).get(command);
    }

    public void setState(PlayerIndex player, Command command, boolean isActive) {
        initPlayer(player, command);
        _state.get(player).put(command, isActive);
    }

    private void initPlayer(PlayerIndex playerIndex, Command command) {
        if (!_state.containsKey(playerIndex)) {
            _state.put(playerIndex, new HashMap<Command, Boolean>());
        }
        if (!_state.get(playerIndex).containsKey(command)) {
            _state.get(playerIndex).put(command, false);
        }
    }

    public void reset(CommandState cs) {
        for (PlayerIndex index : Players.getAll()) {
            if (cs._state.containsKey(index)) {
                for (Command command : Commands.values()) {
                    if (cs._state.get(index).containsKey(command)) {
                        setState(index, command, cs._state.get(index).get(command));
                    }
                }
            }
        }
    }

    public String debug() {
        String result = "";
        for (PlayerIndex key : _state.keySet()) {
            result += "{PI:" + key + ",";
            HashMap<Command, Boolean> upOrDowns = _state.get(key);
            for (Command command : upOrDowns.keySet()) {
                result += "{c:" + command.name() + ",?: " + upOrDowns.get(command) + "}";
            }
            result += "}";
        }
        return result;
    }
}
