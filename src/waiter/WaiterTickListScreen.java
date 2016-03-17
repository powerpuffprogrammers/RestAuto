package waiter;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class WaiterTickListScreen extends JPanel {

	public WaiterInterface wi;
	
	/**
	 * Create the panel.
	 * @param waiterInterface 
	 */
	public WaiterTickListScreen(WaiterInterface waiterInterface) {
		wi = waiterInterface;
		//Set color to blue
		setBackground(new Color(51, 153, 255));
		//Array layout where you pick coordinates of each component
		setLayout(null);
		
		makeNameText();
		makeLogOutButton();	
		makeTicketButtons();
		System.out.println("Exiting waiterTickListScreen constructor");
	}
	
	/**
	 * THIS IS ONLY FOR A TEST
	 */
	public WaiterTickListScreen() {
		//Set color to blue
				setBackground(new Color(51, 153, 255));
				//Array layout where you pick coordinates of each component
				setLayout(null);
				
				makeNameText();
				makeLogOutButton();	
				makeTicketButtons();
	}

	private void makeNameText() {
		JTextField nameHeader;
		nameHeader = new JTextField();
		nameHeader.setEditable(false);
		nameHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameHeader.setHorizontalAlignment(SwingConstants.CENTER);
		nameHeader.setText("Logged In As: "+ wi.name);
		nameHeader.setBounds(0, 0, 200, 30);
		add(nameHeader);
		nameHeader.setColumns(10);
		
	}

	/**
	 * Looks through the ticket list and generates buttons for this
	 */
	private void makeTicketButtons() {
		int index =0;
		Iterator<Integer> keyset = wi.listOfTickets.keySet().iterator();
		while(keyset.hasNext() && index<4){
			int key = keyset.next();
			makeTicketButton(key,index);
			index++;
		}
	}

	private void makeTicketButton(int key, int index) {
		int xbut=0, ybut=0;
		if(index ==0){
			xbut = 100;
			ybut = 100;
		}
		else if(index ==1){
			xbut = 100;
			ybut = 350;
		}
		else if(index ==2){
			xbut = 700;
			ybut = 100;
		}
		else if(index ==3){
			xbut = 700;
			ybut = 350;
		}
		
		//draw the button
		JButton ticketButton = new JButton("#"+key);
		ticketButton.setForeground(Color.BLACK);
		ticketButton.setBackground(Color.WHITE);
		ticketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open that ticket
			}
		});
		ticketButton.setBounds(xbut, ybut, 400, 150);
		add(ticketButton);
	}

	/**
	 * Sets up the Log Out Button
	 */
	private void makeLogOutButton(){
		
		JButton logOutButton = new JButton("Log Out");
		logOutButton.setForeground(Color.WHITE);
		logOutButton.setBackground(Color.RED);
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wi.loggedOut=true;
			}
		});
		logOutButton.setBounds(1000, 0, 200, 30);
		add(logOutButton);
		
	}
	

}
