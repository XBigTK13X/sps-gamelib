package com.simplepathstudios.gamelib.display.render;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.bridge.DrawDepth;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.display.Point2;
import com.simplepathstudios.gamelib.particle.ParticleLease;

public class RenderApiCall implements Comparable<RenderApiCall> {
    public String Content;
    public Point2 Location;
    public Color Filter;
    public int PointSize;
    public String FontLabel;
    public float Scale;
    public DrawDepth Depth;
    public ParticleLease Particles;

    public RenderApiCall(String content, Point2 location, Color filter, String fontLabel, int pointSize, float scale, DrawDepth depth) {
        Content = content;
        Location = location;
        Filter = filter;
        PointSize = pointSize;
        FontLabel = fontLabel;
        Scale = scale;
        Depth = depth;
    }

    public com.badlogic.gdx.graphics.g2d.Sprite Sprite;

    public RenderApiCall(Sprite sprite, DrawDepth depth) {
        Sprite = new Sprite(sprite);
        Depth = depth;
    }

    public RenderApiCall(ParticleLease particles) {
        Particles = particles;
        Depth = particles.Depth;
    }

    @Override
    public int compareTo(RenderApiCall o) {
        return Depth.DrawDepth - o.Depth.DrawDepth;
    }
}
