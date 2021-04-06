package com.gorman.voxel_engine.world.voxels;

import java.awt.Color;

import com.gorman.voxel_engine.world.primitives.Vector;

public class Water extends Voxel {

    public Water(Vector p) {
        super(p, new Color(82, 92, 186, 220));
    }
    
}
