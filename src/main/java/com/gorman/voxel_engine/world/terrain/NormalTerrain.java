package com.gorman.voxel_engine.world.terrain;

import com.gorman.voxel_engine.world.voxels.Stone;
import com.gorman.voxel_engine.world.chunks.Chunk;
import com.gorman.voxel_engine.world.noise.Noise;
import com.gorman.voxel_engine.world.noise.SimplexNoise;
import com.gorman.voxel_engine.world.primitives.Vector;

public class NormalTerrain extends Terrain{

    public Noise noise;

    public NormalTerrain(long s) {
        super(s, 32);
        this.noise = new SimplexNoise(s);
    }

    private int getZ(double x, double y){
        return Math.max(
            0, 
            24 + 
            (int) (
                (
                    this.noise.noise(x * 0.005, y * 0.005, 0) + 
                    this.noise.noise(x * 0.004, y * 0.0004, 0) +
                    this.noise.noise(x * 0.01, y * 0.01, 0)
                ) 
                / 3
                * 
                16
            )
        );
    }

    @Override
    public Chunk createChunk(Vector p) {
        Chunk c = new Chunk(p);

        for (double x = p.x; x < p.x + Chunk.size; x++){
            for (double y = p.y; y < p.y + Chunk.size; y++){
                for (double z = p.z; z < p.z + Chunk.size && z <= this.getZ(x, y); z++){
                    try { c.addVoxel(new Stone(new Vector(x, y, z))); } 
                    catch (Exception e) { e.printStackTrace(); }
                }
            }
        }

        return c;
    }
    
}
