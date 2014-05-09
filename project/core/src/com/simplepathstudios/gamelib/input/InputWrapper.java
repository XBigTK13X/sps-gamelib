package com.simplepathstudios.gamelib.input;

import com.simplepathstudios.gamelib.bridge.Command;
import com.simplepathstudios.gamelib.bridge.Commands;

public class InputWrapper {
    public static boolean isActive(String commandId) {
        return isActive(Commands.get(commandId));
    }

    public static boolean isActive(Command command) {
        for (PlayerIndex index : Players.getAll()) {
            if (Input.get().isActive(command, index)) {
                return true;
            }
        }
        return false;
    }
}
