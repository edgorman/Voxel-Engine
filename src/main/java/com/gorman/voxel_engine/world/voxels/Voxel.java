package com.gorman.voxel_engine.world.voxels;

import java.awt.Color;

import com.gorman.voxel_engine.world.primitives.Polygon;
import com.gorman.voxel_engine.world.primitives.Vector;

/**
 * The Voxel object represents a cube in 3D space.
 * 
 * @author Edward Gorman
 */
public abstract class Voxel {
	public static double length = 2;
	
	public Vector position;
	public Vector ps;
	public double rotation;
	public Color color;
	public Polygon[] faces;
	
	public Voxel(Vector p, Color c){
		this.position = p;
		this.ps = this.position.scale(Voxel.length);
		this.rotation = 0;
		this.color = c;

		this.faces = new Polygon[6];
		this.faces[0] = new Polygon(new double[]{ps.x, ps.x+length, ps.x+length, ps.x}, new double[]{ps.y, ps.y, ps.y+length, ps.y+length},  new double[]{ps.z, ps.z, ps.z, ps.z}, c, false);
		this.faces[1] = new Polygon(new double[]{ps.x, ps.x+length, ps.x+length, ps.x}, new double[]{ps.y, ps.y, ps.y+length, ps.y+length},  new double[]{ps.z+length, ps.z+length, ps.z+length, ps.z+length}, c, false);
		this.faces[2] = new Polygon(new double[]{ps.x, ps.x, ps.x+length, ps.x+length}, new double[]{ps.y, ps.y, ps.y, ps.y},  new double[]{ps.z, ps.z+length, ps.z+length, ps.z}, c, false);
		this.faces[3] = new Polygon(new double[]{ps.x+length, ps.x+length, ps.x+length, ps.x+length}, new double[]{ps.y, ps.y, ps.y+length, ps.y+length},  new double[]{ps.z, ps.z+length, ps.z+length, ps.z}, c, false);
		this.faces[4] = new Polygon(new double[]{ps.x, ps.x, ps.x+length, ps.x+length}, new double[]{ps.y+length, ps.y+length, ps.y+length, ps.y+length},  new double[]{ps.z, ps.z+length, ps.z+length, ps.z}, c, false);
		this.faces[5] = new Polygon(new double[]{ps.x, ps.x, ps.x, ps.x}, new double[]{ps.y, ps.y, ps.y+length, ps.y+length},  new double[]{ps.z, ps.z+length, ps.z+length, ps.z}, c, false);
		
		this.update();
	}

	public void update(){
		double radius = Math.sqrt(length*length + length*length);
		double x1 = this.ps.x+length*0.5+radius*0.5*Math.cos(rotation + ((0.00+0.75) * Math.PI));
		double x2 = this.ps.x+length*0.5+radius*0.5*Math.cos(rotation + ((0.50+0.75) * Math.PI));
		double x3 = this.ps.x+length*0.5+radius*0.5*Math.cos(rotation + ((1.00+0.75) * Math.PI));
		double x4 = this.ps.x+length*0.5+radius*0.5*Math.cos(rotation + ((1.50+0.75) * Math.PI));
		
		double y1 = this.ps.y+length*0.5+radius*0.5*Math.sin(rotation + ((0.00+0.75) * Math.PI));
		double y2 = this.ps.y+length*0.5+radius*0.5*Math.sin(rotation + ((0.50+0.75) * Math.PI));
		double y3 = this.ps.y+length*0.5+radius*0.5*Math.sin(rotation + ((1.00+0.75) * Math.PI));
		double y4 = this.ps.y+length*0.5+radius*0.5*Math.sin(rotation + ((1.50+0.75) * Math.PI));
		
		this.faces[0].vertexes = new Vector[]{
			new Vector(x1, y1, this.ps.z),
			new Vector(x2, y2, this.ps.z),
			new Vector(x3, y3, this.ps.z),
			new Vector(x4, y4, this.ps.z),
		};

		this.faces[1].vertexes = new Vector[]{
			new Vector(x4, y4, this.ps.z+length),
			new Vector(x3, y3, this.ps.z+length),
			new Vector(x2, y2, this.ps.z+length),
			new Vector(x1, y1, this.ps.z+length),
		};
		
		this.faces[2].vertexes = new Vector[]{
			new Vector(x1, y1, this.ps.z),
			new Vector(x1, y1, this.ps.z+length),
			new Vector(x2, y2, this.ps.z+length),
			new Vector(x2, y2, this.ps.z),
		};

		this.faces[3].vertexes = new Vector[]{
			new Vector(x2, y2, this.ps.z),
			new Vector(x2, y2, this.ps.z+length),
			new Vector(x3, y3, this.ps.z+length),
			new Vector(x3, y3, this.ps.z),
		};

		this.faces[4].vertexes = new Vector[]{
			new Vector(x3, y3, this.ps.z),
			new Vector(x3, y3, this.ps.z+length),
			new Vector(x4, y4, this.ps.z+length),
			new Vector(x4, y4, this.ps.z),
		};

		this.faces[5].vertexes = new Vector[]{
			new Vector(x4, y4, this.ps.z),
			new Vector(x4, y4, this.ps.z+length),
			new Vector(x1, y1, this.ps.z+length),
			new Vector(x1, y1, this.ps.z),
		};

	}

}
