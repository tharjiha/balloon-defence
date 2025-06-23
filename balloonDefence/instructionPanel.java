package balloonDefence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A instruction panel to show the instructions of this game
 * Tharjiha Suthekara
 * January 2nd, 2024
 */

public class instructionPanel extends JPanel implements ActionListener, ItemListener{
	Image background; //an image variable of button
	JButton ok = new JButton ("OK"); //an okay button to exit panel
	
	//two radio buttons for which info is visible
	JRadioButton pin = new JRadioButton("INFO. ON PINS");
	JRadioButton bot = new JRadioButton("INFO. ON BOTS");
	ButtonGroup group = new ButtonGroup(); //group of both buttons
	
	Image panel; //image of shown panel
	Image panelB; //image of bot panel
	Image panelP; //image of pin panel
	JLabel test = new JLabel(""); //a label to show changed panel
	
	//CONSTRUCTOR
    public instructionPanel() {
    	setLayout(null);
    	
    	Toolkit kit = Toolkit.getDefaultToolkit();
		background = kit.getImage("instructions.png"); //background image of board
		
		//changes image of a panel
		panelP = kit.getImage("pinInfo.png");
		panelB = kit.getImage("botInfo.png");
		panel = panelP; //makes pins as default
		
		
		ok.setFont(new Font("Sans", Font.BOLD, 18)); //changing button of instruction
	    menuPanel.editButton(ok, Color.WHITE, Color.WHITE); //editing text on button
	    ok.setBounds(415,610,150,30); //changing okay button position
		ok.addActionListener((ActionListener) this); //add to listener
		add(ok); //add to screen
		
		//changes buttons bounds
		pin.setBounds(140,315,150,30);
		bot.setBounds(290,315,150,30);
		group.add(pin);
		group.add(bot);
		//adds to sreen
		add(pin);
		add(bot);
		//adds to itemlistener
		pin.addItemListener(this);
		bot.addItemListener(this);
		
		test.setBounds(0,300,1000,1000); //sets bounds for test
		add(test); //adds to screen
		
		
    }
    
    /**
     * A paint component method to show all graphics (image) on screen
     */
    public void paintComponent(Graphics comp) {
		Graphics2D comp2D = (Graphics2D) comp; //creates graphic component to add images and backgrounds
		comp2D.drawImage(background, 0, 0, this); //adds background
		comp2D.drawImage(panel, 0, 0, this); //adds panel of info
	}

    /**
     * Runs when button is pressed
     * pre: an action event called 'e' of which button
     */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ok) { //if okay button exit screens
			popUpMain.frameP.dispose();
		}
	}

	/**
	 * Runs when radio button  is interacted with
	 * pre: an item event instance called 'e' of which radio button it is
	 */
	public void itemStateChanged(ItemEvent e) {
		if(e.getItemSelectable() == pin) { //if pin button shows pin screen
			panel = panelP;
			test.setText("p");
		}
		if(e.getItemSelectable() == bot) { //if bot button shows pin screen
			panel = panelB;
			test.setText("b");
		}
		
	}
    
}
