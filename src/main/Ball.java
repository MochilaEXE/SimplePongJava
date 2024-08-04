package main;

import java.awt.Graphics;
import java.util.Random;

public class Ball {

	float x, y, acc;
	double yvel, xvel;
	final int diameter = 50;
	Racket[] rackets;
	public static short p1points, p2points = 0;
	
	public Ball(short x, short y, Racket[] rackets) {
		this.x = x;
		this.y = y;
		yvel = (new Random().nextDouble(0.2, 0.8)*8)-4;
		xvel = 6;
		acc = 0.001F;
		this.rackets = rackets;
	}
	
	public void draw(Graphics g) {
		g.fillOval((int) x, (int) y, diameter, diameter);
	}
	
	public void update() {
		if(yvel > 0) yvel += acc;
		else yvel -= acc;

		if(xvel > 0) xvel += acc;
		else xvel -= acc;
		
		x += xvel;
		y += yvel;
		
		if(y+diameter > 1000) {
			yvel *= -1;
			y = 1000-diameter;
		}else if(y < 0){
			yvel *= -1;
			y = 0;
		}
		
		// points
		if(x < 0) {
			p2points++;
			x = 500-diameter/2;
			y = 500-diameter/2;
			yvel = (new Random().nextDouble(0.2, 0.8)*8)-4;
			xvel = 6;
			acc *= 1.12;
		}else if (x+diameter > 1000) {
			p1points++;
			x = 500-diameter/2;
			y = 500-diameter/2;
			yvel = (new Random().nextDouble(0.2, 0.8)*8) -4;
			xvel = -6;
			acc *= 1.12;
		}
		
		resolveCollisions();
	}
	
	private void resolveCollisions() {
		Racket racket1 = rackets[0];
		Racket racket2 = rackets[1];
		
		if(racket1.x+racket1.width > x && racket1.x < x+diameter &&
				racket1.y < y+diameter && racket1.y+racket1.height > y) {
			if(xvel < 0) {
				xvel *= -1;
			}
		}
		
		if(racket2.x+racket2.width > x && racket2.x < x+diameter &&
				racket2.y < y+diameter && racket2.y+racket2.height > y) {
			if(xvel > 0) {
				xvel *= -1;
			}
		}
	}
}
