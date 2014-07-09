package com.simplepathstudios.gamelib.display;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simplepathstudios.gamelib.bridge.SpriteType;
import com.simplepathstudios.gamelib.bridge.SpriteTypes;
import com.simplepathstudios.gamelib.core.Loader;
import com.simplepathstudios.gamelib.core.Logger;
import com.simplepathstudios.gamelib.util.BoundingBox;
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

    private void cacheSprite(int index, int frame, Sprite sprite) {
        if (!_indexedSprites.containsKey(index)) {
            _indexedSprites.put(index, new HashMap<>());
        }
        if (!_indexedSprites.get(index).containsKey(frame)) {
            _indexedSprites.get(index).put(frame, sprite);
        }
    }

    private void scanSprites(String graphicsPath) {
        for (File spriteTile : Loader.get().graphics(graphicsPath).listFiles()) {
            switch (FilenameUtils.getExtension(spriteTile.getName())) {
                case "txt":
                    continue;
            }
            if (!spriteTile.isHidden()) {
                if (spriteTile.isDirectory()) {
                    scanSprites(new File(graphicsPath, spriteTile.getName()).getPath());
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
                cacheSprite(index, frame, sprite);
            }
        }
    }

    private int currentSheetIndex = -1;

    private BoundingBox getFrameBounds(JsonObject frame) {
        return BoundingBox.fromDimensions(frame.get("x").getAsInt(), frame.get("y").getAsInt(), frame.get("width").getAsInt(), frame.get("height").getAsInt());
    }

    private Sprite spriteJSONtoImage(Texture texture, JsonObject spriteJSON) {
        BoundingBox b = getFrameBounds(spriteJSON);
        TextureRegion region = new TextureRegion(texture, b.X, b.Y, b.Width, b.Height);
        return new Sprite(region);
    }

    private void parseSheet(String graphicsPath, JsonObject sheetJSON) {
        File spriteSheet = Loader.get().graphics(new File(graphicsPath, sheetJSON.get("file").getAsString()).getPath());
        String collection = sheetJSON.get("collection").getAsString();
        for (JsonElement spriteEl : sheetJSON.get("sprites").getAsJsonArray()) {
            JsonObject spriteJSON = spriteEl.getAsJsonObject();
            String name = spriteJSON.get("name").getAsString();

            String cacheName = collection + "." + name;
            currentSheetIndex--;
            _spriteNames.put(currentSheetIndex, cacheName);

            Texture texture = new Texture(new FileHandle(spriteSheet.getAbsoluteFile()));

            if (spriteJSON.has("frames")) {
                int frame = 0;
                for (JsonElement frameEl : spriteJSON.get("frames").getAsJsonArray()) {
                    Sprite sprite = spriteJSONtoImage(texture, frameEl.getAsJsonObject());
                    cacheSprite(currentSheetIndex, frame++, sprite);
                }
            }
            else {
                Sprite sprite = spriteJSONtoImage(texture, spriteJSON);
                cacheSprite(currentSheetIndex, 1, sprite);
            }
        }
    }

    private void scanSheets(String graphicsPath) {
        for (File sheetDesc : Loader.get().graphics(graphicsPath).listFiles()) {
            if (!sheetDesc.isHidden()) {
                if (sheetDesc.isDirectory()) {
                    scanSheets(new File(graphicsPath, sheetDesc.getName()).getPath());
                    continue;
                }
                if (FilenameUtils.getExtension(sheetDesc.getName()).equals("json")) {
                    try {
                        Logger.info("Parsing spritesheet JSON file: " + sheetDesc.getAbsolutePath());
                        JsonObject sheetJSON = JSON.getObject(FileUtils.readFileToString(sheetDesc));
                        parseSheet(graphicsPath, sheetJSON);
                    }
                    catch (Exception e) {
                        Logger.exception(e, false);
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
            //TODO Build metadata object into SpriteType
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
