package sps.core;

import sps.display.Screen;

public class Point2 {
    public static final Point2 Zero = new Point2(0, 0);

    public float X;
    public float Y;
    public float Weight;

    public Point2() {
    }

    public Point2(float x, float y, int weight) {
        reset(x, y);
        Weight = weight;
    }

    public Point2(Point2 target) {
        this(target.X, target.Y, 0);
    }

    public Point2(int x, int y) {
        this(x, y, 0);
    }

    public Point2(float x, float y) {
        this(x, y, 0);
    }

    public Point2(double x, double y) {
        this((float) x, (float) y);
    }

    public void reset(float x, float y) {
        X = x;
        Y = y;
    }

    public void copy(Point2 point) {
        if (point != null) {
            reset(point.X, point.Y);
        }
    }

    public Point2 addRaw(Point2 target) {
        return new Point2(X + target.X, Y + target.Y);
    }

    public Point2 add(float dX, float dY) {
        return add((int) dX, (int) dY);
    }

    public Point2 add(int dX, int dY) {
        return new Point2(X + dX, Y + dY);
    }

    public void setWeight(float weight) {
        Weight = weight;
    }

    public boolean isZero() {
        return X == 0 && Y == 0;
    }

    public Point2 rotateAroundOrigin(int degrees) {
        return rotateAround(Point2.Zero, degrees);
    }

    public Point2 rotateAround(Point2 pivot, int degrees) {
        double theta = (degrees) * Math.PI / 180;

        Point2 displacement = add(-pivot.X, -pivot.Y);

        double x = displacement.X * Math.cos(theta) - displacement.Y * Math.sin(theta);
        double y = displacement.X * Math.sin(theta) + displacement.Y * Math.cos(theta);

        return new Point2(x + pivot.X, y + pivot.Y);
    }

    @Override
    public String toString() {
        return "(X,Y):  (" + X + "," + Y + ")";
    }

    public static Point2 random() {
        return new Point2(RNG.next(0, Screen.get().VirtualWidth), RNG.next(0, Screen.get().VirtualHeight));
    }

    public void setY(float y) {
        Y = y;
    }

    public void setX(float x) {
        X = x;
    }

    public Point2 add(Point2 point2) {
        return addRaw(point2);
    }

    public Point2 mult(float x, float y) {
        return new Point2(X * x, Y * y);
    }
}
