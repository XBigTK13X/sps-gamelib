package sps.color;

import junit.framework.TestCase;
import org.junit.Test;

public class TestColors extends TestCase {
    @Test
    public void testColorToInts() {
        Color[] colors = {Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.BLUE};

        int[] ints = Colors.colorsToInts(colors);

        assertEquals(255, ints[0]);

    }

    @Test
    public void testIntsToColor() {
        int[] blackWhiteRedGreenBlue = {0, 0xffffffff, 0xaa0000ff, 0x00aa00ff, 0x0000aaff};

        Color[] colors = Colors.intsToColors(blackWhiteRedGreenBlue);

        assertEquals(0.0f, colors[0].r);
        assertEquals(0.0f, colors[0].g);
        assertEquals(0.0f, colors[0].b);
        assertEquals(1.0f, colors[1].r);
        assertEquals(1.0f, colors[1].g);
        assertEquals(1.0f, colors[1].b);
        assertEquals(0.6666667f, colors[2].r);
        assertEquals(0.0f, colors[2].g);
        assertEquals(0.0f, colors[2].b);
        assertEquals(0.0f, colors[3].r);
        assertEquals(0.6666667f, colors[3].g);
        assertEquals(0.0f, colors[3].b);
        assertEquals(0.0f, colors[4].r);
        assertEquals(0.0f, colors[4].g);
        assertEquals(0.6666667f, colors[4].b);
    }
}
