package sps.io;

import sps.core.Loader;

import java.io.File;

public class UserFiles {
    private static String _gameId = "sps-gamelib";

    public static void setGameId(String gameId){
        _gameId = gameId;
    }

    public static File save() {
        return Loader.get().userSave(_gameId, "autosave.dat");
    }

    public static File config() {
        return Loader.get().userSave(_gameId, "game.cfg");
    }

    public static File input() {
        return Loader.get().userSave(_gameId, "input.cfg");
    }

    public static File crash(){
        return Loader.get().userSave(_gameId, "game.crash");
    }
}
