package com.gorman.voxel_engine.world;

import java.awt.Color;

/**
 * The Voxel object represents a cube in 3D space.
 * 
 * @author Edward Gorman
 */
public class Voxel {
	public Vector position;
	public double rotation;
	static double length = 1;
	public Color color;
	public Polygon[] faces;
	
	public Voxel(Vector p, Color c){
		this.position = p;
		this.rotation = 0;
		this.color = c;

		this.faces = new Polygon[6];
		this.faces[0] = new Polygon(new double[]{p.x, p.x+length, p.x+length, p.x}, new double[]{p.y, p.y, p.y+length, p.y+length},  new double[]{p.z, p.z, p.z, p.z}, c, false);
		this.faces[1] = new Polygon(new double[]{p.x, p.x+length, p.x+length, p.x}, new double[]{p.y, p.y, p.y+length, p.y+length},  new double[]{p.z+length, p.z+length, p.z+length, p.z+length}, c, false);
		this.faces[2] = new Polygon(new double[]{p.x, p.x, p.x+length, p.x+length}, new double[]{p.y, p.y, p.y, p.y},  new double[]{p.z, p.z+length, p.z+length, p.z}, c, false);
		this.faces[3] = new Polygon(new double[]{p.x+length, p.x+length, p.x+length, p.x+length}, new double[]{p.y, p.y, p.y+length, p.y+length},  new double[]{p.z, p.z+length, p.z+length, p.z}, c, false);
		this.faces[4] = new Polygon(new double[]{p.x, p.x, p.x+length, p.x+length}, new double[]{p.y+length, p.y+length, p.y+length, p.y+length},  new double[]{p.z, p.z+length, p.z+length, p.z}, c, false);
		this.faces[5] = new Polygon(new double[]{p.x, p.x, p.x, p.x}, new double[]{p.y, p.y, p.y+length, p.y+length},  new double[]{p.z, p.z+length, p.z+length, p.z}, c, false);
		
		this.update();
	}

	public void update(){
		double radius = Math.sqrt(length*length + length*length);
		double x1 = this.position.x+length*0.5+radius*0.5*Math.cos(rotation + ((0.00+0.75) * Math.PI));
		double x2 = this.position.x+length*0.5+radius*0.5*Math.cos(rotation + ((0.50+0.75) * Math.PI));
		double x3 = this.position.x+length*0.5+radius*0.5*Math.cos(rotation + ((1.00+0.75) * Math.PI));
		double x4 = this.position.x+length*0.5+radius*0.5*Math.cos(rotation + ((1.50+0.75) * Math.PI));
		
		double y1 = this.position.y+length*0.5+radius*0.5*Math.sin(rotation + ((0.00+0.75) * Math.PI));
		double y2 = this.position.y+length*0.5+radius*0.5*Math.sin(rotation + ((0.50+0.75) * Math.PI));
		double y3 = this.position.y+length*0.5+radius*0.5*Math.sin(rotation + ((1.00+0.75) * Math.PI));
		double y4 = this.position.y+length*0.5+radius*0.5*Math.sin(rotation + ((1.50+0.75) * Math.PI));
		
		this.faces[0].vertexes = new Vector[]{
			new Vector(x1, y1, this.position.z),
			new Vector(x2, y2, this.position.z),
			new Vector(x3, y3, this.position.z),
			new Vector(x4, y4, this.position.z),
		};

		this.faces[1].vertexes = new Vector[]{
			new Vector(x4, y4, this.position.z+length),
			new Vector(x3, y3, this.position.z+length),
			new Vector(x2, y2, this.position.z+length),
			new Vector(x1, y1, this.position.z+length),
		};
		
		this.faces[2].vertexes = new Vector[]{
			new Vector(x1, y1, this.position.z),
			new Vector(x1, y1, this.position.z+length),
			new Vector(x2, y2, this.position.z+length),
			new Vector(x2, y2, this.position.z),
		};

		this.faces[3].vertexes = new Vector[]{
			new Vector(x2, y2, this.position.z),
			new Vector(x2, y2, this.position.z+length),
			new Vector(x3, y3, this.position.z+length),
			new Vector(x3, y3, this.position.z),
		};

		this.faces[4].vertexes = new Vector[]{
			new Vector(x3, y3, this.position.z),
			new Vector(x3, y3, this.position.z+length),
			new Vector(x4, y4, this.position.z+length),
			new Vector(x4, y4, this.position.z),
		};

		this.faces[5].vertexes = new Vector[]{
			new Vector(x4, y4, this.position.z),
			new Vector(x4, y4, this.position.z+length),
			new Vector(x1, y1, this.position.z+length),
			new Vector(x1, y1, this.position.z),
		};

	}

}
