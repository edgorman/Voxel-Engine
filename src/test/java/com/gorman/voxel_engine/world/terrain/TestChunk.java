package com.gorman.voxel_engine.world.terrain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.voxels.Stone;
import com.gorman.voxel_engine.world.voxels.Voxel;

import org.junit.Test;

public class TestChunk
{

    @Test
    public void checkObjectInstantiation(){
        Vector u = new Vector(0, 0, 0);
        Chunk c = new Chunk(u);

        assertEquals(Chunk.size, c.array.length, 0);
        assertEquals(Chunk.size, c.array[0].length, 0);
        assertEquals(Chunk.size, c.array[0][0].length, 0);
        assertEquals(0, c.list.size(), 0);
    }

    @Test
    public void checkVectorInChunk(){
        Chunk c = new Chunk(new Vector(0, 0, 0));

        Vector u = new Vector(0, 0, 0);
        Vector v = new Vector(15, 15, 15);
        Vector w = new Vector(-1, -1, -1);
        Vector x = new Vector(16, 16, 16);

        assertTrue(c.contains(u));
        assertTrue(c.contains(v));
        assertFalse(c.contains(w));
        assertFalse(c.contains(x));
    }

    @Test
    public void checkAddVoxel(){
        Chunk c = new Chunk(new Vector(0, 0, 0));

        Vector u = new Vector(0, 0, 0);
        Voxel v = new Stone(u);

        try {
            c.addVoxel(v);
            assertNotNull(c.getVoxel(u));
            assertEquals(1, c.list.size(), 0);
        } 
        catch (Exception e) {
            fail("An exception was not expected on: checkAddVoxel()");
        }
    }

    @Test
    public void checkAddVoxelException(){
        Chunk c = new Chunk(new Vector(0, 0, 0));

        Vector u = new Vector(-1, 2, 3);
        Voxel v = new Stone(u);

        try {
            c.addVoxel(v);
            fail("An exception was expected on: checkAddVoxelException()");
        } 
        catch (Exception e) { }
    }

    @Test
    public void checkRemoveVoxel(){
        Chunk c = new Chunk(new Vector(0, 0, 0));

        Vector u = new Vector(0, 0, 0);
        Voxel v = new Stone(u);

        try {
            c.addVoxel(v);
            assertNotNull(c.getVoxel(u));

            c.removeVoxel(v);
            assertNull(c.getVoxel(u));
            assertEquals(0, c.list.size(), 0);
        } 
        catch (Exception e) {
            fail("An exception was not expected on: checkRemoveVoxel()");
        }
    }

    @Test
    public void checkRemoveVoxelException(){
        Chunk c = new Chunk(new Vector(0, 0, 0));

        Vector u = new Vector(0, 0, 0);
        Voxel v = new Stone(u);

        try {
            c.removeVoxel(v);
            fail("An exception was not expected on: checkRemoveVoxelException()");
        } 
        catch (Exception e) { }
    }
}
