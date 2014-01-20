package sps.color;

import sps.core.RNG;
import sps.core.SpsConfig;
import sps.util.Maths;

import java.util.HashMap;
import java.util.Map;

public class Colors {

    private static int __defaultBrightnessShiftPercent = 35;

    public static Color lighten(Color color) {
        return brightnessShift(color, __defaultBrightnessShiftPercent);
    }

    public static Color darken(Color color) {
        return brightnessShift(color, -__defaultBrightnessShiftPercent);
    }

    public static Color brightnessShift(Color color, int shiftPercent) {
        if (shiftPercent == 0) {
            return color;
        }
        HSV hsv = HSV.fromRGB(color.r, color.g, color.b);
        float shift = hsv.V * Maths.percentDecimal(shiftPercent);
        hsv.V += shift;

        return hsv.toColor();
    }

    public static Color random() {
        return new RGBA(RNG.next(0, 255, false), RNG.next(0, 255, false), RNG.next(0, 255, false)).toColor();
    }

    //From: http://martin.ankerl.com/2009/12/09/how-to-create-random-colors-programmatically/
    private static final float __goldenAngle = 137.508f;
    private static float __hueBase;
    private static boolean __hueBaseRandomized = false;

    public static Color randomPleasant() {
        if (!__hueBaseRandomized) {
            __hueBase = RNG.next(0, 360);
            __hueBaseRandomized = true;
        }

        __hueBase += __goldenAngle;
        __hueBase %= 360f;
        return new HSV(__hueBase, 0.7f, 0.95f).toColor();
    }


    public static Color compliment(Color color) {
        return hueShift(color, 180);
    }

    public static Color hueShift(Color color, float shift) {
        HSV hsv = HSV.fromColor(color);

        hsv.H += shift;
        hsv.H = Maths.massage(hsv.H, 0, 360, 60);

        return hsv.toColor();
    }

    public static Color[] gradient(Color start, Color end, int steps) {
        Color[] gradient = new Color[steps];
        for (int ii = 0; ii < steps; ii++) {
            float sP = 100 * (float) ii / steps;
            gradient[ii] = interpolate(sP, start, end);
        }
        return gradient;
    }

    private static RGBA __lerp1 = new RGBA(0, 0, 0);
    private static RGBA __lerp2 = new RGBA(0, 0, 0);
    private static ColorSpec __lookup = new RGBA(0, 0, 0);

    private static Map<ColorSpec, Color> __colorLookup = new HashMap<>();

    public static Color interpolate(float startPercent, Color start, Color end) {
        if (__colorLookup.size() > SpsConfig.get().maxColorLookupSize) {
            __colorLookup.clear();
        }
        __lerp1.R = start.r;
        __lerp1.G = start.g;
        __lerp1.B = start.b;
        __lerp2.R = end.r;
        __lerp2.G = end.g;
        __lerp2.B = end.b;
        __lookup = __lerp1.lerp(startPercent, __lerp2);
        if (!__colorLookup.containsKey(__lookup)) {
            __colorLookup.put(__lookup, __lookup.toColor());
        }
        return __colorLookup.get(__lookup);
    }

    public static Color[] twoDtoOneD(Color[][] base) {
        Color[] flattened = new Color[base.length * base[0].length];
        int index = 0;
        for (int ii = 0; ii < base.length; ii++) {
            for (int jj = 0; jj < base[0].length; jj++) {
                flattened[index++] = base[ii][jj];
            }
        }
        return flattened;
    }

    public static Color[][] oneDtoTwoD(Color[] base, int breadth, int depth) {
        Color[][] packed = new Color[breadth][depth];
        for (int ii = 0; ii < breadth; ii++) {
            for (int jj = 0; jj < depth; jj++) {
                packed[ii][jj] = base[ii * depth + jj];
            }
        }
        return packed;
    }

    public static Color[] intsToColors(int[] ints) {
        Color[] colors = new Color[ints.length];
        for (int ii = 0; ii < ints.length; ii++) {
            colors[ii] = new Color(ints[ii]);
        }
        return colors;
    }

    public static int[] colorsToInts(Color[] colors) {
        int[] ints = new int[colors.length];
        for (int ii = 0; ii < colors.length; ii++) {
            if (colors[ii] != null) {
                ints[ii] = colors[ii].rgba8888();
            }
        }
        return ints;
    }
}
