package balloonDefence;

import java.awt.*;
import javax.swing.*;

/**
 * Pin's class with coordinates and advancements
 * Tharjiha Suthekara
 * January 2nd, 2024
 */

public class pin {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Image pinI; //image of pin
	Image left;
	Image right;
	Image up;
	boolean move = false; //if it's moving or not
	int iM = 0; //index of it's movement
	int x; //x coordinate
	int y; //y coordinates
	int velocity = 5; //moves the pin
	public int force = 20; //how much force the pin applies on the balloon
	int strength; //amount of lives pin has
	//width and height of pins
	public int width = 21;
	public int height = 37;
	String image;
	pinType type;
	
	//CONSTRUCTOR
	//pre: two int of coordinates and pinType of which pin
	pin (int x, int y, pinType t){
		type = t; //assigns pin type
		image = type.getPinText(); //gets the string of the pin type
		//assigns coordinate
		this.x = x;
		this.y = y;
		
		//assigns width and height based on type
		width = t.getWidth();
		height = t.getHeight();
		
		//assigns all images of pin
		pinI = menuPanel.getImage((image + ".png"), width, height);
		left = menuPanel.getImage((image + "L.png"), width, height);
		right = menuPanel.getImage((image + "R.png"), width, height);
		up = menuPanel.getImage((image + "U.png"), width, height);
		
		velocity = t.getVelocity(); //gets pin velocity
		force = t.getForce(); //gets pin force
		strength = t.getStrength(); //gets pin strength
	}
	
	/*
	 * moves the pin up
	 */
	void up () {
		y -= velocity;
	}
	
	/*
	 * moves the pin down
	 */
	void down () {
		y += velocity;
	}
	
	/*
	 * moves the pin to the left
	 */
	void left () {
		x -= velocity;
	}
	
	/*
	 * moves the pin to the right
	 */
	void right () {
		x += velocity;
	}
	
	/**
	 * Changes image of pin depending on strength
	 * pre: an int variable of what strength its at
	 */
	void changeImage (int str) {
		if(strength <= type.getStrength()) {
			String s = Integer.toString(str);
			pinI = menuPanel.getImage((image + "(" + s + ").png"), width, height);
			left = menuPanel.getImage((image + "(" + s + ")L.png"), width, height);
			right = menuPanel.getImage((image + "(" + s + ")R.png"), width, height);
			up = menuPanel.getImage((image + "(" + s + ")U.png"), width, height);
		}
	}
	
	/**
	 * Adds to force of pin
	 */
	void addForce(int add) {
		force = force + add;
	}
}
