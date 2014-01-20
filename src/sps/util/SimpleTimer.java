package sps.util;

public class SimpleTimer {
    private long _elapsedTime;
    private long _lastTime;

    public void start() {
        start(false);
    }

    public void start(boolean reset) {
        if (reset) {
            _elapsedTime = 0;
        }
        _lastTime = System.currentTimeMillis();
    }

    public void stop() {
        _elapsedTime += System.currentTimeMillis() - _lastTime;

    }

    public String getDisplay() {
        return _elapsedTime + " milliseconds";
    }

    public long getElapsedTimeMillis(){
        return _elapsedTime;
    }
}
