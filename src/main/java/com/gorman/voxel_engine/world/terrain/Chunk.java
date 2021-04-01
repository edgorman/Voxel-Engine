package com.gorman.voxel_engine.world.terrain;

import java.util.ArrayList;

import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.voxels.Voxel;

/**
 * The Chunk object represents a section of the voxel terrain.
 * 
 * @author Edward Gorman
 */
public class Chunk {
    
    public static int size = 16;

    public Vector position;
    public Voxel[][][] array;
    public ArrayList<Voxel> list;

    public Chunk(Vector p){
        this.position = p;
        this.array = new Voxel[Chunk.size][Chunk.size][Chunk.size];
        this.list = new ArrayList<Voxel>();
    }

    public boolean contains(Vector v){
        if (v.x < 0 || v.x >= Chunk.size ||
            v.y < 0 || v.y >= Chunk.size ||
            v.z < 0 || v.z >= Chunk.size)
                return false;
        return true;
    }

    public Voxel getVoxel(Vector p) throws Exception{
        Vector q = p.subtract(this.position);
        
        if (this.contains(q))
            return this.array[(int) q.x][(int) q.y][(int) q.z];
        else
            throw new Exception("Error: Voxel " + p + " is not accessible from this chunk: " + this.position);
    }

    public ArrayList<Voxel> getVoxelList(){
        return this.list;
    }

    public void addVoxel(Voxel v) throws Exception{
        Vector w = v.position.subtract(this.position);

        if (this.getVoxel(v.position) == null){
            this.array[(int) w.x][(int) w.y][(int) w.z] = v;
            this.list.add(v);
        }
        else
            throw new Exception("Error: Cannot add voxel to position, contains another voxel: " + w);
    }

    public void removeVoxel(Voxel v) throws Exception{
        Vector w = v.position.subtract(this.position);

        if (this.getVoxel(v.position) != null){
            this.array[(int) w.x][(int) w.y][(int) w.z] = null;
            this.list.remove(v);
        }
        else
            throw new Exception("Error: Cannot remove voxel from position, does not contain a voxel: " + w);
    }
}
