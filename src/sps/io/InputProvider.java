package sps.io;

import sps.bridge.Command;
import sps.bridge.Context;

public interface InputProvider {
    void setup(StateProvider stateProvider);

    boolean detectState(Command command, PlayerIndex playerIndex);

    boolean isActive(Command command, PlayerIndex playerIndex, boolean failIfLocked);

    public boolean isActive(Command command, PlayerIndex playerIndex);

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
}
