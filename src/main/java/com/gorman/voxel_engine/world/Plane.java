package com.gorman.voxel_engine.world;

/**
 * The Plane object represents a plane in 3D space.
 * 
 * @author Edward Gorman
 */
public class Plane {
	
	public Vector p;
	private Vector r1;
	private Vector r2;
	public Vector normal;

	public Plane(Polygon p){
		this.p = p.getCentre();
		this.r1 = p.vertexes[1].subtract(p.vertexes[0]); //.normalise();
		this.r2 = p.vertexes[2].subtract(p.vertexes[0]); //.normalise();
		this.normal = this.r1.crossProduct(this.r2);
	}
	
	public Plane(Vector p, Vector r1, Vector r2){
		this.p = new Vector(p);
		this.r1 = new Vector(r1);
		this.r2 = new Vector(r2);
		this.normal = this.r1.crossProduct(this.r2);
	}
}
