package com.gorman.voxel_engine.world.voxels;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.gorman.voxel_engine.world.primitives.Vector;

public class TestVoxel
{

    @Test
    public void checkObjectInstantiation(){
        Vector u = new Vector(0, 0, 0);
        Voxel v = new Stone(u);

        assertEquals(u.x, v.position.x, 0);
        assertEquals(u.y, v.position.y, 0);
        assertEquals(u.z, v.position.z, 0);
        assertEquals(6, v.faces.length, 0);

        assertEquals(new Vector(0, 0, -1), v.faces[0].normal);
        assertEquals(new Vector(0, 0, 1), v.faces[1].normal);
        assertEquals(new Vector(0, -1, 0), v.faces[2].normal);
        assertEquals(new Vector(0, 1, 0), v.faces[3].normal);
        assertEquals(new Vector(-1, 0, 0), v.faces[4].normal);
        assertEquals(new Vector(1, 0, 0), v.faces[5].normal);
    }

}
