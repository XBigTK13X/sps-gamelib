package game.Android;

import com.badlogic.gdx.Gdx;
import sps.core.ILogger;
import sps.core.SpsConfig;

public class AndroidLogger implements ILogger {
    private static final String __tag = "SPSGame";

    @Override
    public void setLogFile(String name) {
    }

    @Override
    public void error(String message) {
        Gdx.app.error(__tag, message);
    }

    @Override
    public void info(String message) {
        Gdx.app.log(__tag, message);
    }

    @Override
    public void devConsole(String message) {
    }

    @Override
    public void exception(Exception e) {
        exception(e, true);
    }

    @Override
    public void exception(Exception e, boolean exit) {
        exception(e.getMessage(), e, exit);
    }

    @Override
    public void exception(String s, Exception e) {
        exception(s, e, true);
    }

    @Override
    public void exception(String s, Exception e, boolean exit) {
        Gdx.app.error(__tag, s, e);
        if (exit) {
            Gdx.app.exit();
        }
    }

    @Override
    public void metaData(String message) {
    }
}
