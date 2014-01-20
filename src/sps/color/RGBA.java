package sps.color;

import com.badlogic.gdx.utils.NumberUtils;
import sps.util.Maths;

public class RGBA implements ColorSpec<RGBA> {
    public static RGBA fromRGB(float r, float g, float b) {
        return new RGBA(r, g, b, 1f);
    }

    private static float __base = 255f;

    public float R;
    public float G;
    public float B;
    public float A;

    public RGBA(int r, int g, int b) {
        this(r, g, b, (int) __base);
    }

    public RGBA(int r, int g, int b, int a) {
        this(r / __base, g / __base, b / __base, a / __base);
    }

    public RGBA(float r, float g, float b) {
        this(r, g, b, 1f);
    }

    public RGBA(float r, float g, float b, float a) {
        this.R = r;
        this.G = g;
        this.B = b;
        this.A = a;
    }

    @Override
    public Color toColor() {
        return new Color(R, G, B, A);
    }

    @Override
    public ColorSpec average(RGBA target) {
        return lerp(50, target);
    }

    @Override
    public ColorSpec lerp(float startPercent, RGBA target) {
        return new RGBA(Maths.lerp(R, target.R, startPercent), Maths.lerp(G, target.G, startPercent), Maths.lerp(B, target.B, startPercent));
    }

    private RGBA comp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        comp = (RGBA) o;
        return R == comp.R && G == comp.G && B == comp.B && A == comp.A;
    }

    @Override
    public int hashCode() {
        int result = (R != +0.0f ? NumberUtils.floatToIntBits(R) : 0);
        result = 31 * result + (G != +0.0f ? NumberUtils.floatToIntBits(G) : 0);
        result = 31 * result + (B != +0.0f ? NumberUtils.floatToIntBits(B) : 0);
        result = 31 * result + (A != +0.0f ? NumberUtils.floatToIntBits(A) : 0);
        return result;
    }
}
