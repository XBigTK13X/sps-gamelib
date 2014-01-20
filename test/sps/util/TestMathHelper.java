package sps.util;

import junit.framework.TestCase;
import org.junit.Test;

public class TestMathHelper extends TestCase {
    private static final float SIGNIFICANCE = .0001f;

    @Test
    public void testPercentToValue() {
        assertEquals(100.0f, Maths.percentToValue(50, 150, 50));
        assertEquals(-50.0f, Maths.percentToValue(-50, 50, 0));
        assertEquals(-100.0f, Maths.percentToValue(-150, -50, 50));
        assertEquals(-50.0f, Maths.percentToValue(-150, -50, 100));
    }

    @Test
    public void testValueToPercent() {
        assertEquals(100.0f, Maths.valueToPercent(50, 150, 150));
        assertEquals(50.0f, Maths.valueToPercent(50, 150, 100));
        assertEquals(50.0f, Maths.valueToPercent(-50, 50, 0));
        assertEquals(50.0f, Maths.valueToPercent(-150, -50, -100));
        assertEquals(0.0f, Maths.valueToPercent(-50, 50, -50));
    }

    @Test
    public void testRotationLerp() {
        assertEquals(359.0f, Maths.lerpDegrees(0, 359, 0));
        assertTrue(SIGNIFICANCE > Maths.lerpDegrees(0, 359, 100));
        assertEquals(40.0f, Maths.lerpDegrees(30, 50, 50));
        assertTrue(SIGNIFICANCE > Maths.lerpDegrees(10, -10, 50));
        assertEquals(330.0f, Maths.lerpDegrees(0, 300, 50));
    }
}


