package balloonDefence;

/**
 * Enum type of which pin is chosen
 * Tharjiha Suthekara
 * January 2nd, 2024
 */

public enum pinType {
	grey, purple, gold, mega; //type of pins
	
	/**
	 * Gets the string version of pin
	 * post: string version of pin text to get it's image
	 */
	public String getPinText() {
		switch (this) { //returns according pin image name depending on type
		case grey: return "Pins/pin1";
		case purple: return "Pins/pin2";
		case gold: return "Pins/pin3";
		case mega: return "Pins/pin4";
		}
		return "";
	}
	
	/**
	 * Gets the velocity of the selected pin
	 * post: an int value of velocity
	 */
	public int getVelocity () {
		switch(this) { //returns according velocity
		case grey: return 5;
		case purple: return 7;
		case gold: return 10;
		case mega: return (int)(Math.random()*(5-3)+3);
		}
		return 0;
	}
	
	/**
	 * Gets the force of the selected pin
	 * post: an int value of force
	 */
	public int getForce () {
		switch (this) { //returns according force
		case grey: return 5;
		case purple: return 10;
		case gold: return 20;
		case mega: return 50;
		}
		return 0;
	}
	
	/**
	 * Gets the strength of the selected pin
	 * post: an int value of strength
	 */
	public int getStrength () {
		switch(this) { //returns according strength
		case grey: return 1;
		case purple: return 2;
		case gold: return 3;
		case mega: return (int)(Math.random()*(200-75+1)+75);
		}
		return 0;
	}
	
	/**
	 * Gets the width of the selected pin
	 * post: an int value of width
	 */
	public int getWidth() {
		if(this == pinType.mega) { //if mega the pin is bigger the rest is normal pin
			return 30;
		}
		else {
			return 21;
		}
	}
	
	/**
	 * Gets the height of the selected pin
	 * post: an int value of height
	 */
	public int getHeight() {
		if(this == pinType.mega) { //if mega the pin is bigger the rest is normal pin
			return 46;
		}
		else {
			return 37;
		}
	}
}
