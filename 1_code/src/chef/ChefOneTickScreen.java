// written by: Annie Antony and Nishtha Sharma
// tested by: Annie Antony and Nishtha Sharma
// debugged by: Annie Antony and Nishtha Sharma
package chef;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import dataBaseC.Ticket;
/**
 * Panel that will be used when one ticket is selected from the list of tickets screen.
 * Displays the current ticket - the dishes in it.
 * This is where the chef would modify the status of each dish
 * @author aa1122 and ns662
 *
 */
public class ChefOneTickScreen extends JPanel{
	/**
	 * current ticket opened
	 */
	public Ticket currTicket;
	/**
	 * way to access all the methods in the chef interface
	 */
	public ChefInterface ci;
	/**
	 * Constructor
	 * @param chefInterface
	 */
	public ChefOneTickScreen(ChefInterface chefInterface) {
		ci = chefInterface;
		/**
		 * Set color to blue
		 */
		setBackground(new Color(51, 153, 255));
		/**
		 * Array layout where you pick coordinates of each component
		 */
		setLayout(null);
	}

	/**
	 * This is called when ever you switch to this screen, it sets up the ticket 
	 * to be displayed on the screen.
	 * @param t - ticket to be displayed
	 */
	public void setTicket(Ticket t){
		currTicket = t;
		updateScreen();
		
	}
	/**
	 * Refreshes the screen
	 */
	public void updateScreen() {
		makeBackButton();
		repaint();
		validate();
	}
	/**
	 * Sets up the Back Button which is used to jump back to the list of tickets screen.
	 */
private void makeBackButton(){
		
		JButton logOutButton = new JButton("Back");
		logOutButton.setForeground(Color.WHITE);
		logOutButton.setBackground(Color.RED);
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ci.backToMainScreen();
			}
		});
		logOutButton.setBounds(1000, 0, 200, 30);
		add(logOutButton, getComponentCount());
		
	}
}
