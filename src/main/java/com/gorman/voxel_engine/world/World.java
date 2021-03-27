package com.gorman.voxel_engine.world;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JPanel;

import com.gorman.voxel_engine.player.Player;
import com.gorman.voxel_engine.window.Window;

/**
 * The World object handles the following:
 * - storing all world objects
 * - storing the player object
 * - the game loop:
 * 	- handle player input
 * 	- update world objects
 * 	- render world objects
 * 
 * @author Edward Gorman
 */
public class World extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	static Vector lightVector = new Vector(0, 0, -1);

	public boolean renderOutline = true;
	public boolean renderNormal = false;
	public ArrayList<Polygon> renderObjects = new ArrayList<Polygon>();

	public Player player;

	// Debug information
	public double fps;
	
	public World(){
		super();
		this.setSize(Window.screenSizeX, Window.screenSizeY);
		this.setFocusable(true);
		this.hideMouse();
		
		// Init player objects
		this.player = new Player(new Vector(0, 0, 0));
		this.addKeyListener(this.player.input);
		this.addMouseListener(this.player.input);
		this.addMouseMotionListener(this.player.input);
		this.addMouseWheelListener(this.player.input);
		
		// Init world objects
		chunks.add(new Chunk(new Vector(0, 0, 0)));
		for (int x = 0; x < 16; x++){
			for (int y = 0; y < 16; y++){
				for (int z = 0; z < 16; z++){
					try{ chunks.get(0).addVoxel(new Voxel(new Vector(x, y, z), Color.red)); }
					catch(Exception e){ System.out.println(e); }
				}
			}
		}
		// chunks.add(new Chunk(new Vector(0, -4, 0)));
		// try{
		// 	chunks.get(0).addVoxel(new Voxel(new Vector(0, -1, 0), Color.red));
		// 	chunks.get(0).addVoxel(new Voxel(new Vector(0, 0, 0), Color.red));
		// 	chunks.get(0).addVoxel(new Voxel(new Vector(0, 1, 0), Color.red));
		// }
		// catch(Exception e){ System.out.println(e); }

		this.update();
	}

	public void hideMouse(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT); 
		Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");        
		this.setCursor(invisibleCursor);
   	}

	public static void centerMouse(){
		try { new Robot().mouseMove(Window.screenSizeX/2, Window.screenSizeY/2); } 
		catch (AWTException e) { e.printStackTrace(); }
	}

	// World methods --------------------
	public void update(){
	}

	public void paint(Graphics g){
		// Clear screen and draw background
		super.paint(g);
		g.setColor(new Color(140, 180, 180));
		g.fillRect(0, 0, Window.screenSizeX, Window.screenSizeY);

		// Update user view
		this.player.processMovement();
		this.player.setPrederterminedInfo();

		// Update render objects
		this.setRenderObjects();
		Collections.sort(this.renderObjects);
		this.setPolygonMouseOver();

		// Draw Polygons to screen
		for (Polygon p : this.renderObjects)
			p.drawPolygon(g, this, this.player);
			
		// Draw Player UI
		this.player.drawUI(g, this);
	}

	public Voxel getVoxel(Vector p){
		for (Chunk c : this.chunks){
			if (c.position.x <= p.x && c.position.x + Chunk.size > p.x &&
				c.position.y <= p.y && c.position.y + Chunk.size > p.y &&
				c.position.z <= p.z && c.position.z + Chunk.size > p.z){
					try{ return c.getVoxel(p); }
					catch(Exception e){ System.out.println(e); }
				}
		}
		
		return null;
	}

	public void setRenderObjects(){
		this.renderObjects = new ArrayList<Polygon>();

		for (Chunk c : this.chunks){
			for (Voxel v : c.getVoxelList()){
				for (Polygon p : v.faces){
					p.update(this.player);

					// If the polygon is outside screen bounds
					if (!p.draw)
						continue;

					// If the player and polygon face same direction
					if (p.normal.dotProduct(this.player.viewFrom.subtract(p.getCentre())) >= 0)
						continue;

					// If polygon is facing another block
					if (this.getVoxel(v.position.add(p.normal.inverse())) != null)
						continue;

					// If here, object must be renderable
					this.renderObjects.add(p);
				}
			}
		}
	}
		
	public void setPolygonMouseOver(){
		this.player.polygonMouseOver = null;

		for (int i = this.renderObjects.size() - 1; i >= 0; i--){
			Polygon p = this.renderObjects.get(i);
			if (p.mouseOver() && p.draw && p.visible){
				this.player.polygonMouseOver = p;
				break;
			}
		}
	}

	public void run(){
		double checkFPS = 0;
		double maxFPS = 1000;
		double lastFPSCheck = 0;
		double lastRefresh = 0;

		while(true){
			long timeSLU = (long) (System.currentTimeMillis() - lastRefresh); 

			checkFPS++;
			if(checkFPS >= 100){
				this.fps = checkFPS/((System.currentTimeMillis() - lastFPSCheck)/1000.0);
				lastFPSCheck = System.currentTimeMillis();
				checkFPS = 0;
			}
			
			if(timeSLU < 1000.0/maxFPS){
				try {
					Thread.sleep((long) (1000.0/maxFPS - timeSLU));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}

			this.update();
			this.repaint();
			lastRefresh = System.currentTimeMillis();
		}
	}
	
}
