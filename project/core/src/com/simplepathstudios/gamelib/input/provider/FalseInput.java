package com.simplepathstudios.gamelib.input.provider;

import com.simplepathstudios.gamelib.bridge.Command;
import com.simplepathstudios.gamelib.bridge.Context;
import com.simplepathstudios.gamelib.input.PlayerIndex;
import com.simplepathstudios.gamelib.input.StateProvider;

public class FalseInput implements InputProvider {
    @Override
    public void setup(StateProvider stateProvider) {

    }

    @Override
    public boolean detectState(Command command, PlayerIndex playerIndex) {
        return false;
    }

    @Override
    public boolean isActive(Command command, PlayerIndex playerIndex, boolean failIfLocked) {
        return false;
    }

    @Override
    public boolean isActive(Command command, PlayerIndex playerIndex) {
        return false;
    }

    @Override
    public float getVector(Command command, PlayerIndex playerIndex) {
        return 0;
    }

    @Override
    public void setContext(Context context, PlayerIndex playerIndex) {

    }

    @Override
    public boolean isContext(Context context, PlayerIndex playerIndex) {
        return false;
    }

    @Override
    public boolean isLocked(Command command, PlayerIndex playerIndex) {
        return false;
    }

    @Override
    public void lock(Command command, PlayerIndex playerIndex) {

    }

    @Override
    public void unlock(Command command, PlayerIndex playerIndex) {

    }

    @Override
    public void update() {

    }

    @Override
    public int x() {
        return 0;
    }

    @Override
    public int y() {
        return 0;
    }

    @Override
    public boolean isMouseDown() {
        return false;
    }

    @Override
    public void setMouseLock(boolean locked) {
    }

    @Override
    public boolean isMouseDown(boolean failIfLocked) {
        return false;
    }

    @Override
    public void setValidCommands(Command... commands) {

    }

    @Override
    public void removeValidCommands() {

    }
}
