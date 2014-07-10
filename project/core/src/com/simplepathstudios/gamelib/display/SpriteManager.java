package com.simplepathstudios.gamelib.display;

import java.util.HashMap;

public class SpriteManager {
    private static final HashMap<String, SpriteDefinition> __manager = new HashMap<>();

    public static SpriteDefinition get(String spriteName) {
        return __manager.get(spriteName);
    }

    public static void add(SpriteDefinition spriteDefinition) {
        __manager.put(spriteDefinition.Name, spriteDefinition);
    }
}
