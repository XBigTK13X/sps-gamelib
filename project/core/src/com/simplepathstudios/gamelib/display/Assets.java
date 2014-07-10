package com.simplepathstudios.gamelib.display;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.Logger;

import java.util.HashMap;
import java.util.Map;

public class Assets {
    private static Assets instance;

    public static Assets get() {
        if (instance == null) {
            instance = new Assets();
        }
        return instance;
    }

    private final FontPack _fonts;

    private Map<String, Texture> _textures = new HashMap<>();

    private Assets() {
        _fonts = new FontPack();
        SpriteLoader.getIndexedSprites();
    }

    private FileHandle fragmentShader() {
        return new FileHandle(Loader.get().graphics("fragment.glsl"));
    }

    private FileHandle vertexShader() {
        return new FileHandle(Loader.get().graphics("vertex.glsl"));
    }

    public ShaderProgram defaultShaders() {
        ShaderProgram shaders = new ShaderProgram(vertexShader(), fragmentShader());
        if (!shaders.isCompiled()) {
            Logger.exception(new Exception("Shader compilation failed. GLSL log:\n\t" + shaders.getLog()));
        }
        return shaders;
    }

    public BitmapFont defaultFont() {
        return _fonts.getDefault();
    }

    public FontPack fontPack() {
        return _fonts;
    }

    public Texture image(String fileName) {
        if (!_textures.containsKey(fileName)) {
            _textures.put(fileName, new Texture(Loader.get().graphics(fileName).getAbsolutePath()));
        }
        return _textures.get(fileName);
    }

    public Sprite sprite(int verticalIndex) {
        return SpriteLoader.getIndexedSprites().get(verticalIndex).get(0);
    }

    public Sprite sprite(int frame, int index) {
        return SpriteLoader.getIndexedSprites().get(index).get(frame);
    }
}
