package balloonDefence;

import java.awt.*;
import javax.swing.*;

import balloonDefence.robot.robotDirec;

import java.awt.event.*;

/**
 * A update panel that allows user to change robot or increase force/speed
 * Tharjiha Suthekara
 * January 2nd, 2024
 */

public class updatePanel extends JPanel implements ActionListener{
	Image background; //a image variable of background
	
	JButton exit = new JButton("EXIT"); //a exit button
	
	//a default array of not bought robots and replace bot type
	static robotType [] notBought = {robotType.happy, robotType.lovely, robotType.brainy, robotType.brainy};
	static robotType replaceBot = robotType.mega;
	
	//images of robots to replace with
	Image [] robotImage = new Image [4]; 
	JButton [] replaceB = new JButton [4]; //all replace buttons
	JLabel [] coinL = new JLabel [4]; //all buttons cost
	
	JLabel [] velocityL = new JLabel [4]; //all bots velocity
	JLabel [] forceL = new JLabel[4]; //all bots force
	
	static robot remove = new robot(replaceBot, robotDirec.up, 10, 10); //default bot that is being clicked to be removed or adjusted
	
	JButton incrSpeed = new JButton("Increase Speed".toUpperCase()); //a button to increase speed 
	JButton incrStrength = new JButton("Increase Force".toUpperCase()); //a button to increase strength
	
	//labels for speed and force and their increase cost
	JLabel newSpeed = new JLabel("s");
	JLabel newSpeedC = new JLabel("c");
	JLabel force = new JLabel("n");
	JLabel forceC = new JLabel("c");
	
	//int value of update speed/force cost
	int updateSpeedC;
	int updateForce;
	
	Image maxButtonS; //image of max button for speed
	Image maxButtonF; //image of max button for force
	
	//CONSTRUCTOR
	public updatePanel() {
    	setLayout(null); //make layout null to use bounds
    	
    	Toolkit kit = Toolkit.getDefaultToolkit(); //get toolkit
		background = kit.getImage("BackgroundImages//updateBackground.png"); //background image of board
		for(int i = 0; i < notBought.length; i++) { //adds robot image
			robotImage[i] = notBought[i].getImage();
		}
		
		exit.setBounds(200,611,160,30); //positions exit button
		menuPanel.editButton(exit, Color.BLACK, Color.BLACK); //edit button
		exit.setFont(new Font("SansSerif",Font.BOLD,15)); //edit exit button text
		exit.addActionListener(this); //adds to action listener
		add(exit); //add to screen
		
		//a loop to adjust for each replace bot
		for(int i = 0; i < replaceB.length; i++) {
			//adjusts the replace buttons position, text, colour and size while adding to screen and action listener
			replaceB[i] = new JButton("REPLACE");
			replaceB[i].setBounds((25 + i*142),(280 + i*1),100,30);
			menuPanel.editButton(replaceB[i], Color.BLACK, Color.WHITE);
			replaceB[i].setFont(new Font("SansSerif",Font.BOLD,15));
			replaceB[i].addActionListener(this);
			add(replaceB[i]);

			//adjusts the coin label position, text, colour and size while adding to screen
			coinL[i] = new JLabel(Integer.toString(notBought[i].getCost()));
			coinL[i].setBounds((70 + i*142),(247 + i*1),50,20);
			coinL[i].setFont(new Font("SansSerif",Font.BOLD,15));
			add(coinL[i]);
			
			//adjusts the velocity label position, text, colour and size while adding to screen
			velocityL[i] = new JLabel(Integer.toString(notBought[i].getVelocity()[0]));
			velocityL[i].setBounds((90 + i*142),(205 + i*1),50,20);
			velocityL[i].setFont(new Font("SansSerif",Font.BOLD,15));
			add(velocityL[i]);
			
			//adjusts the force label position, text, colour and size while adding to screen
			forceL[i] = new JLabel(Integer.toString(notBought[i].getForce()[0]));
			forceL[i].setBounds((90 + i*142),(220 + i*1),50,20);
			forceL[i].setFont(new Font("SansSerif",Font.BOLD,15));
			add(forceL[i]);

		}
	
		//getting maxButton default for both
		maxButtonS = menuPanel.getImage("random/maxButton.png", 195, 33); 
		maxButtonF = menuPanel.getImage("random/maxButton.png", 195, 33);
		   
		
	    updateSpeedC = (int)((remove.robotT.getVelocity()[remove.currentVIndex] * 2.5)); //sets cost for new speed
		newSpeed.setText(Integer.toString(remove.robotT.getVelocity()[remove.currentVIndex])); //set the current speed label
		newSpeedC.setText(Integer.toString(updateSpeedC)); //update coin label
		newSpeed.setBounds(100,414,50,50); //adjust speed position
		newSpeed.setFont(new Font("SansSerif",Font.BOLD,15));  //adjust speed font
		add(newSpeed); //add to screen
		
		newSpeedC.setBounds(235,405,50,50);  //adjust new speed coin position
		newSpeedC.setFont(new Font("SansSerif",Font.BOLD,15));  //adjust new speed coin font
		add(newSpeedC); //add to screen
		
		incrSpeed.setBounds(69,552,160,25); //sets increase speed button position
		if(remove.currentVIndex <= 2) { //if not at max
			incrSpeed.setText("INCREASE SPEED TO " + (Integer.toString(remove.robotT.getVelocity()[remove.currentVIndex + 1]))); //show new speed in button
			maxButtonS = menuPanel.getImage(".png", 195, 33); //remove image in max button
		}
		else {  //if max reached
			incrSpeed.setEnabled(false); //disable button
			incrSpeed.setText(""); //remove text
			newSpeedC.setText("N/A"); //remove coin amount
			maxButtonS = menuPanel.getImage("random/maxButton.png", 195, 33); //add max button image
		}
		//changes buttons font, colour and adds to action listener and screen
		incrSpeed.setFont(new Font("SansSerif",Font.BOLD,14)); 
		menuPanel.editButton(incrSpeed, Color.WHITE, Color.WHITE);
		add(incrSpeed);
		incrSpeed.addActionListener(this);
		
		
		
		updateForce = (int)((remove.robotT.getForce()[remove.currentFIndex] * 3)); //int value of new force cost
		force.setText(Integer.toString(remove.robotT.getForce()[remove.currentFIndex])); //update label of force with current force
		forceC.setText(Integer.toString(updateForce)); //update label of force with new force cost
		force.setBounds(375,414,50,50); //adjust force label position
		force.setFont(new Font("SansSerif",Font.BOLD,15)); //adjust new force font
		add(force); //add to screen
		
		forceC.setBounds(517,405,50,50);  //adjust force coin label position
		forceC.setFont(new Font("SansSerif",Font.BOLD,15));  //adjust new force coin font
		add(forceC); //add to screen
		
		incrStrength.setBounds(317,552,220,25); //sets increase strength button position
		if(remove.currentFIndex <= 2) { //if not at max
			incrStrength.setText("INCREASE FORCE TO " + (Integer.toString(remove.robotT.getForce()[remove.currentFIndex + 1]))); //show new speed in button
			maxButtonF = menuPanel.getImage(".png", 195, 33); //remove image in max button
		}
		else { //if max reached
			incrStrength.setEnabled(false);  //disable button
			incrStrength.setText(""); //remove text
			forceC.setFont(new Font("SansSerif",Font.BOLD,10));
			forceC.setText("N/A"); //remove coin amount
			maxButtonF = menuPanel.getImage("random/maxButton.png", 195, 33); //add max button image
		}
		//changes buttons font, colour and adds to action listener and screen
		incrStrength.setFont(new Font("SansSerif",Font.BOLD,14)); 
		menuPanel.editButton(incrStrength, Color.WHITE, Color.WHITE);
		add(incrStrength);
		incrStrength.addActionListener(this);
		
	}

