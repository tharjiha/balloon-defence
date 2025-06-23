package balloonDefence;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

import javax.swing.*;

import balloonDefence.robot.robotDirec;

/**
 * GamePanel class implements Runnable, actionListener in order to use a Thread it also sets to run the game
 * Tharjiha Suthekara
 * January 8th, 2023
 */

public class gamePanel extends JPanel implements Runnable, ActionListener, KeyListener{
	static Image board; //image variables for all the map
    
    Image [] bars = new Image [9]; //all bar images array
    Image currentBar; //image with current bar progress
    
    JLabel levelsL = new JLabel (); //a label with the current level
    JLabel percentL = new JLabel("100"); //percent of balloon's total HP left
    JButton start = new JButton("START"); //button to start game
    JButton stop = new JButton("STOP"); //button to stop game
    boolean startGame = false; //boolean to start game
    
    int speed = 15; //speed of the delay of the screen thread (higher the value more choppy because fewer repaints and vice versa)
    Thread runner; //a thread variable to run multiple things at once
    
    ArrayList <balloon> balloons = new ArrayList <>(); //an array list with all the balloon on screen
    ArrayList <pin> pins = new ArrayList <> (); //an array list with all pins on screen
    int numberOfPins; //number of pins in each round
    static int level = 0; //how many levels have passed 
    
    //an array of coordinates of corners in array
    static int [][] cornersC;
    //an array of movement on how the pin will move at each corner
    static String [] movement;
    boolean collide = false; //a boolean to see if the balloons collide with the pin and restarts or if it reaches end and restarts
   
    
    //an array of all bots 
    static ArrayList <robot> robots = new ArrayList <>();
    //directions of all pots in position
    static robotDirec [] botDirection = {};
    //coordinates of all bots position
    static int [][] botCoord = {};
    boolean destroy = false; //boolean to see if laser hit pin
    boolean moveAllL = false; //start moving all lasers
    

    static boolean [] addBotEnable = new boolean [botDirection.length]; //boolean arraylist with add Bot button status
    static JButton [] addBot = new JButton [botDirection.length]; //arraylist with buttons to add Bot
    
    Image coinI, starI; //image of star and coin
    //label with coins and points
    static JLabel coinL = new JLabel("1",SwingConstants.RIGHT);
    static JLabel pointsL = new JLabel ("1",SwingConstants.RIGHT);
    static int coins = 10, points = 0, highScore; //default values for coins, points and high score
    
    static int boughtIndex = 0; //index of the button that is pressed and bought
    
    Image robotSeatV, robotSeatH; //image of robot seat / button
    Image maxSeatV, maxSeatH; //image of robot seat if it's max
    
    boolean pause = false; //boolean to pause game
    
    String [] speedTime = {"x1","x1.25","x1.5","x1.75","x2"}; //array of string to see how to speed up
    int currentSpeedI = 0; //index of current speed
    int [] speedAcc = {15,12,10,8,5}; //actually thread runner speed to change the thread runner
    JButton speedAdjust = new JButton(); //a button to adjust speed
    
    //icons for the arrows for the buy bot button
    static ImageIcon left = new ImageIcon(menuPanel.getImage("random/arrowL.png", 35, 35));
    static ImageIcon up = new ImageIcon(menuPanel.getImage("random/arrowU.png", 35, 35));
    static ImageIcon down = new ImageIcon(menuPanel.getImage("random/arrowD.png", 35, 35));
    static ImageIcon right = new ImageIcon(menuPanel.getImage("random/arrowR.png", 35, 35));
    static ImageIcon empty = new ImageIcon(menuPanel.getImage(".png", 35, 35));
    
    popUpMain popUp; //pop up menu
    
