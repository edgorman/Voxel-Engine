package com.gorman.voxel_engine.world.terrain;

import java.util.ArrayList;
import java.util.HashMap;

import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.voxels.Voxel;

public class ChunkManager {
    
    public Terrain terrain;
    public int radius;

    public ArrayList<Chunk> loaded;
    public HashMap<Vector, Chunk> map;

    public ChunkManager(Terrain t, int r){
        this.terrain = t;
        this.radius = r;

        this.map = new HashMap<Vector, Chunk>();
    }

    public Voxel getVoxel(Vector p){
        Vector q = this.getChunkVector(p);
        Chunk c = this.map.get(q);
        if (c == null){
            c = this.terrain.createChunk(q);
            this.map.put(q, c);
        }
        
        try { return c.getVoxel(p); } 
        catch (Exception e) { return null; }
    }

    public Vector getChunkVector(Vector p){
        double s = Chunk.size;
        return new Vector(
            s * Math.floor(p.x/s),
            s * Math.floor(p.y/s),
            s * Math.floor(p.z/s)
        );
    }

    public void getChunks(Vector p){
        this.loaded = new ArrayList<Chunk>();
        Vector pc = this.getChunkVector(p);

        for (int x = -this.radius; x <= this.radius; x++){
			for (int y = -this.radius; y <= this.radius; y++){
                // Use modified manhatten distance rendering strategy
                if (Math.abs(x) + Math.abs(y) - 1 <= this.radius){
                    for (int z = 0; z < this.terrain.maxZ; z++){

                        Vector q = new Vector(
                            pc.x + (double) x * Chunk.size, 
                            pc.y + (double) y * Chunk.size, 
                            (double) z * Chunk.size
                        );
                        Chunk c = this.map.get(q);

                        if (c == null){
                            c = this.terrain.createChunk(q);
                            this.map.put(q, c);
                        }
                        this.loaded.add(c);

                    }
                }
            }
        }
    }

}