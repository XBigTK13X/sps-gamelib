package sps.util;

import sps.entities.HitTest;

public class BoundingBox {
    public int X;
    public int Y;
    public int X2;
    public int Y2;
    public int Width;
    public int Height;

    private static void reset(BoundingBox box, float x, float y, int width, int height) {
        box.X = (int) x;
        box.Y = (int) y;
        box.X2 = (int) (x + width);
        box.Y2 = (int) (y + height);
        box.Width = width;
        box.Height = height;
    }

    public static BoundingBox empty() {
        return new BoundingBox(0, 0, 0, 0);
    }

    public static void fromDimensions(BoundingBox reset, float x, float y, int width, int height) {
        reset(reset, x, y, width, height);
    }

    public static BoundingBox fromDimensions(float x, float y, int width, int height) {
        return new BoundingBox(x, y, width, height);
    }

    public static void fromPoints(BoundingBox reset, float x, float y, float x2, float y2) {
        reset(reset, x, y, (int) (x2 - x), (int) (y2 - y));
    }

    public static BoundingBox fromPoints(float x, float y, float x2, float y2) {
        return fromPoints((int) x, (int) y, (int) x2, (int) y2);
    }

    public static BoundingBox fromPoints(int x, int y, int x2, int y2) {
        return new BoundingBox((float) x, (float) y, x2 - x, y2 - y);
    }

    private BoundingBox(float x, float y, int width, int height) {
        X = (int) x;
        Y = (int) y;
        Width = width;
        Height = height;
        X2 = X + width;
        Y2 = Y + height;
    }

    public boolean envelopes(BoundingBox b) {
        boolean left = HitTest.inBox(b.X, b.Y, this);
        boolean top = HitTest.inBox(b.X, b.Y2, this);
        boolean right = HitTest.inBox(b.X2, b.Y, this);
        boolean bottom = HitTest.inBox(b.X2, b.Y2, this);

        return left && right && top && bottom;
    }

    public String debug() {
        return "(" + X + "," + Y + ")>(" + X2 + "," + Y2 + ")";
    }
}
