package sps.core;

import java.io.File;

public class Logger {
    private static ILogger __impl = new NullLogger();

    public static void set(ILogger instance) {
        __impl = instance;
    }

    private static File __dir = new File("logs");

    public static void setLogFile(String name) {
        __impl.setLogFile(name);
    }

    public static void error(String message) {
        __impl.error(message);
    }

    public static void info(String message) {
        __impl.info(message);
    }

    public static void devConsole(String message) {
        __impl.devConsole(message);
    }

    public static void exception(Exception e) {
        __impl.exception(e);
    }

    public static void exception(Exception e, boolean exit) {
        __impl.exception(e, exit);
    }

    public static void exception(String s, Exception e) {
        __impl.exception(s, e);
    }

    public static void exception(String s, Exception e, boolean exit) {
        __impl.exception(s, e, exit);
    }

    public static void metaData(String message) {
        __impl.metaData(message);
    }
}
