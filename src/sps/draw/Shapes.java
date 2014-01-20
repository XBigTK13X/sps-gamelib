package sps.draw;


import com.badlogic.gdx.math.Circle;

import java.awt.*;

public class Shapes {
    public static Polygon regular(int sides, float radius, int rotationDeg) {
        double rotRads = rotationDeg * Math.PI / 180;
        int[] xs = new int[sides];
        int[] ys = new int[sides];
        double theta = 2 * Math.PI / sides;
        for (int ii = 0; ii < sides; ii++) {
            xs[ii] = (int) (Math.cos(theta * ii + rotRads) * radius);
            ys[ii] = (int) (Math.sin(theta * ii + rotRads) * radius);
        }
        return new Polygon(xs, ys, sides);
    }
}
