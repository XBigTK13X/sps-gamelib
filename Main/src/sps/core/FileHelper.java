package sps.core;

import com.badlogic.gdx.files.FileHandle;

public class FileHelper {
    public static String[] readLines(FileHandle handle) {
        String contents = handle.readString();
        contents = contents.replaceAll("\r\n", "\n");
        String[] result = contents.split("\n");
        return result;
    }
}
