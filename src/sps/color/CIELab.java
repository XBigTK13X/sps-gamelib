package sps.color;

import sps.util.Maths;

//From http://www.codeproject.com/Articles/19045/Manipulating-colors-in-NET-Part-1#rgb2
public class CIELab implements ColorSpec<CIELab> {
    public static final CIELab Empty = new CIELab(0, 0, 0);

    public static CIELab fromRGB(float red, float green, float blue) {
        CIEXYZ xyz = CIEXYZ.fromRGB(red, green, blue);
        return fromXYZ(xyz.X, xyz.Y, xyz.Z);
    }

    public static CIELab fromXYZ(float x, float y, float z) {
        CIELab lab = CIELab.Empty;

        lab.L = 116.0f * Fxyz(y / CIEXYZ.D65.Y) - 16f;
        lab.A = 500.0f * (Fxyz(x / CIEXYZ.D65.X) - Fxyz(y / CIEXYZ.D65.Y));
        lab.B = 200.0f * (Fxyz(y / CIEXYZ.D65.Y) - Fxyz(z / CIEXYZ.D65.Z));

        return lab;
    }

    private static float Fxyz(float t) {
        return (float) ((t > 0.008856f) ? Math.pow(t, (1.0f / 3.0f)) : (7.787f * t + 16.0f / 116.0f));
    }

    public float L;
    public float A;
    public float B;

    public CIELab(float l, float a, float b) {
        this.L = l;
        this.A = a;
        this.B = b;
    }

    public Color toColor() {
        return toXYZ().toColor();
    }

    public ColorSpec average(CIELab target) {
        return lerp(50, target);
    }

    @Override
    public ColorSpec lerp(float startPercent, CIELab target) {
        return new CIELab(Maths.lerp(L, target.L, startPercent), Maths.lerp(A, target.A, startPercent), Maths.lerp(B, target.B, startPercent));
    }

    public CIEXYZ toXYZ() {
        double delta = 6.0 / 29.0;

        double fy = (L + 16) / 116.0;
        double fx = fy + (A / 500.0);
        double fz = fy - (B / 200.0);

        return new CIEXYZ(
                (float) ((fx > delta) ? CIEXYZ.D65.X * (fx * fx * fx) : (fx - 16.0 / 116.0) * 3 * (
                        delta * delta) * CIEXYZ.D65.X),
                (float) ((fy > delta) ? CIEXYZ.D65.Y * (fy * fy * fy) : (fy - 16.0 / 116.0) * 3 * (
                        delta * delta) * CIEXYZ.D65.Y),
                (float) ((fz > delta) ? CIEXYZ.D65.Z * (fz * fz * fz) : (fz - 16.0 / 116.0) * 3 * (
                        delta * delta) * CIEXYZ.D65.Z)
        );
    }


}