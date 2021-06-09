package com.gorman.voxel_engine.world.chunks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.gorman.voxel_engine.world.voxels.Stone;
import com.gorman.voxel_engine.world.voxels.Voxel;
import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.terrain.FlatTerrain;

import org.junit.Test;

public class TestChunkManager
{

    @Test
    public void checkObjectInstantiation(){
        FlatTerrain f = new FlatTerrain(0);
        ChunkManager m = new ChunkManager(f, 1);

        assertEquals(0, m.map.size(), 0);
    }

    @Test
    public void checkGetVoxel(){
        FlatTerrain f = new FlatTerrain(0);
        ChunkManager m = new ChunkManager(f, 1);
        m.loadChunks(new Vector(0, 0, 0));
        Voxel v = m.getVoxel(new Vector(0, 0, 0));
        
        assertNotNull(v);
    }

    @Test
    public void checkAddVoxel(){
        FlatTerrain f = new FlatTerrain(0);
        ChunkManager m = new ChunkManager(f, 1);
        m.loadChunks(new Vector(0, 0, 0));
        Voxel v = new Stone(new Vector(0, 0, 16));

        try {
            m.addVoxel(v);
        } 
        catch (Exception e) {
            fail("An exception was not expected on: checkAddVoxel()");
        }
    }

    @Test
    public void checkRemoveVoxel(){
        FlatTerrain f = new FlatTerrain(0);
        ChunkManager m = new ChunkManager(f, 1);
        m.loadChunks(new Vector(0, 0, 0));
        Voxel v = m.getVoxel(new Vector(0, 0, 0));

        try {
            m.removeVoxel(v);
        } 
        catch (Exception e) {
            fail("An exception was not expected on: checkRemoveVoxel()");
        }
    }


    @Test
    public void checkGetChunkVector(){
        FlatTerrain f = new FlatTerrain(0);
        ChunkManager m = new ChunkManager(f, 1);

        Vector u = m.getChunkVector(new Vector(0, 0, 0));
        assertEquals(0, u.x, 0);
        assertEquals(0, u.y, 0);
        assertEquals(0, u.z, 0);

        Vector v = m.getChunkVector(new Vector(15, 16, 17));
        assertEquals(0 * Chunk.size, v.x, 0);
        assertEquals(1 * Chunk.size, v.y, 0);
        assertEquals(1 * Chunk.size, v.z, 0);

        Vector w = m.getChunkVector(new Vector(-2, 0, 1));
        assertEquals(-1 * Chunk.size, w.x, 0);
        assertEquals(0 * Chunk.size, w.y, 0);
        assertEquals(0 * Chunk.size, w.z, 0);
    }

    @Test
    public void checkGetChunks(){
        FlatTerrain f = new FlatTerrain(0);
        ChunkManager m = new ChunkManager(f, 1);
        
        m.loadChunks(new Vector(0, 0, 0));
        assertEquals(9 * f.maxZ, m.map.size(), 0);
        assertNotNull(m.map.get(new Vector(0, 0, 0)));
        assertEquals(9 * f.maxZ, m.map.size(), 0);
    }

}
