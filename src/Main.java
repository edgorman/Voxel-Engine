import window.Window;
import world.World;

public class Main{
	public static void main(String[] args) throws InterruptedException
	{
		World world = new World();
		Window window = new Window("Voxel Terrain Generator");
        window.add(world);
		world.run();
	}
}
