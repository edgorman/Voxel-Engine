package com.gorman.voxel_engine.world.terrain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.gorman.voxel_engine.world.primitives.Vector;

import org.junit.Test;

public class TestChunkManager
{

    @Test
    public void checkObjectInstantiation(){
        FlatTerrain f = new FlatTerrain(0);
        ChunkManager m = new ChunkManager(f, 1);

        assertEquals(0, m.chunkMap.size(), 0);
    }

    @Test
    public void checkGetChunks(){
        FlatTerrain f = new FlatTerrain(0);
        ChunkManager m = new ChunkManager(f, 1);

        assertNotNull(m.getChunks(new Vector(0, 0, 0)));
        assertEquals(9 * ChunkManager.maxZ, m.chunkMap.size(), 0);

        assertNotNull(m.chunkMap.get(new Vector(0, 0, 0)));
        assertEquals(9 * ChunkManager.maxZ, m.chunkMap.size(), 0);
    }

}
