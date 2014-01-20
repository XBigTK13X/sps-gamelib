package sps.display;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import sps.core.Loader;

import java.util.HashMap;
import java.util.Map;

public class FontPack {
    Map<String, Map<Integer, BitmapFont>> _fonts;
    Map<String, String> _fontLabels;
    private int _defaultSize;

    private static final String __defaultLabel = "default";

    public FontPack() {
        _fonts = new HashMap<>();
        _fontLabels = new HashMap<>();
    }

    public BitmapFont getDefault() {
        return getFont(__defaultLabel, _defaultSize);
    }

    public int getDefaultPointSize() {
        return _defaultSize;
    }

    public void setDefault(String fontName, int pointSize) {
        _defaultSize = pointSize;
        cacheFont(__defaultLabel, fontName, pointSize);
    }

    public BitmapFont getFont(String label, Integer pointSize) {
        if (label == null) {
            return getDefault();
        }
        cacheFont(label, pointSize);
        return _fonts.get(label).get(pointSize);
    }

    private void cacheFont(String label, int pointSize) {
        cacheFont(label, _fontLabels.get(label), pointSize);
    }

    public void cacheFont(String label, String fontName, int pointSize) {
        if (_fonts.containsKey(label) && _fonts.get(label).containsKey(pointSize)) {
            return;
        }

        if (!_fontLabels.containsKey(label)) {
            if (fontName == null) {
                throw new RuntimeException("A font must be cached with a label and font name before it can be referenced.");
            }
            _fontLabels.put(label, fontName);
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(new FileHandle(Loader.get().font(fontName)));
        if (!_fonts.containsKey(label)) {
            _fonts.put(label, new HashMap<Integer, BitmapFont>());
        }
        if (!_fonts.get(label).containsKey(pointSize)) {
            BitmapFont font = generator.generateFont(pointSize);
            _fonts.get(label).put(pointSize, font);
        }
        generator.dispose();
    }
}
