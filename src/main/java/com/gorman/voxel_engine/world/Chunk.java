package com.gorman.voxel_engine.world;

import java.util.ArrayList;

/**
 * The Chunk object represents a section of the voxel terrain.
 * 
 * @author Edward Gorman
 */
public class Chunk {
    
    public Vector position;
    public Voxel[][][] array;
    public ArrayList<Voxel> list;
    public static int size = 16;

    public Chunk(Vector p){
        this.position = p;
        this.array = new Voxel[Chunk.size][Chunk.size][Chunk.size];
        this.list = new ArrayList<Voxel>();
    }

    public boolean checkVectorValid(Vector v){
        if (v.x < 0 || v.x >= 16 ||
            v.y < 0 || v.y >= 16 ||
            v.z < 0 || v.z >= 16)
                return false;
        return true;
    }

    public Voxel getVoxel(Vector p) throws Exception{
        Vector q = p.subtract(this.position);
        
        if (this.checkVectorValid(q))
            return this.array[(int) q.x][(int) q.y][(int) q.z];
        else
            throw new Exception("Error: Voxel is not accessible from this chunk: " + q);
    }

    public ArrayList<Voxel> getVoxelList(){
        return this.list;
    }

    public void addVoxel(Voxel v) throws Exception{
        Vector w = v.position.subtract(this.position);

        if (this.checkVectorValid(w)){
            this.array[(int) w.x][(int) w.y][(int) w.z] = v;
            this.list.add(v);
        }
        else
            throw new Exception("Error: Voxel is not accessible from this chunk: " + w);
    }

    public void removeVoxel(Voxel v) throws Exception{
        Vector w = v.position.subtract(this.position);

        if (this.checkVectorValid(w)){
            this.array[(int) w.x][(int) w.y][(int) w.z] = null;
            this.list.remove(v);
        }
        else
            throw new Exception("Error: Voxel is not accessible from this chunk: " + w);
    }
}
