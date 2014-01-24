package sps.input;

public class CommandLock {
    public CommandLock(sps.bridge.Command command, PlayerIndex playerIndex) {
        Command = command;
        Index = playerIndex;
    }

    public final sps.bridge.Command Command;
    public final PlayerIndex Index;
}
