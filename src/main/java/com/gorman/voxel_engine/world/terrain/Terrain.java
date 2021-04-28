package com.gorman.voxel_engine.world.terrain;

import com.gorman.voxel_engine.world.chunks.Chunk;
import com.gorman.voxel_engine.world.primitives.Vector;

public abstract class Terrain {

    public long seed;
    public int maxZ;

    public Terrain(long s, int m){
        this.seed = s;
        this.maxZ = m;
    };
    
    public abstract Chunk createChunk(Vector p);

}
