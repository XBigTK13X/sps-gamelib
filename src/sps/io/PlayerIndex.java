package sps.io;

public class PlayerIndex {
    private Integer _playerIndex;
    public Integer KeyboardIndex;
    public Integer ControllerIndex;

    public PlayerIndex(int playerIndex, Integer keyboardIndex, Integer controllerIndex) {
        KeyboardIndex = keyboardIndex;
        ControllerIndex = controllerIndex;
        _playerIndex = playerIndex;
    }
}