	/**
	 * A paint component to show graphics
	 */
	 public void paintComponent(Graphics comp) {
			Graphics2D comp2D = (Graphics2D) comp; //creates graphic component to add images and backgrounds
			comp2D.drawImage(background, 0, 0, this); //adds background
			
			for(int i = 0; i < robotImage.length; i++) { //add all replace robot images
				comp2D.drawImage(robotImage[i],(43 + i*142),107, this);
			}
			
			//add max buttons
			comp2D.drawImage(maxButtonS,50,548,this);
			comp2D.drawImage(maxButtonF,330,548,this);
	}
	
	/**
	 * A method that is run when button is clicked
	 * pre: an action event variable 'e' of which button is clicked
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exit) { //if exit leave screen
			popUpMain.frameP.dispose();
		}
		for(int i = 0; i < replaceB.length; i++) { //for all replace buttons
			if(e.getSource() == replaceB[i]) { //for this replace button
				replaceBot = notBought[i]; //get new bot type
				
				if(gamePanel.coins >= replaceBot.getCost()) { //if within their coin amount
					gamePanel.coins -= replaceBot.getCost(); //change amount of coins person has
					gamePanel.buyBot(gamePanel.boughtIndex, replaceBot); //call buying method in gamePanel to replace with new type
					gamePanel.robots.remove(remove); //remove the robot in arraylist
					
				}
				else { //if not show an error pop up
					replaceBot = null; //make replace null
					purchasePanel.error(); //show error
				}
				popUpMain.frameP.dispose(); //get rid of screen
			}
		}
		
		if(e.getSource() == incrSpeed) { //if it's to increase speed
			if(gamePanel.coins >= updateSpeedC) { //if within their coin amount
				int i = gamePanel.robots.indexOf(remove); //index of robot to adjust
				gamePanel.robots.get(i).currentVIndex++; //add to the robots velocity index
				int c = gamePanel.robots.get(i).currentVIndex; //get current index of robot velocity
				gamePanel.robots.get(i).velocity = gamePanel.robots.get(i).robotT.getVelocity()[c]; //adjust speed with new index
				gamePanel.coins -= updateSpeedC; //update coins
				gamePanel.coinL.setText(Integer.toString(gamePanel.coins)); //update coins label
			}
			else { //if not show an error pop up
				purchasePanel.error(); //show error
			}
			popUpMain.frameP.dispose(); //get rid of screen
			
		}
		
		if(e.getSource() == incrStrength) { //if it's to increase force
			if(gamePanel.coins >= updateForce) {  //if within their coin amount
				int i = gamePanel.robots.indexOf(remove);//index of robot to adjust
				gamePanel.robots.get(i).currentFIndex++; //add to the robots force index
				int c = gamePanel.robots.get(i).currentFIndex; //get current index of robot force
				gamePanel.robots.get(i).force = gamePanel.robots.get(i).robotT.getForce()[c]; //adjust force with new index
				gamePanel.coins -= updateForce; //update coins
				gamePanel.coinL.setText(Integer.toString(gamePanel.coins)); //update coins label
			}
			else { //if not show an error pop up
				purchasePanel.error(); //show error
			}
			popUpMain.frameP.dispose(); //get rid of screen
		}
		
	}

}
