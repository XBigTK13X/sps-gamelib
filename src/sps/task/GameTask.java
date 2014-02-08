package sps.task;

import sps.time.CoolDown;

public abstract class GameTask {
    private CoolDown _timer;
    private String _name;
    private boolean _active;
    private boolean _startSuccess = false;
    private boolean _perpetual;

    public GameTask(String name, float lengthInSeconds) {
        _timer = new CoolDown(lengthInSeconds);
        _name = name;
        _active = true;
    }

    public void update() {
        if (_timer.updateAndCheck()) {
            _active = false || _perpetual;
            if (_perpetual) {
                complete();
                start();
            }
        }
    }

    public boolean isActive() {
        return _active && _startSuccess;
    }

    public String getName() {
        return _name;
    }

    public void setStartSuccess(boolean success) {
        _startSuccess = success;
    }

    public void setPerpetual(boolean perpetual) {
        _perpetual = perpetual;
    }

    public void setActive(boolean active) {
        _active = active;
    }

    public abstract boolean start();

    public abstract boolean cancel();

    public abstract boolean complete();
}
