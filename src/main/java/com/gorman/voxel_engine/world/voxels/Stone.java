package com.gorman.voxel_engine.world.voxels;

import java.awt.Color;

import com.gorman.voxel_engine.world.primitives.Vector;

public class Stone extends Voxel {

    public Stone(Vector p) {
        super(p, new Color(115, 115, 115, 255), true);
    }
    
}
