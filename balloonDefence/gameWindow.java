package balloonDefence;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;

/**
 * GameWindow class sets up the frame for the game, and contains the main method of the game.
 * Tharjiha Suthekara
 * January 2nd, 2024
 */

public class gameWindow extends JFrame {
	public static final CardLayout navigation = new CardLayout();
	public static JFrame frame;
	public static JPanel centerPanel;
	
	//CONSTRUCTOR
    public gameWindow() throws IOException {
    	frame = new JFrame("Balloon Defence"); //sets title and initializes frame
		frame.setLayout(new BorderLayout());  //sets layout
		frame.setSize(800, 660); //sets size
        frame.setLocationRelativeTo(null); //position the window in the center of the screen
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //shut down the program if you close the window
        
		centerPanel = new JPanel(navigation); 
        frame.add(centerPanel, BorderLayout.CENTER); //The window contains one panel and it has a card layout, which is a type of layout that switches between content panels i.e. cards.  
        //adds all panels to screen
		
        centerPanel.add(new menuPanel(), "menuPanel");
        
        navigation.show(centerPanel, "menuPanel"); //starts of with the menu panel
        frame.setVisible(true); 
    }
    public static void main(String[] arguments) throws IOException{
    	gameWindow frame = new gameWindow(); //calls on game panel class
    }
    
    
   
}