package com.gorman.voxel_engine.world;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestVector
{

    @Test
    public void checkObjectInstantiation(){
        Vector u = new Vector(0, 0, 0);
        assertEquals(0, u.x, 0);
        assertEquals(0, u.y, 0);
        assertEquals(0, u.z, 0);

        Vector v = new Vector(u);
        assertEquals(0, v.x, 0);
        assertEquals(0, v.y, 0);
        assertEquals(0, v.z, 0);

        assertTrue(u.equals(v));
        assertFalse(u == v);
    }

    @Test
    public void checkNegativeZero(){
        Vector u = new Vector(0, 0, 0);
        Vector v = new Vector(-0, -0, -0);

        assertTrue(u.equals(v));
    }

    @Test
    public void checkAddition(){
        Vector u = new Vector(0, 0, 0);
        Vector v = new Vector(1, 2, 3);

        Vector w = u.add(v);
        assertEquals(1, w.x, 0);
        assertEquals(2, w.y, 0);
        assertEquals(3, w.z, 0);
    }

    @Test
    public void checkSubtraction(){
        Vector u = new Vector(0, 0, 0);
        Vector v = new Vector(1, 2, 3);

        Vector w = u.subtract(v);
        assertEquals(-1, w.x, 0);
        assertEquals(-2, w.y, 0);
        assertEquals(-3, w.z, 0);
    }

    @Test
    public void checkAbsolute(){
        Vector u = new Vector(0, 0, 0);
        Vector v = new Vector(1, 2, 3);

        Vector w = u.absolute(v);
        assertEquals(1, w.x, 0);
        assertEquals(2, w.y, 0);
        assertEquals(3, w.z, 0);
    }

    @Test
    public void checkMultiplication(){
        Vector u = new Vector(1, 1, 1);
        Vector v = new Vector(1, 2, 3);

        Vector w = u.multiply(v);
        assertEquals(1, w.x, 0);
        assertEquals(2, w.y, 0);
        assertEquals(3, w.z, 0);
    }

    @Test
    public void checkLength(){
        Vector u = new Vector(1, 2, 3);
        double length = Math.sqrt(
            1 * 1 + 
            2 * 2 + 
            3 * 3
        );

        assertEquals(length, u.length(), 0);
    }

    @Test
    public void checkInverse(){
        Vector u = new Vector(1, 1, 1);

        Vector v = u.inverse();
        assertEquals(-1, v.x, 0);
        assertEquals(-1, v.y, 0);
        assertEquals(-1, v.z, 0);
    }

    @Test
    public void checkScale(){
        Vector u = new Vector(2, 2, 2);
        Vector v = u.scale(2);

        assertEquals(4, v.x, 0);
        assertEquals(4, v.y, 0);
        assertEquals(4, v.z, 0);
    }

    @Test
    public void checkSummation(){
        Vector u = new Vector(1, 1, 1);

        double sum = u.sum();
        assertEquals(3, sum, 0);
    }

    @Test
    public void checkNormalisation(){
        Vector u = new Vector(1, 2, 3);
        double a = 1.0d/u.length();
        double b = 2.0d/u.length();
        double c = 3.0d/u.length();

        Vector v = u.normalise();
        assertEquals(a, v.x, 0);
        assertEquals(b, v.y, 0);
        assertEquals(c, v.z, 0);
    }

    @Test
    public void checkDistance(){
        Vector u = new Vector(0, 0, 0);
        Vector v = new Vector(3, 3, 3);
        double distance = Math.sqrt(
            Math.pow(3, 2) + 
            Math.pow(3, 2) + 
            Math.pow(3, 2)
        );

        assertEquals(distance, u.distance(v), 0);
    }

    @Test
    public void checkDotProduct(){
        Vector u = new Vector(1, 2, 3);
        Vector v = new Vector(2, 2, 2);
        double dotProduct = (
            1 * 2 + 
            2 * 2 + 
            3 * 2
        );

        assertEquals(dotProduct, u.dotProduct(v), 0);
        assertEquals(dotProduct, u.dotProduct(v), 0);
        assertEquals(dotProduct, u.dotProduct(v), 0);
    }

    @Test
    public void checkCrossProduct(){
        Vector u = new Vector(1, 2, 3);
        Vector v = new Vector(2, 2, 2);
        double a = 2 * 2 - 3 * 2;
        double b = 3 * 2 - 1 * 2;
        double c = 1 * 2 - 2 * 2;

        assertEquals(a, u.crossProduct(v).x, 0);
        assertEquals(b, u.crossProduct(v).y, 0);
        assertEquals(c, u.crossProduct(v).z, 0);
    }
}
