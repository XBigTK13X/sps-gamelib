package sps.util;

import org.apache.commons.io.FileUtils;
import sps.core.Logger;

import java.io.File;

public class GameMonitor extends Thread {
    private static int __millisecondsWait = 200;
    private static int _updateCount;

    public static void keepAlive() {
        _updateCount++;
    }

    private static GameMonitor _monitor;

    public static void monitor(final File crashOutput, final String errorMessage, final int timeoutMilliseconds) {
        if (_monitor == null) {
            _monitor = new GameMonitor(crashOutput, errorMessage, timeoutMilliseconds);
            _monitor.start();
        }
        else {
            Logger.error("Only one monitor can exist per game");
        }
    }

    private int _maxStalledMilliseconds;
    private int _stalledMilliseconds;
    private int _lastUpdateCount;
    private File _crashOutput;
    private String _message;

    private GameMonitor(final File crashOutput, final String message, final int timeoutMilliseconds) {
        _stalledMilliseconds = _maxStalledMilliseconds = timeoutMilliseconds;
        _message = message;
        _crashOutput = crashOutput;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(__millisecondsWait);
                if (_updateCount == _lastUpdateCount) {
                    _stalledMilliseconds -= __millisecondsWait;
                    if (_stalledMilliseconds <= 0) {
                        Logger.error("The game appears to have frozen for at least " + _maxStalledMilliseconds + " milliseconds. Forcing shutdown and recording the failure.");

                        Logger.error(_message);
                        try {
                            FileUtils.writeStringToFile(_crashOutput, _message);
                        }
                        catch (Exception e) {
                            Logger.exception(e, false);
                        }
                        System.exit(1);
                        return;
                    }
                }
                else {
                    _lastUpdateCount = _updateCount;
                    _stalledMilliseconds = _maxStalledMilliseconds;
                }
            }
            catch (Exception e) {
                Logger.exception(e, false);
            }
        }
    }
}
