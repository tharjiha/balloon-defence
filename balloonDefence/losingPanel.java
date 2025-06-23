package balloonDefence;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

import javax.swing.*;

/**
 * Lost panel to show they have moved on to a new round
 * Tharjiha Suthekara
 * December 19th, 2023
 */

public class losingPanel extends JPanel implements MouseListener, ActionListener {
	Image background; //background image
	JButton home = new JButton("Go to Home Screen".toUpperCase()); //home button
	JButton addScore = new JButton("Add Score to Record".toUpperCase());
	String name;
	
	//changes score and high score label to be centered
	static JLabel scoreL = new JLabel(Integer.toString(gamePanel.points), SwingConstants.CENTER); 
	static JLabel highScore = new JLabel(Integer.toString(gamePanel.highScore), SwingConstants.CENTER);
	
	static int points = 0; //static variable of there points before losing
	
	
	
	//CONSTRUCTOR
	public losingPanel () throws IOException{
		addMouseListener(this); //adds screen to mouse listener
		home.addActionListener(this); //adds home button to action listener
		addScore.addActionListener(this);
		setLayout(null); //sets layout to none
		
		//gets tool kit for images 
	    Toolkit kit = Toolkit.getDefaultToolkit();
	       
	    //gets images from folder using getImage in kit
	    background = kit.getImage("lostScreen.png"); //background image of board
	    
	    
	    scoreL.setFont(new Font("SansSerif", Font.BOLD, 20)); //adjusting the font of percents text
	    scoreL.setForeground(Color.WHITE); //make text white
	    scoreL.setBounds(440,432,100,50); //set percent label position
	    highScore.setFont(new Font("SansSerif", Font.BOLD, 20)); //adjusting the font of percents text
	    highScore.setForeground(Color.WHITE); //make text white
	    highScore.setBounds(440,487,100,50); //set percent label position
	       
	    home.setBounds(180,555,210,30); //sets position of home button
	    addScore.setBounds(400,555,210,30); //sets position of add score button
	    
	    add(home); //adds button to home screen
	    add(addScore); //adds add score button
	    //add score and high score label to screen
	    add(scoreL);
	    add(highScore);
	    
	    update(gamePanel.highScore); //updates the highscore to txt file
	    
		
	}
	
	/**
	 * Paint component method to show graphics on screen
	 */
	public void paintComponent(Graphics comp) {
		Graphics2D comp2D = (Graphics2D) comp; //creates graphic component to add images and backgrounds
		comp2D.drawImage(background, 0, 0, this); //adds background
	}

	//Unused mouselistener method
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	//Unused mouselistener method
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	//Unused mouselistener method
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Runs when mouse is pressed
	 * post: returns user back to gamePanel
	 */
	public void mousePressed(MouseEvent e) {
		losingPanel.scoreL.setText(Integer.toString(gamePanel.points)); //update final score label   
		gameWindow.navigation.show(gameWindow.centerPanel, "gamePanel"); //starts new game and sends to game Panel when screen is clicked
		
	}