    /*
     * Main constructor for the gamePanel
     * pre: a map enum of which map is chosen
     */
    public gamePanel(map m){
       super();
       setMap(m); //a method to change variables according to which map is chosen
       addKeyListener(this); //adding key listener to use space bar
       
       //gets tool kit for images 
       Toolkit kit = Toolkit.getDefaultToolkit();
       
       setLayout(null); //set layout to null
       
       
       start.setBounds(10,7,75,30); //set start buttons position
       stop.setBounds(10,37,75,30); //set stop buttons position
       levelsL.setFont(new Font("SansSerif", Font.BOLD, 27)); //adjusting the font of levels text
       levelsL.setBounds(10,590,200,30); //set levels label position
       percentL.setFont(new Font("SansSerif", Font.BOLD, 20)); //adjusting the font of percents text
       percentL.setBounds(555,15,200,30); //set percent label position
       
       coinL.setFont(new Font("SansSerif", Font.BOLD, 20)); //adjusting the font of coins text
       coinL.setText(Integer.toString(coins)); //adding int value of coins to label
       coinL.setBounds(400,15,67,30); //set coin label position
       pointsL.setFont(new Font("SansSerif", Font.BOLD, 20)); //adjusting the font of points text
       pointsL.setText(Integer.toString(points)); //adding int value of points to label
       pointsL.setBounds(300,15,67,30); //set coin label position
       //add buttons to actionlistenr
       start.addActionListener(this); 
       stop.addActionListener(this);
       //add all elements to screen 
       add(coinL);
       add(pointsL);
       add(levelsL);
       add(percentL);
       add(start); 
       add(stop);
       
       for(int i = 0; i < botCoord.length; i++) { //adding purchase buttons
    	   addBot[i] = new JButton(); //create button
    	   
    	   //add direction text to the button and set the coordinates
    	   if(botDirection[i] == robotDirec.left || botDirection[i] == robotDirec.right) {
    		   addBot[i].setBounds(botCoord[i][0],botCoord[i][1],59,68);
    	   }
    	   else {
    		   addBot[i].setBounds(botCoord[i][0],botCoord[i][1],59,68);
    	   }
    	   
    	   addBotSetDirection(addBot[i],botDirection[i]); //shows direction of button by adding direction icon to button
    	   
    	   addBot[i].setFont(new Font("SansSerif", Font.BOLD, 20)); //adjusting the font of add bot button text
    	   menuPanel.editButton(addBot[i],Color.BLACK,Color.BLACK); //change the colour and make it transparent
    	   addBot[i].addActionListener(this); //add to action listener
    	   add(addBot[i]); //add to screen
    	   addBotEnable[i] = true; //enable the robot in array
       }
       
     
       
       //gets images from folder using getImage in kit
       coinI = menuPanel.getImage("random/coin.png", 30, 30); //coin image
       starI = menuPanel.getImage("random/star.png", 30, 30); //star image
       //robotSeat images
       robotSeatV = menuPanel.getImage("random/verticalRobotSeat.png", 59, 68);
       robotSeatH = menuPanel.getImage("random/horizontalRobotSeat.png", 68, 59);
       maxSeatV = menuPanel.getImage("random/maxVerticalRobotSeat.png", 59, 68);
       maxSeatH = menuPanel.getImage("random/maxHorizontalRobotSeat.png", 68, 59);
       
       for(int i = 0; i < bars.length; i++) { //adds all progress bars into an array
    	   String num = Double.toString(100-(12.5*i));  //get's num for image title
    	   String title = "bars/bar" + num + ".png"; //gets name of image
	   	   bars[i] = kit.getImage(title); //adds to array
	   	   bars[i] = bars[i].getScaledInstance(188, 20, java.awt.Image.SCALE_SMOOTH ); //scales image correctly
       }
       currentBar = bars[0]; //current bar is full
       
       
       //adding all images of balloons
       balloons.add(new balloon(725,60,"greenBalloon"));
       balloons.add(new balloon(675,60,"blueBalloon"));
       balloons.add(new balloon(700,70,"orangeBalloon"));
       balloons.add(new balloon(725,90,"pinkBalloon"));
       balloons.add(new balloon(675,90,"purpleBalloon"));
      
       speedAdjust.setBounds(720,595,75,30); //adjusting speed button position
       speedAdjust.setText(speedTime[currentSpeedI]); //changes text to next speed
       speedAdjust.addActionListener(this); //adds to action listener
       add(speedAdjust); //add to screen
       
       //starts a new round
       try {
		newRound();
       } catch (IOException e) {
		e.printStackTrace(); //try and catch incase error from IO Exceptions
       } 
       
       creatingPins(); //creating a pins array depending on level
       
       levelsL.setText("LEVEL " + level); //setting levels label
       
 
       runner = new Thread(this); //creates a thread variable that controls all elements
       runner.start(); //starts the runner thread variable
   }
    
