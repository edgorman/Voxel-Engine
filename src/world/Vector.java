package world;

import player.Player;

/**
 * The Vector object represents a point in 3D space.
 * Can also represent a 2D point by ignoring z attribute.
 * 
 * @author Edward Gorman
 */
public class Vector {
	public double x;
    public double y;
    public double z;

	public Vector(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

    public Vector(Vector v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
    // General methods --------------------
	public String toString(){
        return (int) this.x + "," + (int) this.y + "," + (int) this.z;
    }

    @Override
    public boolean equals(Object o) { 
        if (o == this) return true; 
        if (!(o instanceof Vector)) return false; 
        
        Vector v = (Vector) o; 
        return this.x == v.x && this.y == v.y && this.z == v.z;
    } 

    // Basic mathematical methods --------------------
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

    // Advanced mathematical methods --------------------
    public Vector normalise(){
        if(this.length() > 0)
            return this.scale(1 / this.length());
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
        Vector playerNormal = player.viewPlane.normal;

		double scale = (playerNormal.multiply(playerVector).sum() - playerNormal.multiply(player.viewFrom).sum()) / playerNormal.multiply(viewVector).sum();
        Vector viewScale = viewVector.scale(scale).add(player.viewFrom);
		
		return new Vector(
			player.viewW2.x * viewScale.x + player.viewW2.y * viewScale.y + player.viewW2.z * viewScale.z, 
			player.viewW1.x * viewScale.x + player.viewW1.y * viewScale.y + player.viewW1.z * viewScale.z, 
			scale
		);
	}
}
