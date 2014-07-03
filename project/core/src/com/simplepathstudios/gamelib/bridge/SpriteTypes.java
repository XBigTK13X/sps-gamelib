package com.simplepathstudios.gamelib.bridge;

import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.display.SpriteDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteTypes {

    private static SpriteTypes instance;

    public static SpriteType get(String name) {
        return instance.resolve(name);
    }

    public static void add(SpriteType SpriteType) {
        if (instance == null) {
            instance = new SpriteTypes();
        }
        instance.put(SpriteType);
    }

    public static List<SpriteDefinition> getDefs() {
        return instance.getDefinitions();
    }

    private Map<String, SpriteType> spriteTypes = new HashMap<>();

    private SpriteTypes() {
    }

    public SpriteType resolve(String name) {
        if (!spriteTypes.containsKey(name)) {
            Logger.error("Unable to find sprite type: [" + name + "]");
            return null;
        }
        return spriteTypes.get(name);
    }

    public void put(SpriteType spriteType) {
        spriteTypes.put(spriteType.Name, spriteType);
    }

    private ArrayList<SpriteDefinition> Definitions;

    public List<SpriteDefinition> getDefinitions() {
        if (Definitions == null) {
            Definitions = new ArrayList<SpriteDefinition>();
            for (String s : spriteTypes.keySet()) {
                Definitions.add(new SpriteDefinition(spriteTypes.get(s), spriteTypes.get(s).Index, spriteTypes.get(s).Frames));
            }
        }
        return Definitions;
    }
}
