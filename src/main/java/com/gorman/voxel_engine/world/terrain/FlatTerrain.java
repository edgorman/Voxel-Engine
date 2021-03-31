package com.gorman.voxel_engine.world.terrain;

import java.awt.Color;

import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.primitives.Voxel;

public class FlatTerrain extends Terrain {

    public FlatTerrain(long s) {
        super(s);
    }

    @Override
    public Chunk getChunk(Vector p) {
        Chunk c = new Chunk(p);

        if (p.z == 0){
            for (double x = p.x; x < p.x + Chunk.size; x++){
                for (double y = p.y; y < p.y + Chunk.size; y++){
                    try {
                        c.addVoxel(new Voxel(new Vector(x, y, 0), Color.GRAY));
                        c.addVoxel(new Voxel(new Vector(x, y, 1), Color.GRAY));
                        c.addVoxel(new Voxel(new Vector(x, y, 2), Color.GRAY));

                        c.addVoxel(new Voxel(new Vector(x, y, 3), Color.GREEN));
                    } 
                    catch (Exception e) { e.printStackTrace(); }
                }
            }
        }

        return c;
    }
    
}
