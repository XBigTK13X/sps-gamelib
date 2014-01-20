package sps.color;

import junit.framework.TestCase;
import org.junit.Test;

public class TestColorSpec extends TestCase {
    @Test
    public void testNullBlack() {
        rgbConversionTest(Color.BLACK, 0, 0, 0, 0, 0, 0);
    }

    @Test
    public void testNullWhite() {
        rgbConversionTest(Color.WHITE, 0, 0, 1, 100, 0, 0);
    }

    //Expectations taken from: http://www.workwithcolor.com/color-converter-01.htm
    @Test
    public void testPurlePinkish() {
        rgbConversionTest(new RGBA(145, 125, 130).toColor(), 345f, 0.13793103f, 0.5686275f, 57.318993f, 8.149952f, 0.09115934f);
    }

    public void rgbConversionTest(Color color, float... ex) {
        Color base = new Color(color);
        HSV hsv = HSV.fromColor(base);
        CIELab lab = CIELab.fromRGB(base.r, base.g, base.b);

        assertEquals(ex[0], hsv.H);
        assertEquals(ex[1], hsv.S);
        assertEquals(ex[2], hsv.V);
        assertEquals(ex[3], lab.L);
        assertEquals(ex[4], lab.A);
        assertEquals(ex[5], lab.B);

    }
}
