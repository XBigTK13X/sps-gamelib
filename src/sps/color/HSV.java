package sps.color;

import sps.util.Maths;

public class HSV implements ColorSpec<HSV> {
    public static HSV fromColor(Color color) {
        return fromRGB(color.r, color.g, color.b);
    }

    public static HSV fromRGB(float r, float g, float b) {
        float[] rawHSV = new float[3];
        java.awt.Color.RGBtoHSB((int) (r * 255), (int) (g * 255), (int) (b * 255), rawHSV);

        HSV hsv = new HSV(rawHSV[0] * 360f, rawHSV[1], rawHSV[2]);
        hsv.clamp();
        return hsv;
    }

    //Range: 0f -> 360f
    public float H;
    //Range: 0f -> 1f
    public float S;
    //Range: 0f -> 1f
    public float V;

    public HSV() {
        this(0f, 0f, 0f);
    }

    public HSV(float hue, float saturation, float value) {
        H = hue;
        S = saturation;
        V = value;
        clamp();
    }

    private void clamp() {
        H = Maths.clamp(H, 0f, 360f);
        S = Maths.clamp(S, 0f, 1f);
        V = Maths.clamp(V, 0f, 1f);
    }

    public Color toColor() {
        clamp();
        java.awt.Color tmp = new java.awt.Color(java.awt.Color.HSBtoRGB(H / 360, S, V));
        return new Color(tmp.getRed() / 255f, tmp.getGreen() / 255f, tmp.getBlue() / 255f, 1f);
    }

    public ColorSpec average(HSV target) {
        clamp();
        target.clamp();
        return lerp(50, target);
    }

    @Override
    public ColorSpec lerp(float startPercent, HSV target) {
        return new HSV(Maths.lerpDegrees(H, target.H, startPercent), Maths.lerp(S, target.S, startPercent), Maths.lerp(V, target.V, startPercent));
    }

}