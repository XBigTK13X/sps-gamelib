package sps.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Loader {

    private static Loader instance;

    public static Loader get() {
        if (instance == null) {
            instance = new Loader();
        }
        return instance;
    }

    private final String graphics = "graphics";
    private final String data = "data";
    private final String music = "music";
    private final String sound = "sound";
    private final String font = "font";

    public FileHandle asset(String dir, String target) {
        String path = dir + ((target.isEmpty() || target == null) ? "" : "/" + target);
        try {
            return Gdx.files.internal(path);
        }
        catch (Exception e) {
            return new FileHandle(path);
        }
    }

    public FileHandle assetDir(String dirName) {
        return asset(dirName, "");
    }

    public FileHandle data(String target) {
        return asset(data, target);
    }

    public FileHandle graphics(String target) {
        return asset(graphics, target);
    }

    public FileHandle music(String target) {
        return asset(music, target);
    }

    public FileHandle sound(String target) {
        return asset(sound, target);
    }

    public FileHandle font(String target) {
        return asset(graphics + "/" + font, target);
    }

    public FileHandle save(String target) {
        return data("save/" + target);
    }
}
