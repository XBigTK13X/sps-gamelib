package sps.util;

import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import sps.core.Point2;

public class MathHelper {

    public static int clamp(float value, int min, int max) {
        return (int) Math.max(Math.min(value, max), min);
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
        return MathHelper.clamp(zeroToOnePercent * 100, 0, 100);
    }

    public static int percent(int max, int percent) {
        return (int) (max * (percent / 100f));
    }
}
