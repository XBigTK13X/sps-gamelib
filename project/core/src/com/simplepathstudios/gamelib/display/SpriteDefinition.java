package com.simplepathstudios.gamelib.display;

import com.simplepathstudios.gamelib.bridge.Sps;
import com.simplepathstudios.gamelib.data.SpsConfig;

public class SpriteDefinition {
    public final String Name;
    public final int Width;
    public final int Height;
    public final int Index;
    public final int MaxFrame;
    public final float TimeSeconds;

    public SpriteDefinition(String name, int index, int maxFrame) {
        this(name, index, maxFrame, SpsConfig.get().spriteWidth, SpsConfig.get().spriteHeight, Sps.AnimationFps);
    }

    public SpriteDefinition(String name, int index, int maxFrame, int width, int height, float timeSeconds) {
        Name = name;
        Width = width;
        Height = height;
        Index = index;
        MaxFrame = maxFrame;
        TimeSeconds = timeSeconds;
    }
}
