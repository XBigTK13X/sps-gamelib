package sps.playerselection;

import sps.display.Screen;
import sps.states.Systems;
import sps.text.Text;
import sps.text.TextPool;

public class AvailablePlayer {
    private int _playerId;
    private AvailableInput _input;

    private Text _display;

    public AvailablePlayer(int playerId) {
        _playerId = playerId;

        _display = Systems.get(TextPool.class).write(getMessage(), Screen.pos(25 + 10 * _playerId, 95));
    }

    private String getMessage() {
        return "P" + (_playerId + 1);
    }

    public void setInput(AvailableInput input) {
        _input = input;
    }

    public void draw() {

    }

    public void update() {

    }
}
