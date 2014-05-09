package com.simplepathstudios.gamelib.input;

public class CommandLock {
    public CommandLock(com.simplepathstudios.gamelib.bridge.Command command, PlayerIndex playerIndex) {
        Command = command;
        Index = playerIndex;
    }

    public final com.simplepathstudios.gamelib.bridge.Command Command;
    public final PlayerIndex Index;
}
