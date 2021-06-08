package com.gorman.voxel_engine.world.primitives;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPlane
{

    @Test
    public void checkObjectInstantiation(){
        Polygon p = new Polygon(
            null,
            new double[]{0, 0, 1, 1}, 
            new double[]{0, 0, 0, 0}, 
            new double[]{0, 1, 0, 1},
            null
        );
        Plane q = new Plane(p);

        assertEquals(0.5, q.p.x, 0);
        assertEquals(0, q.p.y, 0);
        assertEquals(0.5, q.p.z, 0);

        assertEquals(0, q.normal.x, 0);
        assertEquals(1, q.normal.y, 0);
        assertEquals(0, q.normal.z, 0);
    }

}
