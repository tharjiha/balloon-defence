package balloonDefence;

import java.awt.Image;

/**
 * An enum type for which robot is being chosen
 * Tharjiha Suthekara
 * January 2nd, 2024
 */

public enum robotType {
	happy, buddy, brainy, lovely, mega; //types of robot


	/**
	 * Converts the enum name to a string form to call image
	 * post: a string of what type of robot it is
	 */
	public String getRobotText() {
		switch (this) {
		case happy: return "Robots/Happy";
		case buddy: return "Robots/Buddy";
		case brainy: return "Robots/Brainy";
		case lovely: return "Robots/Lovely";
		case mega: return "Robots/Mega";
		}
		return "";
	}
	
	/**
	 * Gets the velocity of the selected robot
	 * post: an int array of velocity
	 */
	public int [] getVelocity () {
		int start = 0; //a default starting speed
		//adds all starting speed based on type
		if(this == robotType.happy) {
			start = 1;
		}
		else if(this == robotType.buddy) {
			start = 3;
		}
		else if(this == robotType.brainy) {
			start = 4;
		}
		else if(this == robotType.lovely) {
			start = 2;
		}
		else {
			start = 5;
		}
		//creates an array based on starting speed
		int [] v = new int [4];
		for(int i = 0; i < v.length; i++) {
			v[i] = start + i;
		}
		return v; //return array
	}
	
	/**
	 * Gets the cost of the selected robot
	 * post: an int value of cost
	 */
	public int getCost () {
		switch (this) { //gets cost of type
		case happy: return 5;
		case lovely: return 10;
		case buddy: return 15;
		case brainy: return 20;
		case mega: return 50;
		}
		return 0;
	}
	
	/**
	 * Get's a default image of robot
	 * post: an image of chosen robot
	 */
	public Image getImage () {
		return menuPanel.getImage((this.getRobotText() + "BotD.png"), 65, 75); //gets image based on type
	}
	
	/**
	 * Gets the force of the selected robot
	 * post: an int array of force
	 */
	public int [] getForce () {
		int start = 0;//a default starting force
		//adds all starting force based on type
		if(this == robotType.happy) {
			start = 1;
		}
		else if(this == robotType.buddy) {
			start = 1;
		}
		else if(this == robotType.brainy) {
			start = 2;
		}
		else if(this == robotType.lovely) {
			start = 1;
		}
		else {
			start = 2;
		}
		//creates an array based on starting force
		int [] v = new int [4];
		for(int i = 0; i < v.length; i++) {
			v[i] = start + i;
		}
		return v;
	} 
}
