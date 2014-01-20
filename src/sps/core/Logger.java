package sps.core;

import com.badlogic.gdx.Gdx;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Logger {
    private static enum Handler {
        Default,
        Error,
        Exception,
        MetaData;

        private File Path;

        private Handler() {
            if (!__dir.exists()) {
                try {
                    FileUtils.forceMkdir(__dir);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Unable to create logging directory");
                }
            }
            setName(name());
        }

        private File makeLog(String name) {
            return new File(__dir.getName() + "/" + name + ".log");
        }

        public void setName(String name) {
            name = name.replace(".log", "");
            if (__dir.exists()) {
                Path = makeLog(name);
                if (Path.exists()) {
                    try {
                        FileUtils.moveFile(Path, new File(Path.getAbsolutePath().replace(".log", "." + UUID.randomUUID() + ".log")));
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Unable to move old log file");
                    }
                }
            }
        }

        public File get() {
            return Path;
        }

        private String format(String message) {
            return message;
        }

        public void log(String message) {
            System.out.println(format(message));
            try {
                FileUtils.writeStringToFile(Path, format(message) + "\n", true);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static File __dir = new File("logs");

    public static void setLogFile(String name) {
        Handler.Default.setName(name);
        Handler.Default.log("Logging to -> " + Handler.Default.get().getAbsoluteFile());
    }

    public static void error(String message) {
        Handler.Error.log(message);
    }

    public static void info(String message) {
        Handler.Default.log(message);
    }

    public static void exception(Exception e) {
        exception(e, true);
    }

    public static void exception(Exception e, boolean exit) {
        Handler.Default.log("Exception logged by SPS. Please see the exception log file.");
        Handler.Exception.log("Exception logged by SPS: " + e.toString());
        if (e.getCause() != null) {
            Handler.Exception.log(e.getCause().getMessage());
        }
        for (StackTraceElement el : e.getStackTrace()) {
            Handler.Exception.log("  " + el.toString());
        }
        if (exit) {
            try {
                Gdx.app.exit();
            }
            catch (Exception swallow) {
            }
            System.exit(-1);
        }
    }

    public static void exception(String s, Exception e) {
        Handler.Default.log(s);
        Handler.Exception.log(s);
        exception(e, true);
    }

    public static void exception(String s, Exception e, boolean exit) {
        Handler.Default.log(s);
        Handler.Exception.log(s);
        exception(e, exit);
    }

    public static void metaData(String message) {
        Handler.MetaData.log(message);
    }
}
