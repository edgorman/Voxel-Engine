package com.gorman.voxel_engine.world.voxels;

import java.awt.Color;

import com.gorman.voxel_engine.world.primitives.Vector;

public class Sand extends Voxel {

    public Sand(Vector p) {
        super(p, new Color(186, 184, 82, 255), true);
    }
    
}
