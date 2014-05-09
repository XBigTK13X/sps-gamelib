package com.simplepathstudios.gamelib.console;

public abstract class DevConsoleAction {
    public final String Id;

    public DevConsoleAction(String id) {
        Id = id;
    }

    public abstract String act(int[] input);
}
