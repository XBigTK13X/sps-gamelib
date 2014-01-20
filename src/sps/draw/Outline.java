package sps.draw;

import sps.color.Color;
import sps.color.Colors;

public class Outline {

    private interface ColorPicker {
        public Color convert(Color color);
    }

    private static class Compliment implements ColorPicker {
        @Override
        public Color convert(Color color) {
            return Colors.compliment(color);
        }
    }

    private static class Single implements ColorPicker {
        private Color _choice;

        public Single(Color choice) {
            _choice = new Color(choice);
        }

        @Override
        public Color convert(Color color) {
            return _choice;
        }
    }

    private static class Blend implements ColorPicker {
        private Color _choice;

        public Blend(Color choice) {
            _choice = new Color(choice);
        }

        @Override
        public Color convert(Color color) {
            return Colors.interpolate(50, color, _choice);
        }
    }

    private static final Compliment compliment = new Compliment();


    private static void naive(Color[][] colors, ColorPicker picker, int pixelThickness) {
        int thicknessX = pixelThickness;
        int thicknessY = pixelThickness;
        boolean[][] shifted = new boolean[colors.length][colors[0].length];

        for (int ii = 0; ii < colors.length; ii++) {
            for (int jj = 0; jj < colors[0].length; jj++) {
                if (colors[ii][jj] != null) {
                    boolean shift = false;
                    for (int mm = 1; mm <= thicknessX; mm++) {
                        if (ii - thicknessX <= 0 || jj - thicknessY <= 0 || ii + thicknessX >= colors.length || jj + thicknessY >= colors[0].length) {
                            shift = true;
                        }
                        else {
                            for (int kk = -thicknessX; kk < thicknessX; kk++) {
                                for (int ll = -thicknessY; ll < thicknessY; ll++) {
                                    if (kk != 0 || ll != 0) {
                                        int adjX = ii + kk;
                                        int adjY = jj + ll;
                                        if (adjX >= 0 && adjX < colors.length - 1 && adjY >= 0 && adjY < colors[0].length) {
                                            if (colors[adjX][adjY] == null) {
                                                shift = true;
                                            }
                                        }
                                        else {
                                            shift = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (shift && !shifted[ii][jj]) {
                            colors[ii][jj] = picker.convert(colors[ii][jj]);
                            shifted[ii][jj] = true;
                        }
                    }
                }
            }
        }
    }

    public static void complimentary(Color[][] colors, int pixelThickness) {
        apply(colors, compliment, pixelThickness);
    }

    public static void single(Color[][] colors, Color outline, int pixelThickness) {
        apply(colors, new Single(outline), pixelThickness);
    }

    public static void blend(Color[][] colors, Color outline, int pixelThickness) {
        apply(colors, new Blend(outline), pixelThickness);
    }

    public static void apply(Color[][] colors, ColorPicker picker, int pixelThickness) {
        naive(colors, picker, pixelThickness);
    }
}