   /**
    * Paint components method to create graphics on screen
    */
   public void paintComponent(Graphics comp) {
       Graphics2D comp2D = (Graphics2D) comp; //creates graphic component to add images and backgrounds
       comp2D.drawImage(board, 0, 0, this); //adds background
       for(pin p: pins) {
    	   comp2D.drawImage(p.pinI,p.x,p.y,this); //adds pins    
       }
       comp2D.drawImage(currentBar,600,20,this); //add current bar
       
       for(balloon b: balloons) { //adds all the balloons
    	   comp2D.drawImage(b.balloonI, b.x, b.y, this);
       }
       
       for(int i = 0; i < botCoord.length; i++) { //add the robots seat image under the button
    	   boolean max = false; //a boolean to have max powers
    	   for(robot r: robots) { //checks to see which robots have max powers
    		   if(r.x == botCoord[i][0] && r.y == botCoord[i][1]) {
    			   if(r.currentFIndex == 3 && r.currentVIndex == 3) { //checks to see if robots at max
    				  max = true;
    				  break; 
    			   }
    		   }
    	   }
    	   if(botDirection[i] == robotDirec.down || botDirection[i] == robotDirec.up) { //if the direction is vertical
    		   //adds seat according to if robot has max power or not
    		   if(max) {
    			   comp2D.drawImage(maxSeatV, botCoord[i][0], botCoord[i][1],this);
    		   }
    		   else {
    			   comp2D.drawImage(robotSeatV, botCoord[i][0], botCoord[i][1],this);
    		   }
    	   }
    	   else {  //if the direction is horizontal
    		 //adds seat according to if robot has max power or not
    		   if(max) {
    			   comp2D.drawImage(maxSeatV, botCoord[i][0], botCoord[i][1],this);
    		   }
    		   else {
    			   comp2D.drawImage(robotSeatV, botCoord[i][0], botCoord[i][1],this);
    		   }
    	   }
    	   
       }
       
       for(robot robot1: robots) { //adds robots and lasers to screen
    	   comp2D.drawImage(robot1.laser,robot1.xL,robot1.yL,this);
    	   comp2D.drawImage(robot1.robot,robot1.x,robot1.y,this);
       }
       
       comp2D.drawImage(coinI,475,15,this); //adding coin image
       comp2D.drawImage(starI,375,15,this); //adding star image
       
   }

