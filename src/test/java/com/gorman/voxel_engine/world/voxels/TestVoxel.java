package com.gorman.voxel_engine.world.voxels;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.awt.Color;

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
        assertEquals(Color.GRAY, v.color);
        assertEquals(6, v.faces.length, 0);
    }

}