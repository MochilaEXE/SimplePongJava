package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Racket implements KeyListener{
	public int x, y;
	int upKey, downKey, changeNextKey, changePreviousKey, vel;
	boolean upPressed, downPressed;
	public final short width = 60;
	public final short height = 250;
	// Color variables
	private final Color[] PossibleColors = new Color[]{
			Color.white,
			Color.cyan,
			Color.blue,
			Color.pink,
			Color.magenta,
			Color.green,
			Color.yellow,
			Color.orange,
			Color.red,
			Color.lightGray
			};
	private short colorIndex = 0;
	public Color color = PossibleColors[colorIndex];
	
	public Racket(int x, int y, int upKey, int downKey, int changeNextKey, int changePreviousKey) {
		this.x = x;
		this.y = y;
		this.upKey = upKey;
		this.downKey = downKey;
		this.changeNextKey = changeNextKey;
		this.changePreviousKey = changePreviousKey;
		vel = 0;
	}
	
	public void draw(Graphics g) {
		color = PossibleColors[colorIndex];
		g.setColor(color);
		g.fillRect(x, y, width, height);
		g.setColor(Color.white);
	}
	
	public void update() {
		if(upPressed) {
			y -= 8;
		}
		if(downPressed) {
			y += 8;
		}
		
		if(y + height > 1000) {
			y = 1000-height;
		}
		if(y < 0) {
			y = 0;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == upKey) {
			upPressed = true;
		}else if (e.getKeyCode() == downKey) {
			downPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		if(e.getKeyCode() == upKey) {
			upPressed = !true;
		}else if (e.getKeyCode() == downKey) {
			downPressed = !true;
		
		}else {
			// check for color change
			if(e.getKeyCode() == changeNextKey) {
				colorIndex++;
			}else if (e.getKeyCode() == changePreviousKey) {
				colorIndex--;
			}
			
			// check bounds
			if(colorIndex >= PossibleColors.length) {
				colorIndex = 0;
			}else if (colorIndex < 0) {
				colorIndex = (short) (PossibleColors.length-1);
			}
		}
	}
		
}
