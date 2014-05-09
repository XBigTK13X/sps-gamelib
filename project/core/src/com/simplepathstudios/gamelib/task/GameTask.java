package com.simplepathstudios.gamelib.task;

import com.simplepathstudios.gamelib.time.CoolDown;

public abstract class GameTask {
    private static final int PERPETUAL = -777;

    private CoolDown _timer;
    private String _name;
    private int _repetitions = 0;
    private float _lengthInSeconds;

    private boolean _startSuccess = false;
    private boolean _active;
    private boolean _cancelled;

    private GameTask _onComplete;
    private GameTask _onCancel;

    private boolean _eachFrameTask;

    public GameTask(String name) {
        this(name, 0f);
    }

    public GameTask(String name, float lengthInSeconds) {
        setLengthInSeconds(lengthInSeconds);
        _name = name;
        _active = true;
        _repetitions = 1;
    }

    public void makeEachFrameTask(){
        _eachFrameTask = true;
    }

    public boolean hasActionEachFrame(){
        return _eachFrameTask;
    }

    public void setLengthInSeconds(float lengthInSeconds) {
        _lengthInSeconds = lengthInSeconds;
        _timer = new CoolDown(_lengthInSeconds);
    }

    public void setRepetitions(int repetitions) {
        _repetitions = repetitions;
    }

    public void makePerpetual() {
        _repetitions = PERPETUAL;
    }

    public void cancel() {
        _cancelled = true;
    }

    public boolean isCancelled() {
        return _cancelled;
    }

    public boolean isActive() {
        return _active && _startSuccess && !_cancelled;
    }

    public String getName() {
        return _name;
    }

    public void setStartSuccess(boolean success) {
        _startSuccess = success;
    }

    public void setActive(boolean active) {
        _active = active;
    }

    public float getLengthInSeconds() {
        return _lengthInSeconds;
    }

    public void setOnComplete(GameTask task) {
        _onComplete = task;
    }

    public GameTask onComplete() {
        return _onComplete;
    }

    public void setOnCancel(GameTask task) {
        _onCancel = task;
    }

    public GameTask onCancel() {
        return _onCancel;
    }

    public void update() {
        if(_eachFrameTask){
            if (updateAction() == true) {
                _active = false;
            }
        }
        else {
            if (_timer.updateAndCheck()) {
                if (_repetitions > 1 || _repetitions == PERPETUAL) {
                    _active = true;
                    if (_repetitions > 0) {
                        _repetitions--;
                    }
                    if (onComplete() != null) {
                        onComplete().start();
                    }
                    start();
                }
                else {
                    _active = false;
                }
            }
        }
    }

    public boolean updateAction() {
        return true;
    }

    public abstract boolean start();
}
