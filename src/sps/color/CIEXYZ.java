package sps.color;

import sps.util.Maths;

//From http://www.codeproject.com/Articles/19045/Manipulating-colors-in-NET-Part-1#rgb2
public class CIEXYZ implements ColorSpec<CIEXYZ> {
    public static final CIEXYZ Empty = new CIEXYZ(0, 0, 0);
    public static final CIEXYZ D65 = new CIEXYZ(0.9505f, 1.0f, 1.0890f);

    public static CIEXYZ fromRGB(float red, float green, float blue) {
        // convert to a sRGB form
        double r = (red > 0.04045) ? Math.pow((red + 0.055) / (
                1 + 0.055), 2.2) : (red / 12.92);
        double g = (green > 0.04045) ? Math.pow((green + 0.055) / (
                1 + 0.055), 2.2) : (green / 12.92);
        double b = (blue > 0.04045) ? Math.pow((blue + 0.055) / (
                1 + 0.055), 2.2) : (blue / 12.92);

        // converts
        return new CIEXYZ(
                (float) (r * 0.4124 + g * 0.3576 + b * 0.1805),
                (float) (r * 0.2126 + g * 0.7152 + b * 0.0722),
                (float) (r * 0.0193 + g * 0.1192 + b * 0.9505)
        );
    }

    public float X;
    public float Y;
    public float Z;

    public CIEXYZ(float x, float y, float z) {
        this.X = (x > 0.9505f) ? 0.9505f : ((x < 0) ? 0 : x);
        this.Y = (y > 1.0f) ? 1.0f : ((y < 0) ? 0 : y);
        this.Z = (z > 1.089f) ? 1.089f : ((z < 0) ? 0 : z);
    }

    public Color toColor() {
        float[] Clinear = new float[3];
        Clinear[0] = X * 3.2410f - Y * 1.5374f - Z * 0.4986f; // red
        Clinear[1] = -X * 0.9692f + Y * 1.8760f - Z * 0.0416f; // green
        Clinear[2] = X * 0.0556f - Y * 0.2040f + Z * 1.0570f; // blue

        for (int i = 0; i < 3; i++) {
            Clinear[i] = (float) ((Clinear[i] <= 0.0031308) ? 12.92 * Clinear[i] : (
                    1 + 0.055) * Math.pow(Clinear[i], (1.0 / 2.4)) - 0.055);
        }

        return new RGBA(Clinear[0], Clinear[1], Clinear[2]).toColor();
    }

    public ColorSpec average(CIEXYZ target) {
        return lerp(50, target);
    }

    @Override
    public ColorSpec lerp(float startPercent, CIEXYZ target) {
        return new HSV(Maths.lerp(X, target.X, startPercent), Maths.lerp(Y, target.Y, startPercent), Maths.lerp(Z, target.Z, startPercent));
    }
}
