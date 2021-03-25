package com.gorman.voxel_engine.window;

import javax.swing.JFrame;

/**
 * The Window object handles the following:
 * - creation of JFrame window
 * - managing JPanel objects
 * - rendering of objects
 * 
 * @author Edward Gorman
 */
public class Window extends JFrame{
    
    private static final long serialVersionUID = 1L;
    
    public static int screenSizeX = 1920;
    public static int screenSizeY = 1080;

    public Window(String title) {
        super(title);

        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(screenSizeX, screenSizeY);
        // this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setVisible(true);
    }
}
