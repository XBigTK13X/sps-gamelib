package sps.core;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Loader {

    private static Loader instance;

    public static Loader get() {
        if (instance == null) {
            instance = new Loader();
        }
        return instance;
    }

    private final String root = "assets";

    private final String graphics = "graphics";
    private final String data = "data";
    private final String music = "music";
    private final String sound = "sound";
    private final String font = "font";

    private File get(String dir, String target) {
        return new File(root + "/" + dir + "/" + target);
    }

    public File data(String target) {
        return get(data, target);
    }

    public File graphics(String target) {
        return get(graphics, target);
    }

    public File music(String target) {
        return get(music, target);
    }

    public File sound(String target) {
        return get(sound, target);
    }

    public File font(String target) {
        return get(graphics + "/" + font, target);
    }

    private Map<String, File> _userSaveDirs = new HashMap<>();

    private void createUserSaveDir(String game) {
        String path = System.getProperty("user.home");
        switch (DesktopApi.getOs()) {
            case windows:
                path = new File(path, "My Documents/My Games/").getAbsolutePath();
                break;
            case macos:
                path = new File(path, "Library/Application Support/").getAbsolutePath();
                break;
            default:
                path = new File(path, ".local/share/").getAbsolutePath();
                break;
        }
        path = new File(path, game).getAbsolutePath();
        File userSaveDir = new File(path);
        if (!userSaveDir.exists()) {
            try {
                FileUtils.forceMkdir(userSaveDir);
            }
            catch (IOException e) {
                Logger.exception(e);
            }
        }
        _userSaveDirs.put(game, userSaveDir);
    }

    public File userSave(String game, String target) {
        if (!_userSaveDirs.containsKey(game)) {
            createUserSaveDir(game);
        }
        return new File(_userSaveDirs.get(game), target);
    }
}
