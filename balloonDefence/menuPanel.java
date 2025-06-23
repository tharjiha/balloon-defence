package balloonDefence;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import balloonDefence.robot.robotDirec;

/**
 * Main home screen panel to allow users to pick a map and see instructions
 * Tharjiha Suthekara
 * December 19th, 2023
 */

public class menuPanel extends JPanel implements ActionListener {
	Image background; //background image
	Image map1, map2; //map image
	JButton start1, start2; //start buttons for both map
	JButton instructions = new JButton("Instructions".toUpperCase()); //buttons for instructions
	JButton seeScore = new JButton("See Score Records".toUpperCase()); //buttons to see scores sheets / leader board
	File sheet = new File("gameScores.txt"); //text file of score sheet
	File leaderBoard = new File("leaderBoard.txt"); //text file of leader board
	
	//CONSTRUCTOR
	public menuPanel () throws IOException {
		instructions.addActionListener(this); //adds home button to action listener
		setLayout(null); //sets layout to none
		
		//gets tool kit for images 
	    Toolkit kit = Toolkit.getDefaultToolkit();
	       
	    //gets images from folder using getImage in kit
	    background = kit.getImage("menu.png"); //background image of board
	    
	    map1 = getImage("board1.png",160,126); //image of the map
	    ImageIcon map1Pic = new ImageIcon(map1); //making the map into image icon
	    start1 = new JButton(map1Pic); //adding map image to start button
	    start1.addActionListener(this); //adding start button to actionlistener
	    start1.setBounds(528,140,150,110); //position start button
	    add(start1); //adding button to screen
	    
	    map2 = getImage("board2.png",160,126); //image of the map
	    ImageIcon map2Pic = new ImageIcon(map2); //making the map into image icon
	    start2 = new JButton(map2Pic); //adding map image to start button
	    start2.addActionListener(this); //adding start button to actionlistener
	    start2.setBounds(528,287,150,110); //position start button
	    add(start2); //adding button to screen
	    
	    instructions.setFont(new Font("Sans", Font.BOLD, 22)); //changing button of instruction
	    editButton(instructions, Color.WHITE, Color.WHITE); //editing text on button
	    instructions.setBounds(560,550,250,40); //sets position of home button
	    add(instructions); //adds button to home screen
	    
	    //adds see score button to screen with coordinates
	    seeScore.setBounds(130,400,330,30);
	    add(seeScore);
	    seeScore.addActionListener(this); //adds to actionlistener
	    losingPanel.getHighScore(); //get's current high score from text file
	}
	
	/**
	 * A pain component to show graphics
	 */
	public void paintComponent(Graphics comp) {
		Graphics2D comp2D = (Graphics2D) comp; //creates graphic component to add images and backgrounds
		comp2D.drawImage(background, 0, -50, this); //adds background
		comp2D.drawImage(background, 0, 600, this); //adds bottom background
	}

	/**
	 * Runs when button is clicked
	 * post: for home button returns user to menu panel
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == instructions) { //if instruction button is clicked
			popUpMain popUp = new popUpMain();
			popUpMain.navigation.show(popUpMain.centerPanelP,"instr");
		}
		if(e.getSource() == start1) { //if start button is clicked goes to gamePanel with first map
			gameWindow.centerPanel.add(new gamePanel(map.map1), "gamePanel");
			gameWindow.navigation.show(gameWindow.centerPanel, "gamePanel");
	
		}
		if(e.getSource() == start2) { //if start button is clicked goes to gamePanel with second map
			gameWindow.centerPanel.add(new gamePanel(map.map2), "gamePanel");
			gameWindow.navigation.show(gameWindow.centerPanel, "gamePanel");
		}
		if(e.getSource() == seeScore) {
			Object[] options = {"LEADERBOARD", "SCORE SHEET"}; //options with see Score button array
			//dialog box to pick which text file to see
			int n = JOptionPane.showOptionDialog(null, "How would you like to see the scores?", " ", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
		    if(n == 1) {
		    	try {
					Desktop.getDesktop().open(sheet); //opens score sheet text file
				} catch (IOException e1) {
					//Catches error
					e1.printStackTrace();
				}
		    }
		    else {
		    	try {
					Desktop.getDesktop().open(leaderBoard); //opens leaderboard text file
				} catch (IOException e1) {
					//Catches error
					e1.printStackTrace();
				}
		    }
		}
		
		
	}
	
	/**
	 * Changes the text to make it tranparent and sets it's colours
	 * pre: A Jbutton, and 2 colours for the background and foreground
	 */
	public static void editButton(JButton button, Color c1, Color c2) {
		button.setBackground(c1); //changes background
		button.setForeground(c2); //changes foreground
		
		//makes the button transparent with only text
		button.setBorder(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
	}
	
	/**
	 * Creates a image variable of image and resizes it
	 * pre: a string variable of file name, two int values of width and height of image
	 * post: an image variable of the resized image
	 */
	public static Image getImage(String name, int w, int h) {
		Toolkit kit = Toolkit.getDefaultToolkit(); //gets tool kit
		Image image;
		image = kit.getImage(name); //gets image
	    image = image.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH ); //rescaling image 
	    return image;
	}
	
	


}
