package com.gorman.voxel_engine.world.voxels;

import java.awt.Color;

import com.gorman.voxel_engine.world.primitives.Vector;

public class Grass extends Voxel{

    public Grass(Vector p) {
        super(p, new Color(24, 87, 39, 255), true);
    }
    
}