   /**
    * A method that runs with the thread to move multiple images at once 
    */
   public void run() {
       Thread thisThread = Thread.currentThread(); //finds the current thread running
      
       while (runner == thisThread) { //while loop while current thread is runner (to check if program is running)
    	   
    	   destroy = false; //a boolean to see if the pin and laser collision requires the pin to be destroyed
    	   
    	   if(moveAllL) { //moves lasers if boolean to move all lasers is true
	    	   for(robot r: robots) {//moves robot
	    		   r.moveL = false; //make the robot movement false as default
	    		   for(pin p: pins) {
	    			   if(r.pinsInRange(p)) { //if pins within the robots range make them start shooting
	    				   r.moveL = true;
	    				   break;
	    			   }
	    		   }
	    		   r.move(); //moves laser if moveL is true
	    		   
	    		   if(r.moveL == false) { //restarts laser if the pins are out of range
	    			   r.xL = r.startXL;
	    			   r.yL = r.startYL;
	    		   }
	    		   
	    		   if(pause) { //if pause breaks out of this loop
		    		   break;
		    	   }
	    	   }
	    	   
    	   }
    	   
    	   for(pin p: pins) { //for all pins
    		   collide = false; //makes collision with balloon false by default
    		   
    		   
	    	   //change direction if it's at a corner
		       if(passedPoint(p)) {
		    		p.iM++;
		       }
		       if(p.iM + 1 >= movement.length) {pins.remove(p); break;} //removes pin if it's reaches balloon
		       
		       if(p.move) {
		    	   move(movement[p.iM],p); //moves pin
		       }
		       
		       for(robot r: robots) {
		    	   if(r.collide(p)) { //if laser collides with pins
		    		   p.strength = p.strength - r.force; //changes the pins HP based on lasers force
		    		   if(p.type != pinType.mega) { //if not mega pin changes pin image to show how much its destroyed
		    			   p.changeImage(p.strength);
		    		   }
		    		   if(p.strength <= 0) { //if strength is zero
			    		   coins++; //add a coin
			    		   
			    		   //add points depending on which pin is shot
			    		   if(p.type == pinType.gold) {
			    			   points += 4;
			    		   }
			    		   if(p.type == pinType.purple) {
			    			   points += 2;
			    		   }
			    		   if(p.type == pinType.grey) {
			    			   points += 1;
			    		   }
			    		   
			    		   updateLabels(); //update labels
			    		   losingPanel.scoreL.setText(Integer.toString(gamePanel.points)); //update final score label
			    		   
			    		   if(points >= highScore) { //change high score if points increase
			    			   highScore = points;
			    		   }
			    		   losingPanel.highScore.setText(Integer.toString(gamePanel.highScore)); //update final high score label
			    		   
			    		   //resets the laser coordinates
			    		   r.xL = r.startXL;
			    		   r.yL = r.startYL;
			    		   p.move = false; //stops the pins movement
			    		   destroy = true; //makes destroy pin boolean true
			    		   break;
		    		   }
		    	   }
		       }
		       if(destroy) { //if destroy boolean is true removes pin and breaks out of loop
		    	   pins.remove(p); //removes pin
		    	   break; //breaks out of pin loop
		       }
		       
		       for(balloon b: balloons) { //for all balloons
		    	   if(p.y < (b.y + balloon.height - 30) && (p.x > 700 && p.x < 800) && b.balloonI.equals(b.bI)) { //checks to see if the balloon image is within the pin heigh wise
			    	   b.hp -= p.force; //gets rid of hp for balloon based on attacked pin's force
			    	   
			    	   
		    		   if(b.hp <= 0) {
			    		   b.balloonI = b.splatI; //make the image into a splatter
			    	   }
		    		   
		    		   collide = true; //says the pin has to be removed since it has collide with balloon
		    		   
		    		   if(percentHP() >= 0) {  //checks to see if percent is in negatives
		    			   replaceBar(); //update bar
		    		   }
			    	   
			    	   break; //break loop
			       }
		       }
		      
		      if(percentHP() <= 0) { //checks to see if all balloons are destroyed and then starts a new round
		    	  try {
					newRound(); //new round when game is over
				} catch (IOException e) {
					e.printStackTrace();
				} //try and catch for IOException error
		    	  
		    	  creatingPins(); //new sets of pins
	    		  break; //breaks loop
	    	  }
		      
		      if(p == pins.get(pins.size() - 1) && ((p.x == 740 && p.y == 120) || collide)) { //checks to see if all pins have gone to end in current level to start a new level
	    		   nextLevel(); //starts next level
	    		   break; //breaks loop
	    	  }
		      
		      if(collide) { //removes pins after it collides
		    	  pins.remove(p); 
		    	  break;
		      }
		      
		  
		      if(pause) { //if paused pauses screen
		    	  break; //breaks loop
		      }
    	   }
    	   
    	   if(pins.size() == 0) { //next level if all pins have been shot
    		   nextLevel(); //starts new level
    	   }
	       
    	  
	       repaint(); //repaints the screen to make elements appear to move
	       requestFocusInWindow(); //makes sure the screen restarts to make the images appear like it's moving when key is pressed
	       
	       try { Thread.sleep(speed); } //slows down speed of images so it looks like its moving
	       catch (InterruptedException e) {} //check to see if there's an error
	       
       }
   }
   
   /**
    * Moving based on which direction is given
    * pre: a string of the direction and a pin variable p
    */
   public void move (String direction, pin p) {
	   if(direction.equals("up")) { //changing image and direction if it's up
		   p.up(); //moves pin
		   p.pinI = p.up; //changes image according to direction
	   }
	   else if(direction.equals("down")) {  //changing image and direction if it's down
		  p.down(); //moves pin
	   }
	   else if(direction.equals("right")) {  //changing image and direction if it's right
		  p.pinI = p.right;  //changes image according to direction
		  p.right(); //moves pin 
	   }
	   else if(direction.equals("left")) {  //changing image and direction if it's left
		  p.pinI = p.left;  //changes image according to direction
		  p.left(); //moves pin
	   }
   }

