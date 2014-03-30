package sps.input;

public class PlayerIndex {
    public Integer PlayerIndex;
    public Integer KeyboardIndex;
    public Integer GamepadIndex;
    public String GamepadType;

    public PlayerIndex(int playerIndex, Integer keyboardIndex, Integer gamepadIndex, String gamepadType) {
        KeyboardIndex = keyboardIndex;
        GamepadIndex = gamepadIndex;
        GamepadType = gamepadType;
        PlayerIndex = playerIndex;
    }
}
