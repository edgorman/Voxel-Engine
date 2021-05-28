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

    private double getTerrainHeight(double x, double y){
        return (
            this.noise.noise(x * 0.01, y * 0.01, 0) + 
            this.noise.noise(x * 0.005, y * 0.005, 0)
        ) / 2;
    }

    @Override
    public Chunk createChunk(Vector p) {
        Chunk c = new Chunk(p);

        for (double x = p.x; x < p.x + Chunk.size; x++){
            for (double y = p.y; y < p.y + Chunk.size; y++){
                double za = Chunk.size * this.maxZ / 16;
                double zb = 0d;
                double zc = -1d;
                double zd = this.getTerrainHeight(x, y);
                double ze = 1d;
                double zf = 16;
                int zi = (int) (((za - zb) * (zd - zc) / (ze - zc)) + zb + zf);

                for (double z = p.z; z < p.z + Chunk.size && z <= zi; z++){
                    try { c.addVoxel(new Stone(new Vector(x, y, z))); } 
                    catch (Exception e) { e.printStackTrace(); }
                }
            }
        }

        return c;
    }
    
}
