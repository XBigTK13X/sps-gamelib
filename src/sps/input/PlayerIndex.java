package sps.input;

public class PlayerIndex {
    private Integer _playerIndex;
    public Integer KeyboardIndex;
    public Integer GamepadIndex;
    public String GamepadType;

    public PlayerIndex(int playerIndex, Integer keyboardIndex, Integer gamepadIndex, String gamepadType) {
        KeyboardIndex = keyboardIndex;
        GamepadIndex = gamepadIndex;
        GamepadType = gamepadType;
        _playerIndex = playerIndex;
    }
}
