package com.gorman.voxel_engine.player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import com.gorman.voxel_engine.world.World;
import com.gorman.voxel_engine.world.voxels.*;
import com.gorman.voxel_engine.world.primitives.Vector;

/**
 * The Listener object handles user input.
 * 
 * @author Edward Gorman
 */
public class Listener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
    
    private Player player;
	public boolean left, right, forward, back, up, down, sprint, exit, debug;
    public int mouseX, mouseY, mouseScroll;
    public boolean rightClick, leftClick, mouseDown;
    public long mouseDownTime;

    public Listener(Player p){
        this.player = p;
    }

	public void keyPressed(KeyEvent key) {
        switch(key.getKeyCode()) {
            case KeyEvent.VK_W: forward = true; break;
            case KeyEvent.VK_D: right = true; break;
            case KeyEvent.VK_S: back = true; break;
            case KeyEvent.VK_A: left = true; break;
            case KeyEvent.VK_SPACE: up = true; break;
            case KeyEvent.VK_CONTROL: down = true; break;
            case KeyEvent.VK_SHIFT: sprint = true; break;
            case KeyEvent.VK_ESCAPE: exit = true; break;
            case KeyEvent.VK_F1: debug = !debug; break;
            case KeyEvent.VK_1: try{ player.voxelConstructor = Stone.class.getDeclaredConstructor(Vector.class); } catch (Exception e) { }; break;
            case KeyEvent.VK_2: try{ player.voxelConstructor = Grass.class.getDeclaredConstructor(Vector.class); } catch (Exception e) { }; break;
            case KeyEvent.VK_3: try{ player.voxelConstructor = Wood.class.getDeclaredConstructor(Vector.class); } catch (Exception e) { }; break;
            case KeyEvent.VK_4: try{ player.voxelConstructor = Sand.class.getDeclaredConstructor(Vector.class); } catch (Exception e) { }; break;
            case KeyEvent.VK_5: try{ player.voxelConstructor = Water.class.getDeclaredConstructor(Vector.class); } catch (Exception e) { }; break;
            default:
        }
	}

	public void keyReleased(KeyEvent key) {
		switch(key.getKeyCode()) {
            case KeyEvent.VK_W: forward = false; break;
            case KeyEvent.VK_D: right = false; break;
            case KeyEvent.VK_S: back = false; break;
            case KeyEvent.VK_A: left = false; break;
            case KeyEvent.VK_SPACE: up = false; break;
            case KeyEvent.VK_CONTROL: down = false; break;
            case KeyEvent.VK_SHIFT: sprint = false; break;
            case KeyEvent.VK_ESCAPE: exit = false; break;
            default:
        }
	}

	public void keyTyped(KeyEvent arg0) {
	}

    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseScroll = e.getUnitsToScroll();
        this.player.processMouse();
    }

    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
		mouseY = e.getY();
        World.centerMouse();
        this.player.processMouse();
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
		mouseY = e.getY();
        World.centerMouse();
        this.player.processMouse();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        mouseDown = true;
        mouseDownTime = System.currentTimeMillis();
        switch(e.getButton()){
            case MouseEvent.BUTTON1: leftClick = true; break;
            case MouseEvent.BUTTON3: rightClick = true; break;
            default:
        }
        this.player.processMouse();
    }

    public void mouseReleased(MouseEvent e) {
        mouseDown = false;
        switch(e.getButton()){
            case MouseEvent.BUTTON1: leftClick = false; break;
            case MouseEvent.BUTTON3: rightClick = false; break;
            default:
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
