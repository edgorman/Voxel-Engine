package com.gorman.voxel_engine.world.chunks;

import java.util.ArrayList;

import com.gorman.voxel_engine.player.Player;
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
    public ArrayList<Vector> visibleDirections;

    public Chunk(Vector p){
        this.position = p;
        this.array = new Voxel[Chunk.size][Chunk.size][Chunk.size];
        this.list = new ArrayList<Voxel>();
        this.visibleDirections = new ArrayList<Vector>();
    }

    public boolean contains(Vector v){
        Vector u = v.absolute(this.position);

        if (u.x < 0 || u.x >= Chunk.size ||
            u.y < 0 || u.y >= Chunk.size ||
            u.z < 0 || u.z >= Chunk.size ||
            v.x < this.position.x ||
            v.y < this.position.y ||
            v.z < this.position.z)
                return false;
        return true;
    }

    public Voxel getVoxel(Vector p) throws Exception{
        if (this.contains(p)){
            Vector q = p.absolute(this.position);
            return this.array[(int) q.x][(int) q.y][(int) q.z];
        }
        else
            throw new Exception("Error: Voxel " + p + " is not accessible from this chunk: " + this.position);
    }

    public ArrayList<Voxel> getVoxelList(){
        return this.list;
    }

    public void addVoxel(Voxel v) throws Exception{
        if (this.getVoxel(v.position) == null){
            Vector q = v.position.absolute(this.position);
            this.array[(int) q.x][(int) q.y][(int) q.z] = v;
            this.list.add(v);
        }
        else
            throw new Exception("Error: Cannot add voxel to position, contains another voxel: " + v.position);
    }

    public void removeVoxel(Voxel v) throws Exception{
        if (this.getVoxel(v.position) != null){
            Vector q = v.position.absolute(this.position);
            this.array[(int) q.x][(int) q.y][(int) q.z] = null;
            this.list.remove(v);
            
            // Update neighbor references by removing this voxel
            for (int i = 0; i < v.neighbors.length; i++){
                if (v.neighbors[i] != null){
                    v.neighbors[i].neighbors[
                        i - (int) Voxel.directions[i].sum()
                    ] = null;
                }
            }
        }
        else
            throw new Exception("Error: Cannot remove voxel from position, does not contain a voxel: " + v.position);
    }

    public void setPrederterminedInfo(Player player){
        this.visibleDirections = new ArrayList<Vector>();
        this.visibleDirections.add(new Vector(-1, 0, 0));
        this.visibleDirections.add(new Vector(1, 0, 0));
        this.visibleDirections.add(new Vector(0, -1, 0));
        this.visibleDirections.add(new Vector(0, 1, 0));
        this.visibleDirections.add(new Vector(0, 0, -1));
        this.visibleDirections.add(new Vector(0, 0, 1));

        // If player in chunk
        if (this.contains(player.viewFrom))
            return;

        // Else player in surrounding chunk
        if (player.viewFrom.x <= this.position.x) this.visibleDirections.remove(new Vector(1, 0, 0));
        else if (player.viewFrom.x >= this.position.x + Chunk.size) this.visibleDirections.remove(new Vector(-1, 0, 0));
        if (player.viewFrom.y <= this.position.y) this.visibleDirections.remove(new Vector(0, 1, 0));
        else if (player.viewFrom.y >= this.position.y + Chunk.size) this.visibleDirections.remove(new Vector(0, -1, 0));
        if (player.viewFrom.z <= this.position.z) this.visibleDirections.remove(new Vector(0, 0, 1));
        else if (player.viewFrom.z >= this.position.z + Chunk.size) this.visibleDirections.remove(new Vector(0, 0, -1));
    }

}