	//Unused mouselistener method
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Runs when button is clicked
	 * post: for home button returns user to menu panel
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == home) {
			gameWindow.navigation.show(gameWindow.centerPanel, "menuPanel"); //sends user back to home page when clicked
		}
		if(e.getSource() == addScore) { //if it's add score button
			name = JOptionPane.showInputDialog(null,"What is your name? ","RECORD YOUR SCORE",JOptionPane.PLAIN_MESSAGE); //dialog box to ask for name
			try {
				addName(name,points); //adds name to txt file
			} catch (IOException e1) {
				//try and catch for exception error
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Updates the text file with the new/current high score
	 * pre: a int value of the high score
	 */
	 public static void update(int highScore) throws IOException {
		ArrayList <String> txt = getTxtArray(); //get's an arraylist of text in score sheet
		PrintWriter p = new PrintWriter("gameScores.txt"); //print writer of score sheet
		p.println("HIGHSCORE: " + highScore); //adds high score
	    for(int i = 1; i < txt.size(); i++) {
	    	p.println(txt.get(i)); //get's the other text (scores of array and adds it
	    }
	    p.close(); //closs print writer
	}
	 
	 /**
	  * Get's the current high score from the text file
	  */
	 public static void getHighScore() throws IOException {
		Scanner in = new Scanner(new File("gameScores.txt")); //scanner input of score sheet
		String txt = in.nextLine(); //get's first line
		txt = txt.substring(11); //get's only high score
	    gamePanel.highScore = Integer.parseInt(txt); //adds to game panel class
	    in.close();
	    	
	 }
	 
	 /**
	  * Get's an arraylist of all text in game Score text file
	  * post: a string arraylist
	  */
	 public static ArrayList<String> getTxtArray() throws FileNotFoundException{
		 Scanner in = new Scanner(new File("gameScores.txt")); //scanner for score sheet
		 ArrayList <String> txtInfo = new ArrayList <>(); //get's al scores
		 while(in.hasNextLine()){ //get's all lines and adds to array list
			String line = in.nextLine();
			txtInfo.add(line);
		 }
		 in.close();
		 return txtInfo;
	 }
	 
	 /**
	  * Adds the person's name and their score to the text file
	  * pre: a string value of person's name and an int of there score
	  */
	  public static void addName (String name, int score) throws IOException{
		 //get's current time and date of game ending
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		 LocalDateTime now = LocalDateTime.now(); 
		 
		 ArrayList <String> txt = getTxtArray(); //adds to array list
		 
		 PrintWriter p = new PrintWriter("gameScores.txt"); //print writer for score sheet
		 txt.add(1,name + " - " + score + " [" + dtf.format(now) + "]"); //add new sore to array list
			
		 for(String t: txt) { //adds everything from array list to text file
			 p.println(t);
		 }
		 p.close();
		 updateLeaderBoard(txt); //update leaderboard
	 }
	 
	 /**
	  * Takes info from score sheet and makes a leader board file out of them
	  * pre: an arraylist of everything in score sheet text file
	  */
	 public static void updateLeaderBoard(ArrayList <String> txt) throws IOException{
		 PrintWriter pG = new PrintWriter("leaderBoard.txt"); //print wriiter for leader board
		 int [] scores = new int [txt.size() - 1]; //array of all scores
		 String [] names = new String [txt.size() - 1]; //array of all names
		 
		 for(int i = 0; i < scores.length; i++) { //get's scores and names from txt file array it and adds them to array
			 String t = txt.get(i+1);
			 names[i] = t.split(" - ")[0];
			 String s = t.split(" - ")[1].substring(0,t.split(" - ")[1].indexOf(" "));
			 scores[i] = Integer.parseInt(s);
			 
		 }
		 
		 //Bubble sort to sort all scores with corresponding names
		 boolean swap; //boolean check to see if swapped
		 do {
			 swap = false; //default starts of false
			 for(int i = 0; i < scores.length-1; i++) {
				 if(scores[i] < scores[i+1]) { //if next score is greater
					 //switches adjacent scores and names wih each other
					 int temp = scores[i];
					 String tempN = names[i];
					 scores[i] = scores[i+1];
					 names[i] = names[i+1];
					 scores[i+1] = temp;
					 names[i+1] = tempN;
					 swap = true; //makes swapped true
				 }
			 }
		 } while(swap); //runs while all elements don't have to be swapped
		 
		 pG.println("BALLOON DEFENCE LEADER BOARD"); //printing title of text file
		 for(int i = 0; i < scores.length; i++) { //adding all names and score board
			 //if top 3 adding a medal emoji
			 if(i == 0) {
				pG.print("🥇)"); 
			 }
			 else if(i == 1) {
				pG.print("🥈)");  
			 }
			 else if(i == 2) {
				pG.print("🥉)");  
			 }
			 else { //rest just gets number
				 pG.print(i+1 + ")");
			 }
			 pG.println(" " + names[i] + " - " + scores[i]); //adding score and name
			 
			 if(i == 2) { //seperating top 3 from rest
					pG.println("");  
			 }
		 }
		 
		 pG.close();
		 
	 }
	 



}
