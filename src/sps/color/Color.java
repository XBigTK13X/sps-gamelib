package sps.color;

import com.badlogic.gdx.utils.NumberUtils;
import sps.util.Maths;

//Bitwise color operations taken from: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/Color.java
public class Color {
    protected static final float COLOR_DEPTH = 255f; ///32 - 8 bits per channel (RGBA)

    public static final Color CLEAR = new Color(0, 0, 0, 0);
    public static final Color WHITE = new Color(1, 1, 1, 1);
    public static final Color BLACK = new Color(0, 0, 0, 1);
    public static final Color RED = new Color(1, 0, 0, 1);
    public static final Color GREEN = new Color(0, 1, 0, 1);
    public static final Color BLUE = new Color(0, 0, 1, 1);
    public static final Color LIGHT_GRAY = new Color(0.75f, 0.75f, 0.75f, 1);
    public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1);
    public static final Color DARK_GRAY = new Color(0.25f, 0.25f, 0.25f, 1);
    public static final Color PINK = new Color(1, 0.68f, 0.68f, 1);
    public static final Color ORANGE = new Color(1, 0.78f, 0, 1);
    public static final Color YELLOW = new Color(1, 1, 0, 1);
    public static final Color MAGENTA = new Color(1, 0, 1, 1);
    public static final Color CYAN = new Color(0, 1, 1, 1);

    public final float r;
    public final float g;
    public final float b;
    public final float a;

    protected final com.badlogic.gdx.graphics.Color _gdxColor;

    public Color(float r, float g, float b, float a) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
        this.a = clamp(a);
        _gdxColor = new com.badlogic.gdx.graphics.Color(this.r, this.g, this.b, this.a);
    }

    public Color(Color color) {
        this(color.r, color.g, color.b, color.a);
    }

    public Color(int rgba8888) {
        this(((rgba8888 & 0xff000000) >>> 24) / COLOR_DEPTH, ((rgba8888 & 0x00ff0000) >>> 16) / COLOR_DEPTH, ((rgba8888 & 0x0000ff00) >>> 8) / COLOR_DEPTH, ((rgba8888 & 0x000000ff)) / COLOR_DEPTH);
    }

    public Color mul(Color target) {
        return new Color(r * target.r, g * target.g, b * target.b, a * target.a);
    }

    public Color newRed(float red) {
        return new Color(red, g, b, a);
    }

    public Color newGreen(float green) {
        return new Color(r, green, b, a);
    }

    public Color newBlue(float blue) {
        return new Color(r, g, blue, a);
    }

    public Color newAlpha(float alpha) {
        return new Color(r, g, b, alpha);
    }

    protected float clamp(float value) {
        return Maths.clamp(value, 0.0f, 1.0f);
    }

    public com.badlogic.gdx.graphics.Color getGdxColor() {
        return _gdxColor;
    }

    public int rgb888() {
        return ((int) (r * COLOR_DEPTH) << 16) | ((int) (g * COLOR_DEPTH) << 8) | (int) (b * COLOR_DEPTH);
    }

    public int rgba8888() {
        return ((int) (r * COLOR_DEPTH) << 24) | ((int) (g * COLOR_DEPTH) << 16) | ((int) (b * COLOR_DEPTH) << 8) | (int) (a * COLOR_DEPTH);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return rgba8888() == color.rgba8888();
    }

    @Override
    public int hashCode() {
        int result = (r != +0.0f ? NumberUtils.floatToIntBits(r) : 0);
        result = 31 * result + (g != +0.0f ? NumberUtils.floatToIntBits(g) : 0);
        result = 31 * result + (b != +0.0f ? NumberUtils.floatToIntBits(b) : 0);
        result = 31 * result + (a != +0.0f ? NumberUtils.floatToIntBits(a) : 0);
        return result;
    }
}
