package com.gorman.voxel_engine.world;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import java.awt.Color;
import java.util.ArrayList;

public class TestWorld
{

    @Test
    public void checkObjectInstantiation(){
        World w = new World();

        assertNotNull(w);
    }

    @Test
    public void checkGetVoxel() throws Exception{
        World w = new World();
        w.chunks = new ArrayList<Chunk>();
        
        w.chunks.add(new Chunk(new Vector(0, 0, 0)));
        w.chunks.get(0).addVoxel(new Voxel(new Vector(0, 0, 0), Color.RED));

        assertNotNull(w.getVoxel(new Vector(0, 0, 0)));
        assertNull(w.getVoxel(new Vector(1, 1, 1)));
    }
}
