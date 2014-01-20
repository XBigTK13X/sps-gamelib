package sps.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import sps.core.Loader;
import sps.core.SpsConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SoundPlayer {
    private static SoundPlayer __instance;

    public static SoundPlayer get() {
        if (__instance == null) {
            __instance = new SoundPlayer();
        }
        return __instance;
    }

    private ExecutorService soundExecutor = Executors.newSingleThreadExecutor();
    private Map<String, Sound> _sounds = new HashMap<>();

    private SoundPlayer() {
    }

    public void add(String id, String nameWithExtension) {
        _sounds.put(id, Gdx.audio.newSound(new FileHandle(Loader.get().sound(nameWithExtension))));
    }

    public void play(final String soundId) {
        if (SpsConfig.get().soundEnabled) {
            soundExecutor.submit(new Runnable() {
                public void run() {
                    _sounds.get(soundId).play();
                }
            });
        }
    }

    public void dispose() {
        for (String id : _sounds.keySet()) {
            _sounds.get(id).dispose();
        }
    }
}
