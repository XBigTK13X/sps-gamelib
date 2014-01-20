package sps.draw;

import sps.color.Color;
import sps.color.Colors;
import sps.core.RNG;
import sps.draw.filters.GaussianFilter;
import sps.draw.filters.GlowFilter;

import java.awt.image.BufferedImage;

public class TextureManipulation {
    public static void negative(Color[][] base) {
        for (int ii = 0; ii < base.length; ii++) {
            for (int jj = 0; jj < base[0].length; jj++) {
                base[ii][jj] = new Color(1f - base[ii][jj].r, 1f - base[ii][jj].g, 1f - base[ii][jj].b, 1f - base[ii][jj].a);
            }
        }
    }

    public static void blurNaive(Color[][] base, int blurriness) {
        for (int ii = 0; ii < base.length; ii++) {
            for (int jj = 0; jj < base[0].length; jj++) {
                float r = 0;
                float g = 0;
                float b = 0;
                float a = 0;
                int hits = 0;
                for (int kk = -blurriness; kk < blurriness; kk++) {
                    for (int ll = -blurriness; ll < blurriness; ll++) {
                        if (kk != 0 || ll != 0) {
                            int x = ii + kk;
                            int y = jj + ll;
                            if (x >= 0 && x < base.length && y >= 0 && y < base[0].length) {
                                r += base[x][y].r;
                                g += base[x][y].g;
                                b += base[x][y].b;
                                a += base[x][y].a;
                                hits++;
                            }
                        }
                    }
                }
                if (hits > 0) {
                    base[ii][jj] = new Color(r / hits, g / hits, b / hits, a / hits);
                }
            }
        }
    }

    public static void blurGaussian(Color[][] base, int radius) {
        BufferedImage dest = getBuffer(base);
        GaussianFilter filter = new GaussianFilter();
        filter.setRadius(radius);
        filter.filter(getBuffer(base), dest);
        copyDest(dest, base);
    }

    public static Color[][] blurStack(Color[][] base, int radius) {
        return Blur.fastblur(base, radius);
    }

    public static void glow(Color[][] base, float strength) {
        BufferedImage dest = getBuffer(base);
        GlowFilter filter = new GlowFilter();
        filter.setAmount(strength);
        filter.filter(getBuffer(base), dest);
        copyDest(dest, base);
    }

    private static BufferedImage getBuffer(Color[][] base) {
        BufferedImage source = new BufferedImage(base.length, base[0].length, BufferedImage.TYPE_INT_ARGB);
        for (int ii = 0; ii < base.length; ii++) {
            for (int jj = 0; jj < base[0].length; jj++) {
                source.setRGB(ii, jj, base[ii][jj].rgb888());
            }
        }
        return source;
    }

    private static void copyDest(BufferedImage dest, Color[][] base) {
        for (int ii = 0; ii < base.length; ii++) {
            for (int jj = 0; jj < base[0].length; jj++) {
                base[ii][jj] = new Color(dest.getRGB(ii, jj));
            }
        }
    }

    public static void darken(Color[][] base, int percent) {
        for (int ii = 0; ii < base.length; ii++) {
            for (int jj = 0; jj < base[0].length; jj++) {
                base[ii][jj] = Colors.brightnessShift(base[ii][jj], -percent);
            }
        }
    }

    public static void subtleNoise(Color[][] base, int maxChangePercent) {
        for (int ii = 0; ii < base.length; ii++) {
            for (int jj = 0; jj < base[0].length; jj++) {
                base[ii][jj] = Colors.brightnessShift(base[ii][jj], RNG.next(maxChangePercent * 2) - maxChangePercent);
            }
        }
    }
}
