package com.simplepathstudios.gamelib.display.render;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.DrawDepth;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.display.Point2;
import com.simplepathstudios.gamelib.display.Assets;
import com.simplepathstudios.gamelib.display.DrawApiCall;
import com.simplepathstudios.gamelib.particle.ParticleLease;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderScheduler {
    private List<RenderApiCall> _renderApiCalls;
    private List<DrawApiCall> _drawApiCalls;

    public RenderScheduler() {
        _drawApiCalls = new ArrayList<>();
        _renderApiCalls = new ArrayList<>();
    }

    public List<DrawApiCall> getDrawApiCalls() {
        return _drawApiCalls;
    }

    public List<RenderApiCall> getRenderApiCalls() {
        return _renderApiCalls;
    }

    public void schedule(DrawApiCall apiCall) {
        _drawApiCalls.add(apiCall);
    }

    public void schedule(Sprite sprite, DrawDepth depth) {
        _renderApiCalls.add(new RenderApiCall(sprite, depth));
    }

    public void schedule(String content, Point2 location, Color filter, String fontLabel, int pointSize, float scale, DrawDepth depth) {
        if (content.contains("\n")) {
            int line = 0;
            if (pointSize == 0) {
                pointSize = Assets.get().fontPack().getDefaultPointSize();
            }
            for (String s : content.split("\n")) {
                renderLine(s, location.add(0, line++ * -pointSize), filter, fontLabel, pointSize, scale, depth);
            }
        }
        else {
            renderLine(content, location, filter, fontLabel, pointSize, scale, depth);
        }
    }

    private void renderLine(String content, Point2 location, Color filter, String fontLabel, int pointSize, float scale, DrawDepth depth) {
        _renderApiCalls.add(new RenderApiCall(content, location, filter, fontLabel, pointSize, scale, depth));
    }

    public void schedule(ParticleLease lease) {
        _renderApiCalls.add(new RenderApiCall(lease));
    }

    public void clear() {
        _renderApiCalls.clear();
        _drawApiCalls.clear();
    }

    public void sort() {
        Collections.sort(_renderApiCalls);
    }
}
