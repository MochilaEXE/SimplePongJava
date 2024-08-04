package main.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import main.Ball;
import main.Racket;

public class Canvas extends JPanel{
	private static final long serialVersionUID = 1L;
	// normal var's
	Racket[] rackets;
	Ball ball;
	String points = "0-0";
	double countdown;
	Font gameFont = new Font("Consolas", Font.BOLD, 130);
	
	// after win var's
	long WinTimePassed = -1;
	short winner,winnerWidth;
	short winnerX, winnerY;
	Color winnerTxtColor = Color.black;
	
	// crown specific var's
	final short floatMaxAltitude = 50;
	short distanceBetweenCrownPoints;
	int[] crownXPoints;

	public Canvas(Racket[] rackets, Ball ball) {
		setBackground(Color.black);
		addKeyListener(rackets[0]);
		addKeyListener(rackets[1]);
		setFocusable(true);
		requestFocus();
		setPreferredSize(new Dimension(1000,1000));
		this.rackets = rackets;
		this.ball = ball;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(gameFont);
		
		g.setColor(Color.gray);
		// paint middle line
		g.fillRect(490, 0, 20, 50);
		// paint middle line from point counter
		for(int i = 200; i <= 1000; i++) {
			// every 100 pixels
			if(i % 100 == 0) {
				// fill rectangle with 50 height
				g.fillRect(490, i, 20, 50);
			}
		}
		
		// paint UI
		points = Ball.p1points + "-" + Ball.p2points;
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int stringWidth = metrics.stringWidth(points);
		int stringHeight = metrics.getHeight();
		
		if(Ball.p1points==Ball.p2points) {			
			g.setColor(Color.LIGHT_GRAY);
			
		}else if(Ball.p1points>Ball.p2points) {
			g.setColor(rackets[0].color);
			
		}else {
			g.setColor(rackets[1].color);
		}
		g.drawString(points, 500-stringWidth/2 , 10+stringHeight);
		
		g.setColor(Color.white);
		// paint objects
		for(Racket r : rackets) {
			r.draw(g);
		}
		ball.draw(g);
		
		// -------------- Special animations -------------- \\
		
		// initial countdown
		if(countdown >= 0) {
			// paint countdown
			switch ((int) countdown) {
			case 2:
				g.setColor(Color.green);
				break;
				
			case 1:
				g.setColor(Color.yellow);
				break;
				
			case 0:
				g.setColor(Color.red);
				break;
				
			}
			String txt = String.valueOf((int) countdown+1);
			int size = (int) (1000*(double)(countdown- (int)countdown));
			g.setFont(new Font("Consolas", Font.BOLD, size));
	        int countdownWidth = g.getFontMetrics(g.getFont()).stringWidth(txt);
	        int countdownHeight = g.getFontMetrics(g.getFont()).getHeight();
	        
			g.drawString(txt , 500-countdownWidth/2 , 500+countdownHeight/4);
		}
		
		// Win animation
		if(WinTimePassed > -1) {
			// blink text during 400 milliseconds every 1200 millisec's
			if((int)(WinTimePassed/400) % 4 != 0) {
				String txt = "Player " + winner + " WON!";
				int winTxtWidth = g.getFontMetrics(g.getFont()).stringWidth(txt);
				int winTxtHeight = g.getFontMetrics(g.getFont()).getHeight();
				
				g.setColor(Color.gray);
				g.fillRect(500 - winTxtWidth/2, 500-winTxtHeight/2, winTxtWidth, winTxtHeight);
				g.setColor(winnerTxtColor);
				g.drawString(txt , 500-winTxtWidth/2 , 500+winTxtHeight/4);
			}
			
			// draw crown (or at least try to)
			short floatAltitude = (short) (WinTimePassed%1000 / (1000/floatMaxAltitude));
			if((short)(WinTimePassed/1000) % 2 == 0) {
				// float down
				floatAltitude = (short) (floatMaxAltitude - floatAltitude);
			}
			
			short crownTallest = (short) (winnerY - 80 - floatAltitude);
			short crownNormal = (short) (winnerY - 70 - floatAltitude);
			short crownDent = (short) (winnerY - 40 - floatAltitude);
			short crownBottomLine = (short) (winnerY - 10 - floatAltitude);
			
			int[] crownYPoints = new int[] {crownNormal, // top most left point
					crownDent, // top 2nd point
					crownTallest, // top 3rd point (middle)
					crownDent, // top 4th point
					crownNormal, // top most right point
					crownBottomLine, // crown right bottom
					crownBottomLine}; // corwn left bottom
			
			g.setColor(Color.YELLOW);
			g.fillPolygon(crownXPoints, crownYPoints, 7);
		}
	}
	
	public void countdown(int durationSeconds) {
		long startTime = System.currentTimeMillis();
		countdown = durationSeconds;
		while(System.currentTimeMillis() < startTime + durationSeconds*1000) {
			repaint();
			countdown = (double) (durationSeconds*1000-(System.currentTimeMillis()-startTime))/1000;
		}
		countdown = -1;
	}

	public void ShowWinner(short winner, Racket winnerPlayer) {
		// get winner info
		this.winner = winner;
		winnerWidth = winnerPlayer.width;
		winnerX = (short) winnerPlayer.x;
		winnerY = (short) winnerPlayer.y;
		
		// set winner crown x points
		distanceBetweenCrownPoints = (short) (winnerWidth / 4);
		crownXPoints = new int[] {winnerX, // top most left point
				winnerX+distanceBetweenCrownPoints, // top 2nd point
				winnerX+2*distanceBetweenCrownPoints, // top 3rd point (middle)
				winnerX+3*distanceBetweenCrownPoints, // top 4th point
				winnerX+winnerWidth, // top most right point
				winnerX+winnerWidth, // crown right bottom
				winnerX}; // crown left bottom
		
		repaint();
		// start win animation
		long startTime = System.currentTimeMillis();
		WinTimePassed = 0;
		while(WinTimePassed < 8000) {
			repaint();
			WinTimePassed = System.currentTimeMillis() - startTime;
		}
	}
	
}
