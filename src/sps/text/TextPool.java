package sps.text;

import sps.color.Color;
import sps.core.Point2;
import sps.states.State;

import java.util.ArrayList;
import java.util.List;

public class TextPool {

    private static TextPool __instance;

    public static TextPool get() {
        if (__instance == null) {
            __instance = new TextPool();
        }
        return __instance;
    }

    public static void set(TextPool textPool) {
        __instance = textPool;
    }

    public static void reset() {
        __instance = new TextPool();
    }


    private List<Text> texts = new ArrayList<Text>();
    private int index = 0;

    private TextPool() {
        for (int ii = 0; ii < 10000; ii++) {
            texts.add(new Text());
        }
    }

    public void clear() {
        for (Text text : texts) {
            text.hide();
        }
    }

    public void clear(State state) {
        for (Text text : texts) {
            if (text.createdDuring(state)) {
                text.hide();
            }
        }
    }

    public Text write(String message, Point2 position) {
        return write(message, position, Text.NotTimed);
    }

    public Text write(String message, Point2 position, float lifeInSeconds) {
        return write(message, position, lifeInSeconds, TextEffects.None);
    }

    public Text write(String message, Point2 position, float lifeInSeconds, TextEffect effect, Color color, float scale) {
        return write(message, position, lifeInSeconds, null, 0, effect, color, scale);
    }

    public Text write(String message, Point2 position, float lifeInSeconds, String fontLabel, Integer fontPointSize, TextEffect effect, Color color, float scale) {
        Text result = texts.get(index);
        result.reset(position, message, fontLabel, fontPointSize, 1, lifeInSeconds, effect);
        result.setColor(color);
        result.setScale(scale);
        //FIXME A log entry when all texts are in use
        index = (index + 1) % texts.size();
        return result;
    }

    public Text write(String message, Point2 position, float lifeInSeconds, TextEffect effect) {
        return write(message, position, lifeInSeconds, effect, Color.WHITE, 1f);
    }

    public void draw() {
        for (Text text : texts) {
            if (text.isVisible()) {
                text.draw();
            }
        }
    }

    public void update() {
        for (Text text : texts) {
            if (text.isVisible()) {
                text.update();
            }
        }
    }
}
