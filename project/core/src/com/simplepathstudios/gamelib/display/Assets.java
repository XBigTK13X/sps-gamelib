package com.simplepathstudios.gamelib.display;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simplepathstudios.gamelib.bridge.SpriteType;
import com.simplepathstudios.gamelib.bridge.SpriteTypes;
import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.util.JSON;
import com.simplepathstudios.gamelib.util.Parse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
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

    private final HashMap<Integer, HashMap<Integer, Sprite>> _indexedSprites = new HashMap<>();
    private final HashMap<Integer, String> _spriteNames = new HashMap<>();

    private void scanSprites(String graphicsPath) {
        for (File spriteTile : Loader.get().graphics(graphicsPath).listFiles()) {
            switch(FilenameUtils.getExtension(spriteTile.getName())){
                case "txt":
                    continue;
            }
            if (!spriteTile.isHidden()) {
                if (spriteTile.isDirectory()) {
                    scanSprites(graphicsPath + '/' + spriteTile.getName());
                    continue;
                }
                String[] comps = spriteTile.getName().split("-");
                int index = Parse.inte(comps[0]);
                int frame = Parse.inte(comps[1]);
                String name = "";
                for (int ii = 1; ii < comps.length; ii++) {
                    String baseName = comps[ii].replace(".png", "");
                    name += Character.toUpperCase(baseName.charAt(0)) + baseName.substring(1) + " ";
                }
                name = name.substring(0, name.length() - 1);

                _spriteNames.put(index, name);
                Sprite sprite = new Sprite(new Texture(spriteTile.getAbsolutePath()));
                if (!_indexedSprites.containsKey(index)) {
                    _indexedSprites.put(index, new HashMap<>());
                }
                if (!_indexedSprites.get(index).containsKey(frame)) {
                    _indexedSprites.get(index).put(frame, sprite);
                }
            }
        }
    }

    private void scanSheets(String graphicsPath){
        for (File sheetDesc : Loader.get().graphics(graphicsPath).listFiles()) {
            if (!sheetDesc.isHidden()) {
                if (sheetDesc.isDirectory()) {
                    scanSheets(graphicsPath + '/' + sheetDesc.getName());
                    continue;
                }
                if(FilenameUtils.getExtension(sheetDesc.getName()).equals("json")){
                    try{
                        JsonObject sheetJSON = JSON.getObject(FileUtils.readFileToString(sheetDesc));
                        for(JsonElement sprite:sheetJSON.getAsJsonArray("sprites")){
                            JsonObject entry = sprite.getAsJsonObject();
                            //TODO Flesh out JSON parse
                        }
                    }
                    catch(Exception swallow){

                    }
                }
            }
        }
    }

    private Assets() {
        _fonts = new FontPack();

        scanSprites("sprites");
        scanSheets("sheets");

        for (Integer index : _indexedSprites.keySet()) {
            int frames = _indexedSprites.get(index).keySet().size();
            String id = _spriteNames.get(index);
            SpriteTypes.add(new SpriteType(id, index, frames));
        }
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
        return _indexedSprites.get(verticalIndex).get(0);
    }

    public Sprite sprite(int frame, int index) {
        return _indexedSprites.get(index).get(frame);
    }
}
