package sps.core;

public class NullLogger implements ILogger {
    @Override
    public void setLogFile(String name) {
    }

    @Override
    public void error(String message) {
    }

    @Override
    public void info(String message) {
    }

    @Override
    public void devConsole(String message) {
    }

    @Override
    public void exception(Exception e) {
    }

    @Override
    public void exception(Exception e, boolean exit) {
    }

    @Override
    public void exception(String s, Exception e) {
    }

    @Override
    public void exception(String s, Exception e, boolean exit) {
    }

    @Override
    public void metaData(String message) {
    }
}
