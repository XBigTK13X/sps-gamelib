package sps.util;

import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import sps.core.Point2;

public class Maths {
    public static final double TWO_PI = Math.PI * 2;
    public static final float TWO_PIf = (float) (Math.PI * 2);

    public static int clamp(float value, int min, int max) {
        return (int) Math.max(Math.min(value, max), min);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }

    public static int wrap(float value, int min, int max) {
        if (value > max) {
            return min;
        }
        if (value < min) {
            return max;
        }
        return (int) value;
    }

    public static Point2 directionToPointFromLine(Point2 target, Point2 base, float dX, float dY) {
        float perpDY = -1 * dX;
        float perpDX = dY;

        Line known = new Line(new Vector2D(base.X, base.Y), Math.atan2(dY, dX));
        Line perp = new Line(new Vector2D(target.X, target.Y), Math.atan2(perpDY, perpDX));

        Vector2D intersection = known.intersection(perp);

        return new Point2(target.X - (float) intersection.getX(), target.Y - (float) intersection.getY());
    }

    public static int percent(float zeroToOnePercent) {
        return Maths.clamp(zeroToOnePercent * 100, 0, 100);
    }

    public static int percent(int max, int percent) {
        return (int) (max * (percent / 100f));
    }

    public static float percentDecimal(int percent) {
        return ((float) percent) / 100;
    }

    public static float percentToValue(float lowerBound, float upperBound, int percent) {
        return ((upperBound - lowerBound) * percentDecimal(percent)) + lowerBound;
    }

    public static float valueToPercent(float lowerBound, float upperBound, float location) {
        return Math.abs(100f * ((location - lowerBound) / (upperBound - lowerBound)));
    }

    //values[0] will be linearlly interpolated with values[1] and so on
    //the even values are considered the start (starting at 0)
    public static float[] lerp(float startPercent, float... values) {
        if (values.length % 2 == 1) {
            throw new RuntimeException("Maths cannot lerp if <values> isn't an even length");
        }

        float[] result = new float[values.length / 2];
        int count = 0;
        int ii = 0;

        while (count < values.length - 1) {
            result[ii] = lerp(values[count], values[count + 1], startPercent);
            count += 2;
            ii++;
        }
        return result;
    }

    public static float lerp(float a, float b, float startPercent) {
        return a * (startPercent / 100f) + b * (1 - (startPercent / 100f));
    }

    public static float massage(float start, float min, float max, float strength) {
        start %= max;
        while (start >= max) {
            start -= strength;
        }
        while (start < min) {
            start += strength;
        }
        return start;
    }

    //http://xboxforums.create.msdn.com/forums/p/53551/579133.aspx
    public static float lerpDegrees(float start, float end, float step) {
        float from = (float) (start / 180 * Math.PI);
        float to = (float) (end / 180 * Math.PI);

        from = massage(from, 0, TWO_PIf, TWO_PIf);

        to = massage(to, 0, TWO_PIf, TWO_PIf);

        if (Math.abs(from - to) < Math.PI) {
            return (float) (lerp(from, to, step) * 180 / Math.PI);
        }

        if (from < to) {
            from += Maths.TWO_PI;
        }
        else {
            to += Maths.TWO_PI;
        }

        float retVal = lerp(from, to, step);

        if (retVal >= Maths.TWO_PI) {
            retVal -= Maths.TWO_PI;
        }
        return (float) (retVal * 180 / Math.PI);
    }
}
