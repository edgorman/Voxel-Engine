package com.gorman.voxel_engine;

import com.gorman.voxel_engine.window.Window;
import com.gorman.voxel_engine.world.World;

public class Main{
	public static void main(String[] args) throws InterruptedException
	{
		World world = new World();
		Window window = new Window("Voxel Terrain Generator");
        window.add(world);
		world.run();
	}
}
