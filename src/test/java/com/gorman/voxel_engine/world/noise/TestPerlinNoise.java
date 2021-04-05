package com.gorman.voxel_engine.world.noise;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestPerlinNoise {
    
    @Test
    public void checkObjectInstantiation(){
        PerlinNoise n = new PerlinNoise(0);

        assertEquals(0.11799970560000007, n.noise(3.14, 42, 7), 0);
    }

}
