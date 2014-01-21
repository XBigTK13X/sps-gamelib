package sps.data;

import org.apache.commons.io.FileUtils;
import sps.core.Logger;
import sps.util.Scrambler;

import java.io.File;

public class Persistence {
    private static Persistence __instance;

    private static final File __autoSave = UserFiles.save();

    public static void setup(GameStateHandler handler) {
        __instance = new Persistence(handler);
    }

    public static Persistence get() {
        return __instance;
    }

    private Thread _worker;
    private boolean _saveFileIsBad = false;
    private GameStateHandler _handler;

    private Persistence(GameStateHandler handler) {
        _handler = handler;
    }

    public boolean isBusy() {
        return _worker != null && _worker.isAlive();
    }

    public void autoSave() {
        if (!isBusy()) {
            _worker = new Thread() {
                @Override
                public void run() {
                    try {
                        String scrambled = Scrambler.scramble(_handler.getPersistable());
                        FileUtils.writeStringToFile(__autoSave, scrambled);
                        _saveFileIsBad = false;
                    }
                    catch (Exception e) {
                        Logger.exception(e);
                    }
                }
            };
            _worker.start();
        }
    }

    public GameState autoLoad() {
        if (saveFileExists() && !isBusy()) {
            try {
                String plainText = Scrambler.descramble(FileUtils.readFileToString(__autoSave));
                GameState state = _handler.loadFromPersistable(plainText);
                if (state.getSaveFormatVersion() != _handler.getCurrentSaveFormatVersion()) {
                    throw new RuntimeException("Save game version mismatch. Recorded version is " + state.getSaveFormatVersion() + " but the current version is " + _handler.getCurrentSaveFormatVersion());
                }
                _saveFileIsBad = false;
                return state;
            }
            catch (Exception e) {
                _saveFileIsBad = true;
                Logger.exception(e, false);
                return null;
            }
        }
        else {
            throw new RuntimeException("Save file does not exist. Checked for: " + __autoSave.getAbsolutePath());
        }
    }

    public boolean saveFileExists() {
        return __autoSave.exists();
    }

    public boolean isSaveBad() {
        return _saveFileIsBad;
    }
}
