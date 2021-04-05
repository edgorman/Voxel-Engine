package com.gorman.voxel_engine.world.noise;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestSimplexNoise {
    
    @Test
    public void checkObjectInstantiation(){
        SimplexNoise n = new SimplexNoise(13);

        assertEquals(0.04206944148970622, n.noise(69, 420, 0), 0);
        assertEquals(0.04206944148970622, n.noise(69, 420, 10), 0);

    }

}
