package com.gorman.voxel_engine.world.primitives;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPolygon
{

    @Test
    public void checkObjectInstantiation(){
        Polygon p = new Polygon(
            new double[]{0, 0, 1, 1}, 
            new double[]{0, 0, 0, 0}, 
            new double[]{0, 1, 0, 1},
            null,
            false
        );

        assertEquals(4, p.vertexes.length, 0);
    }

    @Test
    public void checkCentre(){
        Polygon p = new Polygon(
            new double[]{0, 0, 1, 1}, 
            new double[]{0, 0, 0, 0}, 
            new double[]{0, 1, 0, 1},
            null,
            false
        );

        Vector centre = new Vector(
            2.0d / 4.0d,
            0d / 4.0d,
            2.0d / 4.0d
        );
        assertEquals(centre.x, p.getCentre().x, 0);
        assertEquals(centre.y, p.getCentre().y, 0);
        assertEquals(centre.z, p.getCentre().z, 0);
    }

}
