package com.gorman.voxel_engine.world.terrain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.voxels.Voxel;

import org.junit.Test;

public class TestChunkManager
{

    @Test
    public void checkObjectInstantiation(){
        FlatTerrain f = new FlatTerrain(0, 1);
        ChunkManager m = new ChunkManager(f, 1);

        assertEquals(0, m.map.size(), 0);
    }

    @Test
    public void checkGetChunkVector(){
        FlatTerrain f = new FlatTerrain(0, 1);
        ChunkManager m = new ChunkManager(f, 1);

        Vector u = m.getChunkVector(new Vector(0, 0, 0));
        assertEquals(0, u.x, 0);
        assertEquals(0, u.y, 0);
        assertEquals(0, u.z, 0);

        Vector v = m.getChunkVector(new Vector(15, 16, 17));
        assertEquals(0 * Chunk.size * Voxel.length, v.x, 0);
        assertEquals(1 * Chunk.size * Voxel.length, v.y, 0);
        assertEquals(1 * Chunk.size * Voxel.length, v.z, 0);

        Vector w = m.getChunkVector(new Vector(-2, 0, 1));
        assertEquals(-1 * Chunk.size * Voxel.length, w.x, 0);
        assertEquals(0 * Chunk.size * Voxel.length, w.y, 0);
        assertEquals(0 * Chunk.size * Voxel.length, w.z, 0);
    }

    @Test
    public void checkGetChunks(){
        FlatTerrain f = new FlatTerrain(0, 1);
        ChunkManager m = new ChunkManager(f, 1);
        
        m.getChunks(new Vector(0, 0, 0));
        assertEquals(5 * f.maxZ, m.map.size(), 0);
        assertNotNull(m.map.get(new Vector(0, 0, 0)));
        assertEquals(5 * f.maxZ, m.map.size(), 0);
    }

}
