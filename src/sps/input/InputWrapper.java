package sps.input;

import sps.bridge.Command;
import sps.bridge.Commands;

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
