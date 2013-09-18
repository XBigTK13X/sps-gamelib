package sps.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import sps.bridge.Sps;
import sps.core.Point2;
import sps.io.Input;
import sps.util.BoundingBox;

public class HitTest {
    public static boolean isTouching(Entity source, Entity target) {
        return isClose(source, target);
    }

    private static boolean isClose(Entity source, Entity target) {
        return isClose(source.getLocation().X, target.getLocation().X, source.getLocation().Y, target.getLocation().Y);
    }

    private static boolean isClose(float x1, float x2, float y1, float y2) {
        return getDistanceSquare(x1, x2, y1, y2) < Sps.SpriteRadius;
    }

    public static float getDistance(Entity source, Entity target) {
        return getDistance(source.getLocation().X, source.getLocation().Y, target.getLocation().X, target.getLocation().Y);
    }

    public static float getDistanceSquare(Entity source, Entity target) {
        return getDistanceSquare(source.getLocation().X, target.getLocation().X, source.getLocation().Y, target.getLocation().Y);
    }

    public static float getDistanceSquare(Point2 source, Point2 target) {
        return getDistanceSquare(source.X, target.X, source.Y, target.Y);
    }

    public static float getDistanceSquare(float x1, float x2, float y1, float y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static int getDistanceSquare(int x1, int x2, int y1, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    public static boolean inBox(int x, int y, int oX, int oY, int width, int height) {
        boolean inX = x >= oX && x <= oX + width;
        boolean inY = y >= oY && y <= oY + height;
        return inX && inY;
    }

    public static boolean inBox(int x, int y, BoundingBox boundingBox) {
        return inBox(x, y, boundingBox.X, boundingBox.Y, boundingBox.Width, boundingBox.Height);
    }

    public static boolean mouseInside(Sprite sprite) {
        return inBox(Input.get().x(), Input.get().y(), BoundingBox.fromDimensions(sprite.getX(), sprite.getY(), (int) sprite.getWidth(), (int) sprite.getHeight()));
    }

    public static float getDistance(float x, float y, float x1, float y1) {
        return (float) Math.sqrt(getDistanceSquare(x, x1, y, y1));
    }

    public static float getDistance(Point2 a, Point2 b) {
        return (float) Math.sqrt(getDistanceSquare(a.X, b.X, a.Y, b.Y));
    }

    public static boolean overlap(float x, float y, int w, int h, float x2, float y2, int w2, int h2) {
        Rectangle r1 = new Rectangle(x, y, w, h);
        Rectangle r2 = new Rectangle(x2, y2, w2, h2);
        return r1.overlaps(r2);
    }
}
