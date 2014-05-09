package com.simplepathstudios.gamelib.input.provider;

import com.simplepathstudios.gamelib.bridge.Command;
import com.simplepathstudios.gamelib.bridge.Context;
import com.simplepathstudios.gamelib.input.PlayerIndex;
import com.simplepathstudios.gamelib.input.StateProvider;

public interface InputProvider {
    void setup(StateProvider stateProvider);

    boolean detectState(Command command, PlayerIndex playerIndex);

    boolean isActive(Command command, PlayerIndex playerIndex, boolean failIfLocked);

    public boolean isActive(Command command, PlayerIndex playerIndex);

    public float getVector(Command command, PlayerIndex playerIndex);

    void setContext(Context context, PlayerIndex playerIndex);

    boolean isContext(Context context, PlayerIndex playerIndex);

    boolean isLocked(Command command, PlayerIndex playerIndex);

    void lock(Command command, PlayerIndex playerIndex);

    void unlock(Command command, PlayerIndex playerIndex);

    void update();

    int x();

    int y();

    boolean isMouseDown();

    void setMouseLock(boolean locked);

    boolean isMouseDown(boolean failIfLocked);

    void setValidCommands(Command... commands);

    void removeValidCommands();
}
