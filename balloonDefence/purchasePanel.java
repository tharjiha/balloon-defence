package balloonDefence;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A panel to create a new pop up screen to purchase a type of robot
 * Tharjiha Suthekara
 * January 2nd, 2024
 */

public class purchasePanel extends JPanel implements ActionListener {
	Image background; //image of background
	JButton [] buys = new JButton [5]; //all buy buttons
	robotType [] bots = {robotType.happy, robotType.lovely, robotType.buddy, robotType.brainy, robotType.mega}; //all robot types array
	int [][] buttonCoord = {{186,211},{480,211},{186,403},{480,403},{186,600}}; //coordinates of buy buttons
	//all labels of prices, speeds
	JLabel [] prices = new JLabel [5];
	JLabel [] speeds = new JLabel [5];
	JLabel [] forces = new JLabel [5];
	static robotType chosenType = robotType.happy; //chosen type default
	
	JLabel coinsLabel = new JLabel(Integer.toString(gamePanel.coins)); //make a label showing current coins available
	
	//images and image icons for an error images
	Image errorI;
	static ImageIcon errorII;
	
	JButton exit = new JButton("EXIT"); //button to exit panel
	
	//CONSTRUCTOR
    public purchasePanel() {
    	setLayout(null); //make layout null to use bounds
    	
    	Toolkit kit = Toolkit.getDefaultToolkit(); //get toolkit
		background = kit.getImage("purchaseBotBackground.png"); //background image of board
		
		for(int i = 0; i < buys.length; i++) { 
			//adding all buy buttons
			buys[i] = new JButton("BUY"); //setting title
			buys[i].setBounds(buttonCoord[i][0],buttonCoord[i][1],50,20); //setting bounds
			buys[i].setFont(new Font("SansSerif", Font.BOLD, 15)); //changing font
			menuPanel.editButton(buys[i], Color.WHITE, Color.WHITE); //changing colour and tranparency
			buys[i].addActionListener(this); //adding to action listener
			
			int cost = bots[i].getCost(); //get button's type cost
			prices[i] = new JLabel(Integer.toString(cost)); //add to label
			prices[i].setBounds(buttonCoord[i][0],buttonCoord[i][1],50,20); //set the bound
			prices[i].setFont(new Font("SansSerif", Font.BOLD, 15)); //set the new font
			
			int velocity = bots[i].getVelocity()[0]; //get button's type velocity
			speeds[i] = new JLabel(Integer.toString(velocity)); //add to label
			speeds[i].setBounds(buttonCoord[i][0] - 85,buttonCoord[i][1] - 7,50,20); //set the bound
			speeds[i].setFont(new Font("SansSerif", Font.BOLD, 15)); //set the new font
			
			int force = bots[i].getForce()[0]; //get button's type force
			forces[i] = new JLabel(Integer.toString(force)); //add to label
			forces[i].setBounds(buttonCoord[i][0] - 85,buttonCoord[i][1] + 10,50,20); //set the bound
			forces[i].setFont(new Font("SansSerif", Font.BOLD, 15)); //set the new font
		}
		
		//adjust bounding for other labels
		prices[0].setBounds(210,95,50,20);
		prices[1].setBounds(494,95,50,20);
		prices[2].setBounds(210,280,50,20);
		prices[3].setBounds(494,280,50,20);
		prices[4].setBounds(210,476,50,20);
		
		speeds[1].setBounds(buttonCoord[1][0] - 95,buttonCoord[1][1] - 7,50,20);
		speeds[3].setBounds(buttonCoord[3][0] - 95,buttonCoord[3][1] - 7,50,20);
		forces[1].setBounds(buttonCoord[1][0] - 95,buttonCoord[1][1] + 10,50,20);
		forces[3].setBounds(buttonCoord[3][0] - 95,buttonCoord[3][1] + 10,50,20);
		
		//adding all buttons and labels to panel
		for(int i = 0; i < buys.length; i++) {
			add(buys[i]);
			add(prices[i]);
			add(speeds[i]);
			add(forces[i]);
		}
		
		coinsLabel.setBounds(400,455,200,200); //set coin labels bounds
		coinsLabel.setFont(new Font("SansSerif", Font.BOLD, 60)); //set coin fonts
		add(coinsLabel); //add to panel
		
		errorI = menuPanel.getImage("random/error.png", 100, 100); //making error image
		errorII = new ImageIcon(errorI); //making into image icon
		
		//setting exit button
		exit.setBounds(497,617,50,20); //setting bounds
		exit.setFont(new Font("SansSerif", Font.BOLD, 15)); //changing font
		menuPanel.editButton(exit, Color.BLACK, Color.BLACK); //changing colour and tranparency
		exit.addActionListener(this); //adding to action listener
		add(exit);
		
    }
    
    /**
     * A paint component method to show graphics
     */
    public void paintComponent(Graphics comp) {
		Graphics2D comp2D = (Graphics2D) comp; //creates graphic component to add images and backgrounds
		comp2D.drawImage(background, 0, 0, this); //adds background
	}

    /**
     * A method that runs when button is pressed
     * pre: an action event variable 'e' to know which button is pressed
     */
	public void actionPerformed(ActionEvent e) {
		
		for(int i = 0; i < buys.length; i++) {
			JButton buy = buys[i];
			if(e.getSource() == buy) { //check to see if one of the buy button is clicked
				chosenType = bots[i]; //gets which bot is purchased
				if(gamePanel.coins >= chosenType.getCost()) { //if within their coin amount
					gamePanel.coins -= chosenType.getCost(); //change amount of coins person has
					coinsLabel.setText(Integer.toString(gamePanel.coins)); //change coin text
					gamePanel.buyBot(gamePanel.boughtIndex, purchasePanel.chosenType); //call buying method in gamePanel
					
				}
				else { //if not show an error pop up
					chosenType = null;
					error();
				}
				popUpMain.frameP.dispose(); //get rid of screen
			}
		}
		
		if(e.getSource() == exit) {
			popUpMain.frameP.dispose(); //get rid of screen
		}
		
	}
	
	/**
	 * Displays a pop up message indicating they can't afford item
	 */
	public static void error () {
		JOptionPane.showMessageDialog(null, "You can't afford it :(", "PURCHASE ERROR", JOptionPane.INFORMATION_MESSAGE,purchasePanel.errorII);
	}
	
    
}
