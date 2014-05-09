package com.simplepathstudios.gamelib.draw;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.simplepathstudios.gamelib.color.Color;
import com.simplepathstudios.gamelib.color.Colors;
import com.simplepathstudios.gamelib.core.Point2;
import com.simplepathstudios.gamelib.core.RNG;
import com.simplepathstudios.gamelib.display.Screen;
import com.simplepathstudios.gamelib.util.HitTest;

import java.util.ArrayList;
import java.util.List;

public class BackgroundMaker {
    private BackgroundMaker() {

    }

    public static Sprite noisyRadialDark(int percentX, int percentY) {
        Color[][] base = radialDarkBase((int) Screen.width(percentX), (int) Screen.height(percentY));
        TextureManipulation.subtleNoise(base, 5);
        return SpriteMaker.fromColors(base);
    }

    public static Sprite noisyRadialBright() {
        Color[][] base = radialBrightBase((int) Screen.width(100), (int) Screen.height(100));
        TextureManipulation.subtleNoise(base, 40);
        return SpriteMaker.fromColors(base);
    }

    public static Sprite radialBright() {
        return radialBright((int) Screen.width(100), (int) Screen.height(100));
    }

    public static Sprite radialBright(int pixelWidth, int pixelHeight) {

        return SpriteMaker.fromColors(radialBrightBase(pixelWidth, pixelHeight));
    }

    public static Sprite radialDark() {
        return radialDark((int) Screen.width(100), (int) Screen.height(100));
    }


    public static Sprite radialDark(int pixelWidth, int pixelHeight) {
        return SpriteMaker.fromColors(radialDarkBase(pixelWidth, pixelHeight));
    }

    private static Color[][] radialBrightBase(int w, int h) {
        Color c1 = Colors.randomPleasant();
        Color c2 = Color.WHITE;
        return ProcTextures.smoothRadial(w, h, c1, c2);
    }

    private static Color[][] radialDarkBase(int w, int h) {
        Color c1 = Colors.brightnessShift(Colors.randomPleasant(), -50);
        Color c2 = Color.BLACK;
        return ProcTextures.smoothRadial(w, h, c1, c2);
    }

    public static Sprite printedCircuitBoard() {
        return printedCircuitBoard((int) Screen.width(100), (int) Screen.height(100));
    }

    private static final int viaPixelWidth = 12;
    private static final int viaPixelMargin = 10;
    private static final int tracePixelWidth = 3;

    public static Sprite printedCircuitBoard(int pixelWidth, int pixelHeight) {
        Color board = Colors.randomPleasant();
        Color board2 = Colors.randomPleasant();

        List<Point2> vias = new ArrayList<>();

        int width = pixelWidth;
        int height = pixelHeight;

        Color[][] base = ProcTextures.smoothRadial(width, height, board, board2);

        Color trace = Colors.randomPleasant();
        trace = Colors.brightnessShift(trace, -5);

        Color via = Colors.randomPleasant();
        via = Colors.brightnessShift(via, -5);
        Color via2 = Colors.compliment(via);

        //Via sprite
        boolean[][] viaBase = new boolean[viaPixelWidth][viaPixelWidth];
        Point2 viaCenter = new Point2(viaPixelWidth / 2, viaPixelWidth / 2);
        for (int ii = 0; ii < viaBase.length; ii++) {
            for (int jj = 0; jj < viaBase[0].length; jj++) {
                float dist = HitTest.getDistance(ii, jj, viaCenter.X, viaCenter.Y);
                if (dist < viaPixelWidth / 2) {
                    viaBase[ii][jj] = true;
                }
            }
        }

        //Via placement
        int screenArea = width * height;
        int viaCount = screenArea / (viaPixelWidth * viaPixelWidth) / (viaPixelMargin * viaPixelMargin);
        while (viaCount > 0) {
            viaCount--;
            Point2 viaLoc = new Point2(RNG.next(width), RNG.next(height));

            vias.add(viaLoc);
        }

        List<Point2> vias2 = new ArrayList<>(vias);

        //Trace connections
        while (vias.size() > 0) {
            Point2 start = RNG.pick(vias);
            Point2 end = RNG.pick(vias);
            if (start != end) {
                Point2 maxx = (start.X > end.X) ? start : end;
                Point2 minx = (start.X > end.X) ? end : start;
                Point2 maxy = (start.Y > end.Y) ? start : end;
                Point2 miny = (start.Y > end.Y) ? end : start;

                int xtrace = (int) minx.X;
                while (xtrace < maxx.X) {
                    xtrace++;
                    for (int w = -tracePixelWidth / 2; w < tracePixelWidth / 2; w++) {
                        int y = (int) minx.Y + w;
                        if (y >= 0 && y < base[0].length) {
                            base[xtrace][y] = trace;
                        }
                    }
                }

                int ytrace = (int) miny.Y;
                while (ytrace < maxy.Y) {
                    ytrace++;
                    for (int w = -tracePixelWidth / 2; w < tracePixelWidth / 2; w++) {
                        int x = (int) maxx.X + w;
                        if (x >= 0 && x < base.length) {
                            base[x][ytrace] = trace;
                        }
                    }
                }
            }
            vias.remove(end);
            vias.remove(start);
        }

        for (Point2 viaLoc : vias2) {
            Color v = RNG.coinFlip() ? via : via2;
            for (int ox = 0; ox < viaBase.length; ox++) {
                for (int oy = 0; oy < viaBase[0].length; oy++) {
                    if (viaBase[ox][oy]) {
                        int x = (int) (viaLoc.X + ox - viaPixelWidth / 2);
                        int y = (int) (viaLoc.Y + oy - viaPixelWidth / 2);
                        if (x >= 0 && y >= 0 && x < base.length && y < base[0].length) {
                            base[x][y] = v;
                        }
                    }
                }
            }
        }

        base = TextureManipulation.blurStack(base, 3);
        TextureManipulation.darken(base, 60);
        return SpriteMaker.fromColors(base);
    }
}
