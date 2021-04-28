package com.gorman.voxel_engine.world.chunks;

import java.util.ArrayList;
import java.util.HashMap;

import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.terrain.Terrain;
import com.gorman.voxel_engine.world.voxels.Voxel;

public class ChunkManager {
    
    public Terrain terrain;
    public int radius;

    public ArrayList<Chunk> loaded;
    public HashMap<Vector, Chunk> map;

    public ChunkManager(Terrain t, int r){
        this.terrain = t;
        this.radius = r;

        this.loaded = new ArrayList<Chunk>();
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

    public void loadChunks(Vector p){
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

                                    Voxel w;
                                    Vector t = u.position.add(Voxel.directions[i+1]);
                                    try { w = c.getVoxel(t); }  
                                    catch (Exception e) { w = this.getVoxel(t); }

                                    if (w != null){
                                        u.neighbors[i+1] = w;
                                        w.neighbors[i] = u;
                                    }
                                }
                            }

                            this.map.put(q, c);
                        }
                        
                        // Check if loaded contains chunk before adding
                        if (!this.loaded.contains(c))
                            this.loaded.add(c);
                    }
                }
                
            }
        }

        // Remove chunks outside of load distance
        this.loaded.removeIf(
            c -> (
                Math.abs((pc.x / Chunk.size) - (c.position.x / Chunk.size)) + 
                Math.abs((pc.y / Chunk.size) - (c.position.y / Chunk.size)) - 
                1 > this.radius
            )
        );
    }

}