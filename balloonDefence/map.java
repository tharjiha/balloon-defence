package balloonDefence;

import java.awt.*;

import balloonDefence.robot.robotDirec;

/**
 * Enum class for which map is chosen
 * Tharjiha Suthekara
 * January 2nd 2024
 */

public enum map {
	map1, map2; //types of maps
	
	/**
	 * Gets the according image for board based on map chosen
	 * post: an image of which board
	 */
	public Image boardI () {
		Toolkit kit = Toolkit.getDefaultToolkit();
		if(this == map.map1) {
			return kit.getImage("board1.png"); //background image of map1 board 
		}
		else {
			return kit.getImage("board2.png"); //background image of map2 board 
		}
	}
	
	/**
	 * Gets corners for pins according to map
	 * post: get a 2D array of coordinates
	 */
	public int [][] getCorners(){
		if(this == map.map1) { //returns corners of pins for map1
			int [][] cornersC = {{10,535},{135,535},{135,385},{280,385},{280,535},{440,535},{440,240},{50,240},{50,85},{590,85},{590,535},{740,535},{740,120}};
			return cornersC;
		}
		else { //returns corners of pins for map1
			int [][] cornersC = {{10,535},{135,535},{135,85},{285,85},{285,535},{430,535},{430,85},{590,85},{590,535},{740,535},{740,120}};
			return cornersC;
		}
	}
	
	/**
	 * Gets corners directions for pins according to map
	 * post: a string array of pin directions
	 */
	public String [] getDirection (){
		if(this == map.map1) { //returns movements for map1
			String [] movement = {"right","up","right","down","right","up","left","up","right","down","right","up","stop"};
			return movement;
		}
		else { //returns movements for map2
			String [] movement = {"right","up","right","down","right","up","right","down","right","up","stop"};
			return movement;
		}
	}
	
	/**
	 * Gets the bots corners for according map
	 * post: a 2D array of bot corners
	 */
	public int [][] getBotCorners(){
		if(this == map.map1) { //returns corners of bots for map1
			int [][] cornersC = {{190,450},{90,300},{103,150},{35,440},{345,300},{345,440},{500,150},{500,525},{640,450},{640,200}};
			return cornersC;
		}
		else { //returns corners of bots for map1
			int [][] cornersC = {{187,150},{187,420},{340,260},{340,420},{490,150},{490,360},{645,420}};
			return cornersC;
		}
	}
	
	/**
	 * Gets an array of robot direction depending on map
	 * post: a array of robotDirec
	 */
	public robotDirec [] getBotDirection (){
		if(this == map.map1) { //returns directions for map1
			robotDirec [] movement = {robotDirec.left,robotDirec.up,robotDirec.up,robotDirec.down,robotDirec.up,robotDirec.down,robotDirec.up,robotDirec.right,robotDirec.right,robotDirec.right};
			return movement;
		}
		else { //returns directions for map2
			robotDirec [] movement = {robotDirec.up,robotDirec.left,robotDirec.left,robotDirec.down,robotDirec.up,robotDirec.right,robotDirec.down};
			return movement;
		}
	}
	
	
}
