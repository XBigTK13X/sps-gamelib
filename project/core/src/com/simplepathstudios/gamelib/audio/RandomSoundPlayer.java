package com.simplepathstudios.gamelib.audio;

import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.RNG;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomSoundPlayer {
    private static Map<String,List<String>> __sounds= new HashMap<>();

    public static void setup(String collectionId) {
        __sounds.put(collectionId,new ArrayList<String>());

        for (File sound : Loader.get().sound(collectionId).listFiles()) {
            String id = FilenameUtils.removeExtension(sound.getName());
            SoundPlayer.get().add(id, collectionId + "/" + sound.getName());
            __sounds.get(collectionId).add(id);
        }
    }

    public static void play(String collectionId) {
        SoundPlayer.get().play(RNG.pick(__sounds.get(collectionId)));
    }
}
