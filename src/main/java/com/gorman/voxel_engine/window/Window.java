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
    
    public static double width = 1920;
    public static double height = 1080;

    public Window(String title, double w, double h) {
        super(title);
        Window.width = w;
        Window.height = h;

        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize((int) Window.width, (int) Window.height);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setVisible(true);
    }
}
