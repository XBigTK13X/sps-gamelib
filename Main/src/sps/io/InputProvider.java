package sps.io;

import sps.bridge.Command;
import sps.bridge.Context;

public interface InputProvider {
    void setup(StateProvider stateProvider);

    boolean detectState(Command command, int playerIndex);

    boolean isActive(Command command, int playerIndex, boolean failIfLocked);

    public boolean isActive(Command command);

    public boolean isActive(Command command, int playerIndex);

    void setContext(Context context, int playerIndex);

    boolean isContext(Context context, int playerIndex);

    boolean isLocked(Command command, int playerIndex);

    void lock(Command command, int playerIndex);

    void unlock(Command command, int playerIndex);

    void update();

    int x();

    int y();

    boolean isMouseDown();
}
