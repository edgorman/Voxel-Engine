package com.gorman.voxel_engine.player;

import java.awt.Color;
import java.awt.Graphics;

import com.gorman.voxel_engine.window.Window;
import com.gorman.voxel_engine.world.World;
import com.gorman.voxel_engine.world.primitives.Plane;
import com.gorman.voxel_engine.world.primitives.Polygon;
import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.voxels.Voxel;

/**
 * The Player object represents the player in the game world.
 * 
 * @author Edward Gorman
 */
public class Player{
    
	private double movementSpeed;
	private double horzLookSpeed;
	private double vertLookSpeed;
	private double horzLook;
	private double vertLook;
	public double zoom;
	private double minZoom;
	private double maxZoom;
	public int aimSight;
	
	public Vector viewFrom;
	public Vector viewTo;
	public Vector viewVector;
	public Vector viewW1;
	public Vector viewW2;
	public Vector viewFocus;
	public Plane viewPlane;
	
	public Listener input;
	public Polygon polygonMouseOver;

	public Player(Vector vf){
		this.movementSpeed = 0.25;
		this.horzLookSpeed = 900;
		this.vertLookSpeed = 2200;
		this.horzLook = 0;
		this.vertLook = -0.2;
		this.zoom = 1000;
		this.minZoom = 500;
		this.maxZoom = 2500;
		this.aimSight = 4;

		this.viewFrom = vf;
		this.viewTo = new Vector(0, 0, 0);
		this.input = new Listener(this);
		this.polygonMouseOver = null;
	}

	public void processMouse(){
		double difX = (this.input.mouseX - Window.screenSizeX/2);
		double difY = (this.input.mouseY - Window.screenSizeY/2);
		difY *= 6 - Math.abs(this.vertLook) * 5;

		this.vertLook -= difY  / this.vertLookSpeed;
		this.horzLook += difX / this.horzLookSpeed;
		this.vertLook = Math.min(this.vertLook, 0.999);
		this.vertLook = Math.max(this.vertLook, -0.999);

		if(this.input.leftClick)
			if(this.polygonMouseOver != null)
				this.polygonMouseOver.seeThrough = false;
		if(this.input.rightClick)
			if(this.polygonMouseOver != null)
				this.polygonMouseOver.seeThrough = true;
		
		if(this.input.mouseScroll > 0)
			if(this.zoom > this.minZoom)
				this.zoom -= 25 * this.input.mouseScroll;
		if(this.input.mouseScroll < 0)
			if(this.zoom < this.maxZoom)
				this.zoom -= 25 * this.input.mouseScroll;
		this.input.mouseScroll = 0;

		// Update view vector
		double r = Math.sqrt(1 - (this.vertLook * this.vertLook));
		this.viewTo = new Vector(
			viewFrom.x + r * Math.cos(this.horzLook),
			viewFrom.y + r * Math.sin(this.horzLook),
			viewFrom.z + this.vertLook
		);
	}

    public void processMovement(){
		if(this.input.exit) System.exit(0);

		this.viewVector = this.viewTo.subtract(this.viewFrom).normalise();
		Vector move = new Vector(0, 0, 0);
		double speed = this.movementSpeed;
		Vector vertVector = new Vector (0, 0, 1).normalise();
		Vector horzVector = this.viewVector.crossProduct(vertVector);
		
		if(this.input.forward) move = move.add(this.viewVector);
		if(this.input.back) move = move.subtract(this.viewVector);
		if(this.input.left) move = move.add(horzVector);
		if(this.input.right) move = move.subtract(horzVector);
		if(this.input.up) move = move.add(vertVector);
		if(this.input.down) move = move.subtract(vertVector);
		if(this.input.sprint) speed = speed * 3;

		// Update view vector
		this.viewFrom = move.normalise().scale(speed).add(this.viewFrom);
		double r = Math.sqrt(1 - (this.vertLook * this.vertLook));
		this.viewTo = new Vector(
			viewFrom.x + r * Math.cos(this.horzLook),
			viewFrom.y + r * Math.sin(this.horzLook),
			viewFrom.z + this.vertLook
		);
	}

	public void setPrederterminedInfo(){
		this.viewVector = this.viewTo.subtract(this.viewFrom).normalise();
		Vector DirectionVector = new Vector(1, 1, 1).normalise();
		Vector PlaneVector1 = this.viewVector.crossProduct(DirectionVector);
		Vector PlaneVector2 = this.viewVector.crossProduct(PlaneVector1);
		this.viewPlane = new Plane(this.viewTo, PlaneVector1, PlaneVector2);

		double dx = Math.abs(viewFrom.x-viewTo.x);
		double dy = Math.abs(viewFrom.y-viewTo.y);
		double xRot = dy/(dx+dy);
		double yRot = dx/(dx+dy);
		if(viewFrom.y > viewTo.y) xRot = -xRot;
		if(viewFrom.x < viewTo.x) yRot = -yRot;
		Vector RotationVector = new Vector(xRot, yRot, 0).normalise();
		this.viewW1 = this.viewVector.crossProduct(RotationVector);
		this.viewW2 = this.viewVector.crossProduct(this.viewW1);

		this.viewFocus = this.viewTo.project(this);
		this.viewFocus.x = this.zoom * this.viewFocus.x;
		this.viewFocus.y = this.zoom * this.viewFocus.y;
	}

	public void drawUI(Graphics g, World w){
		g.setColor(Color.black);
		g.drawLine(Window.screenSizeX/2 - this.aimSight, Window.screenSizeY/2, Window.screenSizeX/2 + this.aimSight, Window.screenSizeY/2);
		g.drawLine(Window.screenSizeX/2, Window.screenSizeY/2 - this.aimSight, Window.screenSizeX/2, Window.screenSizeY/2 + this.aimSight);

		if (this.input.debug){
			g.setColor(Color.black);
			g.drawString("Voxel Terrain - v0.1", 10, 20);
			g.drawString("FPS: " + (int) w.fps, 10, 40);
			g.drawString("xyz: " + this.viewFrom.scale(1/Voxel.length), 10, 60);
			g.drawString("Look: " + this.viewTo.subtract(this.viewFrom), 10, 80);
			g.drawString("Zoom: " + this.zoom, 10, 100);
			
			g.drawString("Objects loaded: ", 10, 160);
			g.drawString(" Chunks: " + w.chunks.map.size() / w.chunks.terrain.maxZ, 10, 180);
			g.drawString(" Voxels: " + "?", 10, 200);
			g.drawString(" Polygons: " + w.renderObjects.size() + "/" + w.totalPolygons, 10, 220);
		}
	}
}
