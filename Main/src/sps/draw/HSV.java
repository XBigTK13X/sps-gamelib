package sps.draw;

import com.badlogic.gdx.graphics.Color;

public class HSV {
    public float H;
    public float S;
    public float V;

    public HSV() {
        this(0f, 0f, 0f);
    }

    public HSV(float hue, float saturation, float value) {
        H = hue;
        S = saturation;
        V = value;
    }

    //From: http://stackoverflow.com/questions/7896280/converting-from-hsv-hsb-in-java-to-rgb-without-using-java-awt-color-disallowe
    public Color toRGBColor() {
        int h = (int) (H * 6);
        float f = H * 6 - h;
        float p = V * (1 - S);
        float q = V * (1 - f * S);
        float t = V * (1 - (1 - f) * S);

        switch (h) {
            case 0:
                return Colors.fromRGB(V, t, p);
            case 1:
                return Colors.fromRGB(q, V, p);
            case 2:
                return Colors.fromRGB(p, V, t);
            case 3:
                return Colors.fromRGB(p, q, V);
            case 4:
                return Colors.fromRGB(t, p, V);
            case 5:
                return Colors.fromRGB(V, p, q);
            default:
                throw new RuntimeException("HSV to RGB failure. Input was " + H + ", " + S + ", " + V + ". Calculated H: " + h);
        }
    }

    public static Color average(Color a, Color b) {
        HSV ha = fromRGB(a);
        HSV hb = fromRGB(b);
        return new HSV((ha.H + hb.H) / 2, (ha.S + hb.S) / 2, (ha.V + hb.V) / 2).toRGBColor();
    }

    //From: http://stackoverflow.com/questions/1664140/js-function-to-calculate-complementary-colour
    public static HSV fromRGB(Color rgb) {
        HSV hsv = new HSV();
        float max = Math.max(Math.max(rgb.r, rgb.g), rgb.b);
        float dif = max - Math.min(Math.min(rgb.r, rgb.g), rgb.b);
        hsv.S = (max == 0.0) ? 0 : (dif / max);
        if (hsv.S == 0) {
            hsv.H = 0;
        }
        else if (rgb.r == max) {
            hsv.H = 1f / 6f * (rgb.g - rgb.b) / dif;
        }
        else if (rgb.g == max) {
            hsv.H = 2f / 6f + 1f / 6f * (rgb.b - rgb.r) / dif;
        }
        else if (rgb.b == max) {
            hsv.H = 4f / 6f + 1f / 6f * (rgb.r - rgb.g) / dif;
        }
        if (hsv.H < 0.0) {
            hsv.H += 1f / 6f;
        }
        hsv.V = max;

        return hsv;
    }
}

