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
        Chunk c = this.map.get(this.getChunkVector(p));

        if (c == null)
            return null;
        try { return c.getVoxel(p); } 
        catch (Exception e) { return null; }
    }

    public Vector getChunkVector(Vector p){
        return new Vector(
            Chunk.size * Math.floor(p.x/Chunk.size),
            Chunk.size * Math.floor(p.y/Chunk.size),
            Chunk.size * Math.floor(p.z/Chunk.size)
        );
    }

    public void getChunks(Vector p){
        this.loaded = new ArrayList<Chunk>();
        Vector pc = this.getChunkVector(p);

        for (int x = -this.radius; x <= this.radius; x++){
			for (int y = -this.radius; y <= this.radius; y++){

                // Use modified manhatten distance rendering strategy
                if (Math.abs(x) + Math.abs(y) - 1 <= this.radius){
                    for (int z = -this.radius; z <= this.radius; z++){
                        Vector q = new Vector(
                            pc.x + (double) x * Chunk.size, 
                            pc.y + (double) y * Chunk.size, 
                            Math.max(0d, pc.z + (double) z * Chunk.size)
                        );
                        Chunk c = this.map.get(q);

                        // Create chunk if doesn't exist
                        if (c == null){
                            c = this.terrain.createChunk(q);

                            for (Voxel u : c.getVoxelList()){
                                for (int i = 0; i < Voxel.directions.length; i += 2){
                                    Voxel v;
                                    Vector r = u.position.add(Voxel.directions[i]);
                                    try { v = c.getVoxel(r); }  
                                    catch (Exception e) { v = this.getVoxel(r); }

                                    if (v != null){
                                        u.neighbors[i] = v;
                                        v.neighbors[i+1] = u;
                                    }
                                }
                            }

                            this.map.put(q, c);
                        }
                        this.loaded.add(c);
                    }
                }
                
            }
        }
    }

}