   	/**
   	 * Runs when a button is pressed
   	 * pre: an action event called e
   	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == start) { //starts movement with start button
			start();
		}
		if(e.getSource() == stop) { //stops movement with stop button
			pause = true; //change pause boolean to true
			pause(); //pauses screen
			
		}
		for(int i = 0; i < addBot.length; i++) {
			if(e.getSource() == addBot[i]) { //checks to see if a addBot button is pressed
				if(popUp != null) { 
					popUpMain.frameP.dispose(); //get rid of screen
				}
				
				if(addBotEnable[i]) {
					boughtIndex = i; //makes the bought index into selected buttons index
					popUp = new popUpMain(); //calls popup window
					popUpMain.navigation.show(popUpMain.centerPanelP, "purchase"); 	 //makes the purchase panel the front panel in pop up window
				}
				else{
					for(robot r: robots) { //if the robot is already bought
						if(botCoord[i][0] == r.x && botCoord[i][1] == r.y) {
							boughtIndex = i; //assign the index of which button is clicked to updatePanel
							updatePanel.notBought = getNotBought(r.robotT); //assign the type of robot to the updatePanel
							updatePanel.remove = r; //assign robot to be updated to updatePanel
							popUp = new popUpMain(); //calls popup window
							break;
						}
					}
					popUpMain.navigation.show(popUpMain.centerPanelP, "update");  //shows the update panel
					
					
				}
			}
		}
		
		if(e.getSource() == speedAdjust) { //if speed adjust button is clicked
			currentSpeedI++; //increases the index of speed
			if(currentSpeedI == speedTime.length) { //resets if it's greater than length of array
				currentSpeedI = 0;
			}
			
			speedAdjust.setText(speedTime[currentSpeedI]); //changes the text of button to next speed
			speed = speedAcc[currentSpeedI]; //changes the speed
			
		}
		
	}
	
	/*
	 * Pauses the game
	 */
	public void pause () {
		for(pin p: pins) { //pauses pin movements
			p.move = false;
		}
		start.setText("CONTINUE"); //changes start button
		start.setBounds(10,7,105,30); //changes start button position
		
		moveAllL = false; //changes move all button boolean to false
		addBotEnable(); //makes the empty buttons enabled
	}
	
	/**
	 * Starts the pin movements again
	 */
	public void start(){
		pause = false; //change to false pause boolean
		for(pin p: pins) { //starts pin movements
			p.move = true;
		}
		moveAllL = true; //starts all laser movements
		for(JButton add: addBot) { //makes all the buttons disabled
			add.setEnabled(false); 
		}
	}
	
	/**
	 * Set's pin back to the start
	 * pre: a pin object called p
	 */
	public void restartPin (pin p) {
		//restart movement
 	    p.iM = 0;  //makes movement index 0
 	    p.x = pins.indexOf(p)*-100 - 20; //sets x position back to start
 	    p.y = 535; //sets y position back to start
 	    p.pinI = p.right; //set's image back to start
 	    p.move = false; //makes the pin stop moving
 	    
 	    //makes the button into a start button
 	    start.setText("START");
 	    start.setBounds(10,7,75,30);
 	   
	}

	/**
	 * Finds total hp of all balloons percentage
	 * post: a double value of percent of totalHP left
	 */
	public double percentHP () {
		int total = 0; //total variable
		for(balloon b: balloons) {
			total += b.hp; //adding up all the HP together
		}
		double percent = ((total) / ((balloon.startHP*5)*1.0)) * 100; //finding the percent of the HP
		
		return percent;
	}
	
	/**
	 * Replaces the bar image with current progress bar
	 */
	public void replaceBar () {
		double percent = 100 - percentHP(); //gets percent of bar gone
		percentL.setText(" " + Integer.toString((int)percentHP())); //changes the percent text
		int index = (int)(percent / 12.5); //finding which index of the bar array is needed by dividing by 12.5 from the percent
		currentBar = bars[index]; //setting current bar to index of progress
		
	}
	
