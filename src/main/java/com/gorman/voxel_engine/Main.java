package com.gorman.voxel_engine;

import com.gorman.voxel_engine.player.Player;
import com.gorman.voxel_engine.window.Window;
import com.gorman.voxel_engine.world.World;
import com.gorman.voxel_engine.world.primitives.Vector;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Main{

	public static double screenSizeX = 0;
    public static double screenSizeY = 0;
	public static void main(String[] args) throws InterruptedException
	{
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        Main.screenSizeX = size.getWidth();
        Main.screenSizeY = size.getHeight();
		
		Player player = new Player(new Vector(0, 0, 70));
		World world = new World(0, player);
		Window window = new Window("Voxel Terrain Generator");
        window.add(world);
		world.run();
	}
}
