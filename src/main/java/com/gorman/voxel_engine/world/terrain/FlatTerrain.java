package com.gorman.voxel_engine.world.terrain;

import com.gorman.voxel_engine.world.chunks.Chunk;
import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.voxels.Grass;
import com.gorman.voxel_engine.world.voxels.Stone;

public class FlatTerrain extends Terrain {

    public FlatTerrain(long s) {
        super(s, 16);
    }

    @Override
    public Chunk createChunk(Vector p) {
        Chunk c = new Chunk(p);

        if (p.z == 0){
            for (double x = p.x; x < p.x + Chunk.size; x++){
                for (double y = p.y; y < p.y + Chunk.size; y++){
                    try {
                        c.addVoxel(new Stone(new Vector(x, y, 0)));
                        c.addVoxel(new Stone(new Vector(x, y, 1)));
                        c.addVoxel(new Stone(new Vector(x, y, 2)));

                        c.addVoxel(new Grass(new Vector(x, y, 3)));
                    } 
                    catch (Exception e) { e.printStackTrace(); }
                }
            }
        }

        return c;
    }
    
}
