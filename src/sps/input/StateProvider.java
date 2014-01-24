package sps.input;

import sps.bridge.Command;

public interface StateProvider {
    public boolean isActive(Command command, PlayerIndex playerIndex);

    public void setState(Command command, PlayerIndex playerIndex, boolean isActive);

    public void pollLocalState();
}
