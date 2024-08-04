package main;

import java.awt.event.KeyEvent;

import main.window.Canvas;
import main.window.Window;

public class PongMainC {

	public static Racket p1,p2;
	public static Ball ball;
	static Canvas canvas;
	static short winner = 0;
	
	public static void main(String[] args) {
		// Create Rackets and ball
		p1 = new Racket(50, 375,
				KeyEvent.VK_W, KeyEvent.VK_S,
				KeyEvent.VK_A, KeyEvent.VK_D);
		
		p2 = new Racket(890, 375,
				KeyEvent.VK_UP, KeyEvent.VK_DOWN,
				KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
		
		Racket[] rackets = new Racket[] {p1,p2};
		ball = new Ball((short) 475,(short) 475, rackets);
		
		// Create Window
		canvas = new Canvas(rackets, ball);
		new Window(canvas);
		
		// Start Game countdown
		try {
			canvas.repaint();
			Thread.sleep(1000);
			canvas.countdown(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Start Game
		startGame();
		
		// Game ended, we have winner
		if(winner == 1) {			
			p1.y = 500 - p1.height/2;
			canvas.ShowWinner(winner, p1);
		}else if (winner == 2){
			// I know, useless check, just to be sure winner is within bounds
			p2.y = 500 - p2.height/2;
			canvas.ShowWinner(winner, p2);
		}
		
		// The end :P
		System.exit(0);
	}
	
	private static void startGame() {
		canvas.repaint();
		long timeAtStart, expectedEndTime;
		while (true) {
			
			// check for winner
			short pointDiff = (short) (Ball.p1points - Ball.p2points);
			if(pointDiff >= 2) {
				// p1 winning by 2 or more
				if(Ball.p1points >= 11) {
					winner = 1;
					break;
				}
				
			}else if(pointDiff <= -2) {
				// p2 winning by 2 or more
				if(Ball.p2points >= 11) {
					winner = 2;
					break;
				}
			}
			
			// fixed FPS, around 60 (bit less)
			timeAtStart = System.currentTimeMillis();
			expectedEndTime = timeAtStart + 17;
			
			// update Rackets
			p1.update();
			p2.update();
			
			// update ball
			ball.update();
			
			// draw everything
			canvas.repaint();
			
			// fixed FPS, wait time
			while(System.currentTimeMillis() < expectedEndTime) {
				// wait ¯\_(ツ)_/¯
			}
		}
		
		return;
	}
}

