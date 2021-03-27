package com.gorman.voxel_engine.world;

import com.gorman.voxel_engine.player.Player;

/**
 * The Vector object represents a point in 3D space.
 * Can also represent a 2D point by ignoring z attribute.
 * 
 * @author Edward Gorman
 */
public class Vector {
    public static double e = 0.0000001d;

	public double x;
    public double y;
    public double z;

	public Vector(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;

        if (Math.abs(this.x) < e) this.x = 0d;
        if (Math.abs(this.y) < e) this.y = 0d;
        if (Math.abs(this.z) < e) this.z = 0d;
	}

    public Vector(Vector v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;

        if (Math.abs(this.x) < e) this.x = 0d;
        if (Math.abs(this.y) < e) this.y = 0d;
        if (Math.abs(this.z) < e) this.z = 0d;
	}
	
    // General methods --------------------
	public String toString(){
        return String.format("%.0f,%.0f,%.0f", this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object o) { 
        if (o == this) return true; 
        if (!(o instanceof Vector)) return false; 
        
        Vector v = (Vector) o; 
        return this.x == v.x && this.y == v.y && this.z == v.z;
    } 

    // Basic vector methods --------------------
    public Vector add(Vector v){
        return new Vector(
            this.x + v.x,
            this.y + v.y,
            this.z + v.z
        );
    }

    public Vector subtract(Vector v){
        return new Vector(
            this.x - v.x,
            this.y - v.y,
            this.z - v.z
        );
    }

    public Vector absolute(Vector v){
        return new Vector(
            Math.abs(this.x - v.x),
            Math.abs(this.y - v.y),
            Math.abs(this.z - v.z)
        );
    }

    public Vector multiply(Vector v){
        return new Vector(
            this.x * v.x,
            this.y * v.y,
            this.z * v.z
        );
    }

    public double length(){
        return Math.sqrt(
            this.x * this.x +
            this.y * this.y +
            this.z * this.z
        );
    }

    public Vector inverse(){
        return new Vector(
            -this.x,
            -this.y,
            -this.z
        );
    }

    public Vector scale(double s){
        return new Vector(
            this.x * s,
            this.y * s,
            this.z * s
        );
    }

    public double sum(){
        return this.x + this.y + this.z;
    }

    // Advanced vector methods --------------------
    public Vector normalise(){
        if(this.length() > 0)
            return this.scale(1.0d / this.length());
        return this;
    }

    public double distance(Vector v){
        return Math.sqrt(
            (this.x - v.x) * (this.x - v.x) + 
            (this.y - v.y) * (this.y - v.y) +
            (this.z - v.z) * (this.z - v.z)
        );
    }

    public double dotProduct(Vector v){
        return (
            this.x * v.x + 
            this.y * v.y + 
            this.z * v.z
        );
    }

    public Vector crossProduct(Vector v){
        return new Vector(
            this.y * v.z - this.z * v.y,
            this.z * v.x - this.x * v.z,
            this.x * v.y - this.y * v.x
        );
    }

    // Projection code --------------------
    public Vector project(Player player){ 
		Vector viewVector = this.subtract(player.viewFrom);
        Vector playerVector = player.viewPlane.p;
        Vector playerNormal = player.viewPlane.normal;  // may need to normalise this...

		double scale = (playerNormal.multiply(playerVector).sum() - playerNormal.multiply(player.viewFrom).sum()) / playerNormal.multiply(viewVector).sum();
        Vector viewScale = viewVector.scale(scale).add(player.viewFrom);
		
		return new Vector(
			player.viewW2.dotProduct(viewScale), 
			player.viewW1.dotProduct(viewScale),
			scale
		);
	}
}
