package com.simplepathstudios.gamelib.text;

import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.core.Point2;
import com.simplepathstudios.gamelib.states.GameSystem;
import com.simplepathstudios.gamelib.states.State;

import java.util.ArrayList;
import java.util.List;

public class TextPool implements GameSystem {
    private List<Text> texts = new ArrayList<Text>();
    private int index = 0;

    public TextPool() {
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

    @Override
    public void draw() {
        for (Text text : texts) {
            if (text.isVisible()) {
                text.draw();
            }
        }
    }

    @Override
    public void update() {
        for (Text text : texts) {
            if (text.isVisible()) {
                text.update();
            }
        }
    }
}
