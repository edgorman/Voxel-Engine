package com.gorman.voxel_engine.world.terrain;

import com.gorman.voxel_engine.world.Chunk;
import com.gorman.voxel_engine.world.Vector;

public abstract class Terrain {

    public long seed;

    public Terrain(long s){
        this.seed = s;
    };
    
    public abstract Chunk getChunk(Vector p);

}