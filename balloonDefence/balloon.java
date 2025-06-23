package balloonDefence;

import java.awt.*;
import javax.swing.*;

/**
 * Balloon's class with coordinates, images, HP and advancements
 * Tharjiha Suthekara
 * January 8th, 2024
 */

public class balloon {
	Toolkit kit = Toolkit.getDefaultToolkit(); //get's image tool kit
	Image balloonI; //image of balloon
	Image bI; //image of the actual balloon
	Image splatI; //splatter image of balloon
	//coordinates of balloon
	int x;
	int y;
	int hp = startHP; //setting hp of balloon to starting HP
	static int startHP = 50; //starting hp for all balloons
	//width and height of balloon
	final static int width = 76;
	final static int height = 124;
	
	//CONSTRUCTOR
	balloon (int x, int y, String image){
		//assigning x and y coordinates
		this.x = x;
		this.y = y;
		bI = menuPanel.getImage(("balloonImages/" + image + ".png"), width, height); //setting image of balloon
		splatI = menuPanel.getImage(("balloonImages/" + image + "Splat.png"), width, height); //setting image of balloon splat
		balloonI = bI; //making current balloon image of the balloon
	}

}