	/**
	 * Changes balloons, pins and screens to start a new round
	 */
	public void newRound () throws IOException {
		losingPanel.points = points;
		losingPanel.highScore.setText(Integer.toString(gamePanel.highScore)); //update final high score label
		gameWindow.centerPanel.add(new losingPanel(), "losingPanel");
		gameWindow.navigation.show(gameWindow.centerPanel, "losingPanel"); //sets them to losing screen
		
		level = 1; //restarts level
		levelsL.setText("LEVEL " + level); //shows new level
		percentL.setText("100"); //restarts and shows new percent
		for(balloon balloon: balloons) {
			balloon.hp = balloon.startHP; //restarts balloon's hp
		}
		currentBar = bars[0]; //restarts bar image to a full one
		for(int i = 0; i < balloons.size(); i++) { //makes all the balloon images into balloons instead of splats
			balloon b = balloons.get(i);
			b.balloonI = b.bI;
		}
		
		moveAllL = false; //makes all the laser to stop moving
		creatingPins(); //restarting pins
		start.setText("START NEW ROUND"); //change button's text
 	    start.setBounds(10,7,150,30);
 	    
 	    //resets coins and points and there labels
 	    coins = 10;
 	    points = 0;
 	    updateLabels(); //updates their corresponding labels
 	    
 	    
 	    robots.removeAll(robots); //remove all purchased robots
 	    for(int i = 0; i < addBotEnable.length; i++) { //make all the labels enabled
 	    	addBotEnable[i] = true;
 	    }
 	    
 	    for(boolean b: addBotEnable) { //makes all the enable bot true again
 	    	b = true;
 	    }
 	    
 	   for(int i = 0; i < addBot.length; i++) { //adds all directions back to bot
			addBotSetDirection(addBot[i],botDirection[i]);
		}
 	   
 	    addBotEnable(); //enable the buttons
 	
	}
  
	/**
	 * Creates a new set of pins according to level its at
	 */
	public void creatingPins () {
		int incr = 0; //increase pin strength value
		if(level < 15) {
			numberOfPins = level*5; //assigning number of pins
		}
		else {
			numberOfPins = 15*5; //max out pin amount
			incr = (int)(((level-14)/3.0)*1); //start increasing pin strength
			if(level > 25) { //after a certain point increases strength  even more
				incr *= (level/10);
			}
		}
		pins.removeAll(pins); //remove all current pins
		
		//makes the separation between pin smaller as the levels progress
		int seperation = 0;
		if(level <= 9) 
			seperation = 50 - level*5;
		else
			seperation = 5; //default seperation
		
		for(int i = 0; i < numberOfPins; i++) { //create pins at different strength according to level
			if(level > 4 && i % 5 == 0) {
				pins.add(new pin(((i*-seperation)-20),535,pinType.gold));
				
			}
			else if(level > 2 && i % 3 == 0) {
				pins.add(new pin(((i*-seperation)-20),535,pinType.purple));
			}
			else {
				pins.add(new pin(((i*-seperation)-20),535,pinType.grey));
			}
			pins.get(i).addForce(incr); //adds increasement to strength
	    }
		
		for(int i = 1; i <= 20; i++) { //assigns mega pin after level 15 to make levels diffcult
			if(level == i*5 + 10) {
				for(int j = 1; j <= i; j++) {
					pins.add(new pin(-200 * j, 529, pinType.mega));
				}
			}
		}
	}
	
	/**
	 * Starts a new level
	 */
	public void nextLevel () {
		level++; //add onto levels variable
		levelsL.setText("LEVEL " + level); //change text
		creatingPins(); //creating pins
		//makes the button into a start button
 	    start.setText("START");
 	    start.setBounds(10,7,75,30);
 	    
 	   //makes lasers stop and restart
 	    moveAllL = false;
 	   for(robot r: robots) {
			r.moveL = false;
			r.xL = r.startXL;
			r.yL = r.startYL;
		}
 	   
 	   addBotEnable(); //enables all robot adding buttons
 	   
	}
	
	/**
	 * Checks to see if the pin has passed a corner point based on its direction
	 * pre: a pin object
	 * post: a boolean of if the pin passed a corner point
	 */
	public boolean passedPoint (pin p) {
		String direction = movement[p.iM];
		if(p.iM + 1 < movement.length ) {
			if(direction.equals("left")) { //if its left checks to see if the x coordinate is to small
				return p.x <= cornersC[p.iM+1][0];
			}
			if(direction.equals("right")) { //if its right checks to see if the x coordinate is to big
				return p.x >= cornersC[p.iM+1][0];
			}
			if(direction.equals("up")) { //if its up checks to see if the y coordinate is to small
				return p.y <= cornersC[p.iM+1][1];
			}
			if(direction.equals("down")) { //if its down checks to see if the y coordinate is to big
				return p.y >= cornersC[p.iM+1][1];
			}
		
		}
				
		return false;
	}
	
