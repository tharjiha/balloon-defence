package balloonDefence;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A pop up main class to bring up another screen
 * Tharjiha Suthekara
 * January 2nd, 2024
 */

public class popUpMain {

	public static final CardLayout navigation = new CardLayout(); //navigation layout
	//frame and panel for popUpMain
	public static JFrame frameP;
	public static JPanel centerPanelP;
	
    public popUpMain() {
    	
    	frameP = new JFrame(""); //sets title and initializes frame
		frameP.setLayout(new BorderLayout());  //sets layout
		frameP.setSize(577, 690); //sets size
        frameP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //shut down the program if you close the window
        
		centerPanelP = new JPanel(navigation); 
        frameP.add(centerPanelP, BorderLayout.CENTER); //The window contains one panel and it has a card layout, which is a type of layout that switches between content panels i.e. cards.  
        //adds all panels to screen
        centerPanelP.add(new instructionPanel(), "instr");
        centerPanelP.add(new purchasePanel(), "purchase");
        centerPanelP.add(new updatePanel(), "update");
        
        navigation.show(centerPanelP, "instr"); //starts of with the menu panel
        frameP.setVisible(true); 
        
        
    }
    public static void main(String[] arguments) {
    	popUpMain frame = new popUpMain(); //calls on game panel class
    }

}
