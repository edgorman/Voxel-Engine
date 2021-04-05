package com.gorman.voxel_engine.world.terrain;

import com.gorman.voxel_engine.world.voxels.Stone;
import com.gorman.voxel_engine.world.noise.Noise;
import com.gorman.voxel_engine.world.noise.SimplexNoise;
import com.gorman.voxel_engine.world.primitives.Vector;

public class NormalTerrain extends Terrain{

    public Noise noise;

    public NormalTerrain(long s, int m) {
        super(s, m);
        this.noise = new SimplexNoise(s);
    }

    @Override
    public Chunk createChunk(Vector p) {
        Chunk c = new Chunk(p);

        for (double x = p.x; x < p.x + Chunk.size; x++){
            for (double y = p.y; y < p.y + Chunk.size; y++){
                double zd = this.noise.noise(x*0.01, y*0.01, 0);
                int zi = (int) ((((Chunk.size * this.maxZ / 2) - 0d) * (zd - -1d) / (1d - -1d)) + 0d);

                if (zi < p.z || zi >= p.z + Chunk.size)
                    continue;

                try {
                    c.addVoxel(new Stone(new Vector(x, y, zi)));
                } 
                catch (Exception e) { e.printStackTrace(); }
            }
        }

        return c;
    }
    
}
