package sps.draw;

import sps.color.Color;
import sps.color.Colors;
import sps.core.Point2;
import sps.core.RNG;
import sps.entities.HitTest;
import sps.util.GameConfig;
import sps.util.Maths;

public class ProcTextures {
    private static final int defaultPerlinSmoothness = 6;

    public static void remove(Color[][] arr, Color color) {
        for (int ii = 0; ii < arr.length; ii++) {
            for (int jj = 0; jj < arr[ii].length; jj++) {
                if (arr[ii][jj].equals(color)) {
                    arr[ii][jj] = null;
                }
            }
        }
    }

    public static Color[][] monotone(int width, int height, Color color) {
        Color[][] base = new Color[width][height];
        for (int ii = 0; ii < base.length; ii++) {
            for (int jj = 0; jj < base[0].length; jj++) {
                base[ii][jj] = color;
            }
        }
        return base;
    }

    public static Color[][] perlin(int width, int height, Color start, Color end) {
        return perlin(width, height, start, end, RNG.next(ProcTextures.defaultPerlinSmoothness - 1, ProcTextures.defaultPerlinSmoothness + 1));
    }

    public static Color[][] perlin(int width, int height, Color start, Color end, int smoothness) {
        return perlin(width, height, start, end, smoothness, false);
    }

    public static Color[][] perlin(int width, int height, Color start, Color end, int smoothness, boolean ignoreOptimizationsConfig) {
        if (GameConfig.OptDisableCloudyTextures && !ignoreOptimizationsConfig) {
            Color[][] result = new Color[width][height];
            for (int ii = 0; ii < width; ii++) {
                for (int jj = 0; jj < height; jj++) {
                    result[ii][jj] = start;
                }
            }
            return result;
        }

        float[][] noise = Noise.perlin(width, height, smoothness);
        return Noise.mapGradient(start, end, noise);
    }

    public static Color[][] gradient(int width, int height, Color start, Color end, boolean vertical) {
        Color[][] result = new Color[width][height];
        Color[] g = Colors.gradient(start, end, vertical ? height : width);
        for (int ii = 0; ii < result.length; ii++) {
            for (int jj = 0; jj < result[0].length; jj++) {
                result[ii][jj] = g[vertical ? jj : ii];
            }
        }
        return result;
    }

    public static Color[][] radial(int width, int height, Color start, Color end, int steps, Point2 center, boolean fixed) {
        Color[][] result = new Color[width][height];

        int maxDist = (int) (Math.sqrt(height * height + width * width));

        Color[] g = null;

        for (int ii = 0; ii < result.length; ii++) {
            for (int jj = 0; jj < result[0].length; jj++) {
                int dist = (int) HitTest.getDistance(ii, jj, center.X, center.Y);
                float percentDist = Maths.valueToPercent(0, maxDist, dist);
                if (fixed) {
                    if (g == null) {
                        g = Colors.gradient(start, end, steps);
                    }
                    int scaledDist = (int) ((percentDist / 100f) * steps);
                    result[ii][jj] = g[scaledDist];
                }
                else {
                    result[ii][jj] = Colors.interpolate(percentDist, start, end);
                }
            }
        }

        return result;
    }

    public static Color[][] fixedRadial(int width, int height, Color start, Color end, int steps) {
        return radial(width, height, start, end, steps, RNG.point(0, width, 0, height), true);
    }

    public static Color[][] smoothRadial(int width, int height, Color start, Color end) {
        return smoothRadial(width, height, start, end, RNG.point(0, width, 0, height));
    }

    public static Color[][] smoothRadial(int width, int height, Color start, Color end, Point2 center) {
        return radial(width, height, start, end, 0, center, false);
    }

    public static Color[][] centeredCircleSegment(int radiusPixelsMin, int radiusPixelsMax, int degreesMin, int degreesMax, Color start, Color end) {
        int boxHeight = radiusPixelsMax;
        int boxWidth = radiusPixelsMax;
        int widthDegrees = degreesMax - degreesMin;
        int normalizedCenterDegrees = (degreesMax - degreesMin) / 2;

        int arcMin = normalizedCenterDegrees - widthDegrees / 2;
        int arcMax = normalizedCenterDegrees + widthDegrees / 2;

        Color[][] base = new Color[boxWidth * 2][boxHeight * 2];

        Point2 center = new Point2(boxWidth, boxHeight);

        Color[] gradient = Colors.gradient(start, end, 360);

        int radiusBuffer = 1;
        for (int ii = 0; ii < base.length; ii++) {
            for (int jj = 0; jj < base[0].length; jj++) {
                int arcLocationDegrees = (int) (180 * Math.atan2(center.Y - jj, center.X - ii) / Math.PI);
                arcLocationDegrees = (int) Maths.massage(arcLocationDegrees, 0, 360, 360);
                if (arcLocationDegrees >= arcMin && arcLocationDegrees <= arcMax) {
                    float dist = HitTest.getDistance(ii, jj, center.X, center.Y);
                    if (dist < radiusPixelsMax - radiusBuffer && dist > radiusPixelsMin + radiusBuffer) {
                        base[ii][jj] = gradient[arcLocationDegrees];
                    }
                }
            }
        }
        return base;
    }
}