	/**
	 * Adds the bought robot into position and changes button status
	 * pre: an int value of index 'i' to know which position the robot is added to
	 */
	public static void buyBot (int i, robotType t) {
		robots.add(new robot(t, botDirection[i], botCoord[i][0], botCoord[i][1])); //adds the robot into array list of all robots

		addBotEnable[i] = false; //make add button false
		addBotEnable(); //re adjust all button's according
		
		coinL.setText(Integer.toString(coins)); //change coins
		
	}
	
	/**
	 * Enables buttons that haven't been clicked on
	 */
	public static void addBotEnable () {
		for(int i = 0; i < addBot.length; i++) {
			if(addBotEnable[i]) { //if button is not bought make enable and visible true
				addBot[i].setEnabled(true);
				addBot[i].setVisible(true);
				addBotSetDirection(addBot[i],botDirection[i]);
			}
			else { //if button is bought make enable and visible true but button's icon to nothing to make button transparent
				addBot[i].setEnabled(true);
				addBot[i].setVisible(true);
				addBot[i].setIcon(empty);
			}
		}
	}
	
	/**
	 * Updates the labels of the coins and points
	 */
	public static void updateLabels () {
		//changes the labels 
		coinL.setText(Integer.toString(coins));
		pointsL.setText(Integer.toString(points));
		   
	}
	
	/**
	 * Puts the accrording robot direction for the button text
	 * pre: a Jbutton variable 'b' and direction of robot 'd'
	 */
	public static void addBotSetDirection (JButton b, robotDirec d) {
		b.setAlignmentX(CENTER_ALIGNMENT);
		b.setAlignmentY(CENTER_ALIGNMENT);
		if(d == robotDirec.left || d == robotDirec.right) { //if horizontal direction
			//sets icon accordingly
 		   if(d == robotDirec.left) {
 			   b.setIcon(left);
 		   }
 		   else {
 			  b.setIcon(right);
 		   }
 	   }
 	   else {//if vertical direction
 		   //sets icon accordingly
 		   if(d == robotDirec.down) {
 			  b.setIcon(down);
 		   }
 		   else {
 			  b.setIcon(up);
 		   }
 	   }
	}
	
	/**
	 * Gets array of other robot types
	 * pre: a robot type 'r'
	 * post: returns a array of the other types excluding r
	 */
	public robotType [] getNotBought (robotType r) {
		//arraylist of all robotTypes
		ArrayList <robotType> all = new ArrayList<>(Arrays.asList(robotType.happy, robotType.lovely, robotType.buddy, robotType.brainy, robotType.mega));
		all.remove(r); //removes the given type
		robotType [] not = new robotType [4]; //creates the array to return of other types
		//adds all the other types
		for(int i = 0; i < not.length; i++) {
			not[i] = all.get(i);
		}
		return not; //returns array
	}
	
	/**
	 * A method to change coordinates of button, robots and pins depending on map chosen
	 * pre: a map enum m to set accordingly to which map is chosen
	 */
	public static void setMap (map m) {
		board = m.boardI(); //get's board image
		cornersC = m.getCorners(); //get's pin corner coordinates
		movement = m.getDirection(); //get's pin movements
		botDirection = m.getBotDirection(); //get's bot corner directions
		botCoord = m.getBotCorners();//get's bot corner coordinates
		addBotEnable = new boolean [gamePanel.botDirection.length]; //boolean arraylist with add Bot button status
		addBot = new JButton [gamePanel.botDirection.length]; //arraylist with buttons to add Bot
		//default coin and point values
		points = 0;
		coins = 10;
	}

	/**
	 * A method that runs when any key is pressed
	 * pre: KeyEvent variable e that says which key is pressed
	 */
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){ //if space is clicked
			if(pause){ //if game already paused
				pause = false;
				start(); //start it 
			}
			else{ //if game is not paused
				pause = true;
				pause(); //pause it
			}
		}
		
	}

	//UNUSED METHOD
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//UNUSED METHOD
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

