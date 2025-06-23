package balloonDefence;

import java.awt.*;
import javax.swing.*;

/**
 * Robots class that holds all info like it's coordinates and its laser
 * Tharjiha Suthekara
 * December 23rd, 2023
 */

public class robot {
	
	public enum robotDirec { //enum for which direction robot shoots and faces
		up, down, left, right
	}
	
	robotType robotT; //robots type
	robotDirec direc; //robots direction
	Image laser, robot; //images of robot and laser
	boolean moveL = false; //boolean to see if laser starts shooting
	int x, y; //coordinates of the robot
	int xL, yL; //coordinates of the laser
	int wL, hL; //width and height of laser
	int startXL, startYL; //orginal coordinates of the laser
	int velocity = 1; //speed of laser
	int force;
	int currentVIndex = 0;
	int currentFIndex = 0;
	
	//width and height of robot
	final static int width = 59;
	final static int height = 68;
	
	//CONSTRUCTOR
	robot (robotType bot, robotDirec direc, int x, int y){
		robotT = bot; //type of robot
		this.direc = direc; //direction of robot
		//coordinates of robot
		this.x = x;
		this.y = y;
		velocity = bot.getVelocity()[currentVIndex];
		force = bot.getForce()[currentFIndex];
	
		//width and height of laser
		wL = 15;
		hL = 15;
		
		laser = menuPanel.getImage((bot.getRobotText() + "Laser" + getDirection() + ".png"), wL, hL); //gets laser image
		
		if(getDirectionLaser().equals("V")) { //if it's vertically orientated sets images width and height accordingly
			robot = menuPanel.getImage((bot.getRobotText() + "Bot" + getDirection() + ".png"), width, height); //get robot image
			
			//gets coordinate of lasers starting positions
			startXL = x + width/2 - 8;
			startYL = y;
			if(direc == robotDirec.down) {
				startYL += 10;
			}
			else {
				startYL += 10;
			}
		}
		else { //if it's horizontally orientated sets images width and height accordingly
			robot = menuPanel.getImage((bot.getRobotText() + "Bot" + getDirection() + ".png"), height, width);
			
			//gets coordinate of lasers starting positions
			startXL = x;
			startYL = y + height/2 - 10;
			if(direc == robotDirec.right) {
				startXL += 10;
			}
			else {
				startXL += 30;
			}
		}
		
		//sets the x and y coordinates of laser to starting position
		xL = startXL;
		yL = startYL;
	}
	
	
	/*
	 * Get's a string variable of the robots orientation for robots image
	 * post: a string of robots direction
	 */
	public String getDirection () {
		switch(direc) {
		case up: return "U";
		case down:return "D";
		case left: return "L";
		case right: return "R";
		}
		return "";
	}
	
	/**
	 * Checks to see if the robot is aligned horizontally or vertically for the laser image
	 * post: a string of it's vertical or horizontal
	 */
	public String getDirectionLaser () {
		if(direc == robotDirec.up || direc == robotDirec.down) {
			return "V";
		}
		else {
			return "H";
		}
	}
	
	/**
	 * Moves the laser based on the robots orientation
	 */
	public void move() {
		if(moveL) {
			if(direc == robotDirec.down) { //moves the laser down
				yL += velocity;
			}
			else if(direc == robotDirec.up) { //moves the laser up
				yL -= velocity;
			}
			else if(direc == robotDirec.left) { //moves the laser left
				xL -= velocity;
			}
			else { //moves the laser right
				xL += velocity;
			}
			
			//if(yL >= startYL + 100 || yL <= startYL - 100 || xL >= startXL + 1000 || xL <= startXL - 100) {
			if(yL < -10 || yL > 670 || xL < -10 || xL > 800) { //if it reaches end of screen it restarts laser
				xL = startXL;
				yL = startYL;
			}
		}
	}
	
	/**
	 * Checks to see if the laser has collided with the pin
	 * pre: pin object
	 * post: returns a boolean indicating if the pin has collided with the balloon
	 */
	public boolean collide (pin p) {
		boolean collide = false;
		if(p.y < yL && (p.y + p.height) > yL && p.x < (xL) && (p.x + width) > (xL)) { //checks to see if the top corner of laser interacts with the pin
			collide = true;
		}
		if(p.y < (yL + hL) && (p.y + p.height) > (yL + hL) && p.x < (xL+wL) && (p.x + width) > (xL+wL)) { //checks to see if the bottom corner of laser interacts with the pin
			collide = true;
		}
		if(collide) { //if collided restarts laser
			xL = startXL;
			yL = startYL;
		} 
		return collide && moveL;
	}
	
	/**
	 * Checks to see if robot is in range of the moving pins to start shooting
	 * pre: a pin object 'p'
	 * post: a boolean to see if pin is within the range of the robot
	 */
	public boolean pinsInRange(pin p) {
		if(direc == robotDirec.left || direc == robotDirec.right) { //if going horizontal
			if(yL - 100 <= p.y && yL + 100 >= p.y) { //checks to see if within y range
				//depending on direction checks to see if x in range
				if(direc == robotDirec.left) {
					return xL >= p.x;
				}
				if(direc == robotDirec.right) {
					return xL <= p.x;
				}
			}
		}
		if(direc == robotDirec.up || direc == robotDirec.down) { //if going vertical
			if(xL - 100 <= p.x && xL + 100 >= p.x) { //checks to see if within x range
				//depending on direction checks to see if y in range
				if(direc == robotDirec.up) {
					return yL >= p.y;
				}
				if(direc == robotDirec.down) {
					return yL <= p.y;
				}
			}
		}return false;
	}
	
	
	

}
