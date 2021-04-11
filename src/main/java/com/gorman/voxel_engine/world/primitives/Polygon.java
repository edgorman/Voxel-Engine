package com.gorman.voxel_engine.world.primitives;

import java.awt.Color;
import java.awt.Graphics;

import com.gorman.voxel_engine.player.Player;
import com.gorman.voxel_engine.window.Window;
import com.gorman.voxel_engine.world.World;
import com.gorman.voxel_engine.world.voxels.Voxel;

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
	public Vector projectC;
	public Vector projectN;

	public Color color;
	public double alpha = 255;
	public double lighting = 1;

	public double distance;
	
	public Polygon(double[] x, double[] y,  double[] z, Color c){	
		this.vertexes = new Vector[x.length];
		for (int i = 0; i < x.length; i++)
			this.vertexes[i] = new Vector(x[i], y[i], z[i]);
		this.normal = new Plane(this).normal.normalise();

		this.color = c;
	}
	
	public boolean update(Player player){
		this.normal = new Plane(this).normal.normalise();

		// If the player and polygon face same direction
		if (this.normal.dotProduct(this.vertexes[0].subtract(player.viewFrom)) >= 0)
			return false;
		
		
		// Calculate centre point
		Vector project = this.getCentre().project(player);
		if (project.z > 0.5)
			return false;
		this.projectC = new Vector(
			((Window.screenSizeX/2 - player.viewFocus.x) + project.x * player.zoom),
			((Window.screenSizeY/2 - player.viewFocus.y) + project.y * player.zoom),
			project.z
		);

		// Calculate points in projected space
		this.projectX = new int[this.vertexes.length];
		this.projectY = new int[this.vertexes.length];
		for (int i = 0; i < this.vertexes.length; i++){
			project = this.vertexes[i].project(player);

			if (project.z < 0)
				return false;

			this.projectX[i] = (int) ((Window.screenSizeX/2 - player.viewFocus.x) + project.x * player.zoom);
			this.projectY[i] = (int) ((Window.screenSizeY/2 - player.viewFocus.y) + project.y * player.zoom);
		}
		
		// Calculate polygon lighting
		double angle = Math.acos(World.lightVector.multiply(this.normal).sum() / World.lightVector.length());
		this.lighting = 0.2 + 1 - Math.sqrt(Math.toDegrees(angle)/180);
		this.lighting = Math.min(Math.max(this.lighting, 0), 1);

		// Calculate normal line
		project = this.getCentre().add(this.normal.scale(Voxel.length / 2)).project(player);
		this.projectN = new Vector(
			((Window.screenSizeX/2 - player.viewFocus.x) + project.x * player.zoom),
			((Window.screenSizeY/2 - player.viewFocus.y) + project.y * player.zoom),
			project.z
		);

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
		if (this.alpha > 0){
			g.setColor(
				new Color(
					(int)(this.color.getRed() * lighting), 
					(int)(this.color.getGreen() * lighting), 
					(int)(this.color.getBlue() * lighting),
					(int)(Math.min(this.color.getAlpha(), this.alpha))
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

		if (w.renderNormal){
			if (this.projectN.z >= 0){
				g.setColor(new Color(0, 0, 0));
				g.drawLine((int) this.projectC.x, (int) this.projectC.y, (int) this.projectN.x, (int) this.projectN.y);
			}
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
