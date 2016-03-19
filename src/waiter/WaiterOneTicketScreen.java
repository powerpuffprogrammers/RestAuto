package waiter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import databaseB.Ticket;

public class WaiterOneTicketScreen extends JPanel {

	public WaiterInterface wi;
	/**
	 * current ticket open, null if none is open
	 */
	public Ticket currTicket;
	
	/**
	 * Can be Apps, Entrees, Drinks, Desserts
	 */
	public String currTabOpen;
	
	public WaiterOneTicketScreen(WaiterInterface waiterInterface) {
		wi = waiterInterface;
		//Set color to blue
		setBackground(new Color(51, 153, 255));
		//Array layout where you pick coordinates of each component
		setLayout(null);
	}

	private void makeMenuChoices() {
		// TODO Auto-generated method stub
		
	}

	private void makeTicketOnLeft() {
		// TODO Auto-generated method stub
		
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
	 * Sets up the Back Button
	 */
	private void makeBackButton(){
		
		JButton logOutButton = new JButton("Back");
		logOutButton.setForeground(Color.WHITE);
		logOutButton.setBackground(Color.RED);
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wi.backToMainScreen();
			}
		});
		logOutButton.setBounds(1000, 0, 200, 30);
		add(logOutButton);
		
	}
	
	/**
	 * Sets up the notifyManager Button
	 */
	private void makeNotifyManagerButton(){
		
		JButton notifyManager = new JButton("Notify Manager");
		notifyManager.setForeground(Color.BLACK);
		notifyManager.setBackground(Color.ORANGE);
		notifyManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wi.notifyManager(currTicket);
			}
		});
		notifyManager.setBounds(500,570, 300, 30);
		add(notifyManager);
		
	}
	
	/**
	 * Sets up the sendTicket Button
	 */
	private void makeSendTicketButton(){
		
		JButton sendButton = new JButton("Send to Chef");
		sendButton.setForeground(Color.WHITE);
		sendButton.setBackground(Color.GREEN);
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wi.sendTicket(currTicket);
			}
		});
		sendButton.setBounds(0,570, 300, 30);
		add(sendButton);
		
	}
	
	public void setTicket(Ticket t){
		currTicket = t;
		currTicket.recentlySat=false;
		currTicket.hotFood=false;
		makeNameText();
		makeBackButton();
		makeNotifyManagerButton();
		makeSendTicketButton();
		makeTicketOnLeft();
		makeMenuChoices();
		
	}
}
