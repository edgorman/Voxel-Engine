package com.gorman.voxel_engine.world.terrain;

import java.util.ArrayList;
import java.util.HashMap;

import com.gorman.voxel_engine.world.primitives.Vector;

public class ChunkManager {
    
    public Terrain terrain;
    public int radius;
    public static int maxZ = 8;

    public HashMap<Vector, Chunk> chunkMap;

    public ChunkManager(Terrain t, int r){
        this.terrain = t;
        this.radius = r;

        this.chunkMap = new HashMap<Vector, Chunk>();
    }

    public ArrayList<Chunk> getChunks(Vector p){
        ArrayList<Chunk> chunks = new ArrayList<Chunk>();

        for (int x = ((int) Math.floor(p.x)) - this.radius; x < ((int) Math.floor(p.x)) + this.radius + 1; x++){
			for (int y = ((int) Math.floor(p.y)) - this.radius; y < ((int) Math.floor(p.y)) + this.radius + 1; y++){
				for (int z = 0; z < ChunkManager.maxZ; z++){

                    Vector q = new Vector(
                        (double) x * Chunk.size, 
                        (double) y * Chunk.size, 
                        (double) z * Chunk.size
                    );
                    
                    if (this.chunkMap.get(q) == null){
                        Chunk c = this.terrain.createChunk(q);
                        chunks.add(c);
                        this.chunkMap.put(q, c);
                    }
                    else
                        chunks.add(this.chunkMap.get(q));

                }
            }
        }

        return chunks;
    }

}