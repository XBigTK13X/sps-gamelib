package sps.playerselection;

import sps.bridge.Commands;
import sps.color.Color;
import sps.core.Point2;
import sps.display.Screen;
import sps.io.Input;
import sps.io.PlayerIndex;
import sps.text.Text;
import sps.text.TextPool;
import sps.data.GameConfig;

public class AvailableInput {
    private static int __columnMax = GameConfig.PlayersMax + 1;
    private PlayerIndex _playerIndex;
    private Text _display;
    private int _column;
    private int _inputCount;
    private boolean _active;
    private boolean _readyToStartGame;


    public AvailableInput(PlayerIndex index, int inputCount) {
        _playerIndex = index;
        _inputCount = inputCount;
        _display = TextPool.get().write(getMessage(), getPosition(_inputCount));
    }

    private Point2 getPosition(int inputCount) {
        return Screen.pos(15 + 10 * _column, 85 - 10 * inputCount);
    }

    private String getMessage() {
        return ((_playerIndex.KeyboardIndex == null) ? "C" + _playerIndex.ControllerIndex : "K" + _playerIndex.KeyboardIndex);
    }

    public void draw() {
    }

    public void update() {
        _display.setPosition((int) getPosition(_inputCount).X, (int) getPosition(_inputCount).Y);
        _display.setColor(_active ? Color.GREEN : Color.RED);

        if (!_active) {
            if (Input.get().isActive(Commands.get("MoveRight"), _playerIndex)) {
                Input.get().lock(Commands.get("MoveRight"), _playerIndex);
                _column++;
            }
            if (Input.get().isActive(Commands.get("MoveLeft"), _playerIndex)) {
                Input.get().lock(Commands.get("MoveLeft"), _playerIndex);
                _column--;
            }
        }
        if (_column != 0) {
            if (Input.get().isActive(Commands.get("Confirm"), _playerIndex)) {
                _active = true;
            }

            if (_active) {
                if (Input.get().isActive(Commands.get("Start"), _playerIndex)) {
                    _readyToStartGame = true;
                }
            }
        }
        if (Input.get().isActive(Commands.get("Cancel"), _playerIndex)) {
            _active = false;
            _readyToStartGame = false;
        }
        if (_column < 0) {
            _column = 0;
        }
        if (_column >= __columnMax) {
            _column = __columnMax - 1;
        }
    }

    public boolean ready() {
        return _readyToStartGame;
    }

    public boolean confirmed() {
        return _active;
    }

    public int getPlayerColumn() {
        return _column;
    }

    public PlayerIndex getPlayerIndex() {
        return _playerIndex;
    }
}
