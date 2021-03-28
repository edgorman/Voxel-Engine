package com.gorman.voxel_engine.world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

import com.gorman.voxel_engine.player.Player;
import com.gorman.voxel_engine.window.Window;

/**
 * The Polygon3D object represents a polygon in 3D space.
 * 
 * @author Edward Gorman
 */
public class Polygon implements Comparable<Polygon>{

	public Vector[] vertexes;
	public Vector normal;
	public int[] projectX;
	public int[] projectY;
	public int projectCX;
	public int projectCY;
	public int projectNX;
	public int projectNY;

	public Color color;
	public double lighting = 1;

	public double distance;
	public boolean seeThrough;
	
	public Polygon(double[] x, double[] y,  double[] z, Color c, boolean s){	
		this.vertexes = new Vector[x.length];
		for (int i = 0; i < x.length; i++)
			this.vertexes[i] = new Vector(x[i], y[i], z[i]);
		this.normal = new Plane(this).normal.normalise();

		this.color = c;
		this.seeThrough = s; 
	}
	
	public boolean update(Player player){
		this.normal = new Plane(this).normal.normalise();

		// If the player and polygon face same direction
		if (this.normal.dotProduct(player.viewFrom.subtract(this.vertexes[0])) >= 0)	// this.getCentre()
			return false;

		// Calculate points in projected space
		this.projectX = new int[this.vertexes.length];
		this.projectY = new int[this.vertexes.length];
		for (int i=0; i<this.vertexes.length; i++){
			Vector project = this.vertexes[i].project(player);

			if (project.z < 0)
				return false;

			this.projectX[i] = (int) ((Window.screenSizeX/2 - player.viewFocus.x) + project.x * player.zoom);
			this.projectY[i] = (int) ((Window.screenSizeY/2 - player.viewFocus.y) + project.y * player.zoom);
		}
		
		// Calculate polygon lighting
		double angle = Math.acos(World.lightVector.multiply(this.normal).sum() / World.lightVector.length());
		this.lighting = 0.2 + 1 - Math.sqrt(Math.toDegrees(angle)/180);
		this.lighting = Math.min(Math.max(this.lighting, 0), 1);

		// Calculate normal line from center
		this.projectCX = (int) Arrays.stream(this.projectX).average().getAsDouble();
		this.projectCY = (int) Arrays.stream(this.projectY).average().getAsDouble();
		this.projectNX = this.projectCX;
		this.projectNY = this.projectCY;

		// Calculate distance to player
		double total = 0;
		for(Vector v : this.vertexes)
			total += player.viewFrom.distance(v);
		this.distance = total / this.vertexes.length;

		return true;
	}

	public Vector getCentre(){
		Vector m = new Vector(0, 0, 0);
		for (Vector v : this.vertexes)
			m = m.add(v);
		return m.scale((double) 1 / this.vertexes.length);
	}
	
	public void drawPolygon(Graphics g, World w, Player p){
		if (!this.seeThrough){
			g.setColor(
				new Color(
					(int)(this.color.getRed() * lighting), 
					(int)(this.color.getGreen() * lighting), 
					(int)(this.color.getBlue() * lighting)
				)
			);
			g.fillPolygon(this.projectX, this.projectY, this.projectX.length);
		}

		if (p.polygonMouseOver == this){
			g.setColor(new Color(255, 255, 255, 100));
			g.fillPolygon(this.projectX, this.projectY, this.projectX.length);
		}
		
		if (w.renderOutline){
			g.setColor(new Color(0, 0, 0));
			g.drawPolygon(this.projectX, this.projectY, this.projectX.length);
		}

		if(w.renderNormal){
			g.setColor(new Color(0, 0, 0));
			g.drawLine(this.projectCX, this.projectCY, this.projectNX, this.projectNY);
		}
	}

	public boolean mouseOver(){
		double x = Window.screenSizeX/2;
		double y = Window.screenSizeY/2;
		int j = this.projectX.length-1 ;
		boolean oddNodes = false;

		for (int i=0; i < this.projectX.length; i++) {
			if (
				(this.projectY[i]< y && this.projectY[j]>=y || this.projectY[j]< y && this.projectY[i]>=y) &&  
				(this.projectX[i]<=x || this.projectX[j]<=x)
			   ) {
				oddNodes ^= (this.projectX[i]+(y-this.projectY[i])/(this.projectY[j]-this.projectY[i])*(this.projectX[j]-this.projectX[i])<x); 
			}
			j=i; 
		}

		return oddNodes;
	}

	@Override
	public int compareTo(Polygon p) {
		if (this.distance < p.distance) return 1;
		else if (p.distance < this.distance) return -1;
		else return 0;
	}
}
