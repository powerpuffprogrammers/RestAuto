package waiter;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dataBaseC.Ticket;

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
		
		updateScreen();
	}

	public void updateScreen() {
		removeAll();
		makeNameText();
		makeLogOutButton();	
		makeTicketButtons();
		repaint();
	}

	/**
	 * writes the waiter's name at the top left
	 */
	private void makeNameText() {
		JTextField nameHeader;
		nameHeader = new JTextField("Logged In As: "+ wi.name);
		nameHeader.setEditable(false);
		nameHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameHeader.setHorizontalAlignment(SwingConstants.CENTER);
		nameHeader.setBounds(0, 0, 300, 30);
		add(nameHeader);
		
	}

	/**
	 * Looks through the ticket list and generates buttons for this
	 */
	private void makeTicketButtons() {
		int index =0;
		Iterator<Integer> keyset = wi.listOfTickets.keySet().iterator();
		while(keyset.hasNext() && index<4){
			int key = keyset.next();
			makeTicketButton(wi.listOfTickets.get(key),index);
			index++;
		}
	}

	private void makeTicketButton(Ticket t, int index) {
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
		JButton ticketButton = new JButton("#"+t.tableNumber);
		ticketButton.setFont(new Font("Tahoma", Font.PLAIN, 36));
		ticketButton.setForeground(Color.BLACK);
		ticketButton.setBackground(Color.WHITE);
		ticketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open that ticket
				wi.openTicketScreens(t.tableNumber);
			}
		});
		ticketButton.setBounds(xbut, ybut, 400, 150);
		add(ticketButton);
		
		if(t.hotFood){
			JTextField nameHeader;
			nameHeader = new JTextField();
			nameHeader.setEditable(false);
			nameHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
			nameHeader.setHorizontalAlignment(SwingConstants.CENTER);
			nameHeader.setText("HOT FOOD");
			nameHeader.setBounds(xbut+10, ybut+20, 200, 30);
			add(nameHeader);
			nameHeader.setColumns(10);	
		}
		else if (t.recentlySat){
			JTextField nameHeader;
			nameHeader = new JTextField();
			nameHeader.setEditable(false);
			nameHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
			nameHeader.setHorizontalAlignment(SwingConstants.CENTER);
			nameHeader.setText("HOT FOOD");
			nameHeader.setBounds(xbut+10, ybut+20, 200, 30);
			add(nameHeader);
			nameHeader.setColumns(10);	
		}
		
		
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
				makeAreYouSure();
			}
		});
		logOutButton.setBounds(1000, 0, 200, 30);
		add(logOutButton);
		
	}
	

	/**
	 * Creates an are you sure message box
	 */
	private void makeAreYouSure() {
		//Make a White box with "Are you sure"
		JTextField areYouSure;
		areYouSure = new JTextField("Are you sure you want to log out?");
		areYouSure.setEditable(false);
		areYouSure.setFont(new Font("Tahoma", Font.PLAIN, 16));
		areYouSure.setHorizontalAlignment(SwingConstants.CENTER);
		areYouSure.setBackground(Color.ORANGE);
		areYouSure.setBounds(200, 100, 800, 300);
		add(areYouSure);
		setComponentZOrder(areYouSure, 0);
		
		
		//Make yes button
		JButton yes = new JButton("YES");
		yes.setForeground(Color.BLACK);
		yes.setBackground(Color.GREEN);
		yes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wi.loggedOut=true;
			}
		});
		yes.setBounds(100,100, 200, 30);
		areYouSure.add(yes);
		
		//Make no button
		JButton no = new JButton("NO");
		no.setForeground(Color.BLACK);
		no.setBackground(Color.RED);
		no.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateScreen();
			}
		});
		no.setBounds(450,100, 200, 30);
		areYouSure.add(no);
		repaint();
	}
	
	

	/** makes a notification button on top of screen like banner
	 * once it is clicked it closes it
	 * @param content
	 */
	public void makeNotification(String content) {
		JButton notificationButton = new JButton(content);
		notificationButton.setForeground(Color.BLACK);
		notificationButton.setBackground(Color.WHITE);
		notificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(notificationButton);
				updateScreen();
			}
		});
		notificationButton.setBounds(0, 0, 1200, 30);
		add(notificationButton,0);
		repaint();
		
	}
	

}
