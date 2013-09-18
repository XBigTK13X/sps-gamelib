package sps.core;

import com.badlogic.gdx.Gdx;

public interface ILogger {
    public void setLogFile(String name);

    public void error(String message);

    public void info(String message);

    public void devConsole(String message);

    public void exception(Exception e);

    public void exception(Exception e, boolean exit);

    public void exception(String s, Exception e);

    public void exception(String s, Exception e, boolean exit);

    public void metaData(String message);
}
