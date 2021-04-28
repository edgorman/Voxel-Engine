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

import com.gorman.voxel_engine.Main;
import com.gorman.voxel_engine.player.Player;
import com.gorman.voxel_engine.window.Window;
import com.gorman.voxel_engine.world.chunks.Chunk;
import com.gorman.voxel_engine.world.chunks.ChunkManager;
import com.gorman.voxel_engine.world.primitives.Polygon;
import com.gorman.voxel_engine.world.primitives.Vector;
import com.gorman.voxel_engine.world.terrain.FlatTerrain;
import com.gorman.voxel_engine.world.voxels.Voxel;

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

	public long seed;
	public ChunkManager chunks;
	
	public int totalChunks = 0;
	public int totalVoxels = 0;
	public int totalPolygons = 0;
	public boolean renderFill = true;
	public boolean renderOutline = true;
	public boolean renderNormal = false;
	public static Vector lightVector = new Vector(0, 0, 1);
	public ArrayList<Polygon> renderObjects = new ArrayList<Polygon>();

	public Player player;
	public Vector lastPlayerChunk;

	// Debug information
	public double fps;
	public double frames;

	public World(long s){
		super();
		this.setSize((int) Window.width, (int) Window.height);
		this.setFocusable(true);
		this.hideMouse();
		
		// Init player objects
		this.player = new Player(new Vector(0, 0, 70));
		this.addKeyListener(this.player.input);
		this.addMouseListener(this.player.input);
		this.addMouseMotionListener(this.player.input);
		this.addMouseWheelListener(this.player.input);
		
		// Init world objects
		this.seed = s;
		this.chunks = new ChunkManager(new FlatTerrain(s, 16), 5);
		this.update();
	}

	public void hideMouse(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT); 
		Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), "InvisibleCursor");        
		this.setCursor(invisibleCursor);
   	}

	public static void centerMouse(){
		try { new Robot().mouseMove((int) (Main.screenSizeX/2f), (int) (Main.screenSizeY/2f)); } 
		catch (AWTException e) { e.printStackTrace(); }
	}

	// World methods --------------------
	public void update(){
		// Update world chunks
		Vector pc = this.chunks.getChunkVector(this.player.viewFrom);
		if (!pc.equals(this.lastPlayerChunk)){
			this.chunks.getChunks(pc);
			this.lastPlayerChunk = pc;
		}
	}

	public void paint(Graphics g){
		// Clear screen and draw background
		super.paint(g);
		g.setColor(new Color(140, 180, 180));
		g.fillRect(0, 0, (int) Window.width, (int) Window.height);

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

		// Update frames
		this.frames++;
	}

	public void setRenderObjects(){
		this.totalChunks = 0;
		this.totalVoxels = 0;
		this.totalPolygons = 0;
		this.renderObjects = new ArrayList<Polygon>();

		for (int i = 0; i < this.chunks.loaded.size(); i++){
			Chunk c = this.chunks.loaded.get(i);
			c.setPrederterminedInfo(this.player);
			// if (c.update(player))
			// 		continue;
			this.totalChunks++;

			for (int j = 0; j < c.getVoxelList().size(); j++){
				Voxel v = c.getVoxelList().get(j);
				// if (v.update(player))
				// 	continue;
				this.totalVoxels++;

				for (int k = 0; k < v.faces.length; k++){
					Polygon p = v.faces[k];
					this.totalPolygons++;

					if (v.neighbors[k] == null)
						if(c.visibleDirections.contains(p.normal))
							if (p.update(this.player))
								this.renderObjects.add(p);

				}
			}
		}
	}
		
	public void setPolygonMouseOver(){
		this.player.polygonMouseOver = null;

		for (int i = this.renderObjects.size() - 1; i >= 0; i--){
			Polygon p = this.renderObjects.get(i);
			if (p.mouseOver()){
				this.player.polygonMouseOver = p;
				break;
			}
		}
	}

	public void run(){
		double maxFPS = 60;
		double lastFPSCheck = 0;
		double lastRefresh = 0;

		while(true){
			long delta = (long) (System.currentTimeMillis() - lastRefresh); 
			lastRefresh = System.currentTimeMillis();

			lastFPSCheck += delta;
			if(lastFPSCheck >= 1000){
				this.fps = this.frames;
				lastFPSCheck = 0;
				this.frames = 0;
			}
			
			if(delta < 1000.0/maxFPS){
				try {
					Thread.sleep((long) (1000.0/maxFPS - delta));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}

			this.repaint();
			this.update();
		}
	}
	
}
