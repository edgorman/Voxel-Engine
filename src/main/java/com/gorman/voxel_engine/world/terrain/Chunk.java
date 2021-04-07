package com.gorman.voxel_engine.world.terrain;

import java.util.ArrayList;

import com.gorman.voxel_engine.player.Player;
import com.gorman.voxel_engine.world.primitives.Polygon;
import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.voxels.Stone;
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
    public ArrayList<Vector> renderDirections;

    public Chunk(Vector p){
        this.position = p;
        this.array = new Voxel[Chunk.size][Chunk.size][Chunk.size];
        this.list = new ArrayList<Voxel>();
        this.renderDirections = new ArrayList<Vector>();
    }

    public boolean update(Player player){
		return this.position.project(player).z < 0;
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

    public Vector getClosesetVector(Vector p){
        return new Vector(
            Math.min(Math.max(this.position.x, p.x), this.position.x + Chunk.size),
            Math.min(Math.max(this.position.y, p.y), this.position.y + Chunk.size),
            Math.min(Math.max(this.position.z, p.z), this.position.z + Chunk.size)
        );
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

    public void setPrederterminedInfo(Player player){
        this.renderDirections = new ArrayList<Vector>();

        // If within chunk
        if (this.contains(player.viewFrom.scale(1/Voxel.length).subtract(this.position)))
            return;

        // Else surrounding chunk
        Stone t = new Stone(this.getClosesetVector(player.viewFrom));
        for (Polygon p : t.faces)
            if (p.normal.dotProduct(p.vertexes[0].subtract(player.viewFrom)) >= 0)
                this.renderDirections.add(p.normal);
        
    }

}
