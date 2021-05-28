package com.gorman.voxel_engine.world.terrain;

import com.gorman.voxel_engine.world.chunks.Chunk;
import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.voxels.Stone;
import com.gorman.voxel_engine.world.voxels.Grass;

public class DebugTerrain extends Terrain{

    public DebugTerrain(long s) {
        super(s, 16);
    }

    @Override
    public Chunk createChunk(Vector p) {
        Chunk c = new Chunk(p);

        try {
            c.addVoxel(new Stone(new Vector(p.x+7, p.y+7, p.z+7)));
            c.addVoxel(new Grass(new Vector(p.x+7, p.y+8, p.z+7)));
        } 
        catch (Exception e) { e.printStackTrace(); }

        return c;
    }
    
}
