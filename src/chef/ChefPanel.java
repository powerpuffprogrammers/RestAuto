package chef;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import chef.ChefInterface;
import dataBaseC.Ticket;
import waiter.WaiterInterface;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChefPanel extends JPanel {

	public ChefInterface ci;
	
	/**
	 * Create the panel.
	 * @param chefInterface 
	 */
	public ChefPanel(ChefInterface chefInterface) {
		ci = chefInterface;
		setBackground(SystemColor.textHighlight);
		
		makeLogOutButton();	
		makeNotifyManagerButton();
		makeNameText();
		//displayTickets();
	}

	private void displayTickets() {
		int amtleft = 4;
		int size = ci.ticketQueueFinished.size();
		for(int i=0;i<size && amtleft>0;i++){
			Long tickNum = ci.ticketQueueFinished.get(i);
			Ticket t =ci.ticketLookup.get(tickNum);
			displayTicket(4-amtleft, t);
			amtleft--;
		}
		if(amtleft>0){
			size = ci.ticketQueueStarted.size();
			for(int i=0;i<size && amtleft>0;i++){
				Long tickNum = ci.ticketQueueStarted.get(i);
				Ticket t =ci.ticketLookup.get(tickNum);
				displayTicket(4-amtleft, t);
				amtleft--;
			}
		}
		if(amtleft>0){
			size = ci.ticketQueuesemiStarted.size();
			for(int i=0;i<size && amtleft>0;i++){
				Long tickNum = ci.ticketQueuesemiStarted.get(i);
				Ticket t =ci.ticketLookup.get(tickNum);
				displayTicket(4-amtleft, t);
				amtleft--;
			}
		}
		if(amtleft>0){
			size = ci.ticketQueueUnstarted.size();
			for(int i=0;i<size && amtleft>0;i++){
				Long tickNum = ci.ticketQueueUnstarted.get(i);
				Ticket t =ci.ticketLookup.get(tickNum);
				displayTicket(4-amtleft, t);
				amtleft--;
			}
		}
		
	}

	
	/**
	 * displays the ticket on the screen in index index
	 * @param index
	 * @param t
	 */
	private void displayTicket(int index, Ticket t) {
		int xbutton, xwords;
		if(index==0){
			xbutton = 100;
			xwords = 120;
		}
		else if(index==1){
			xbutton = 400;
			xwords = 420;
		}
		else if(index==2){
			xbutton = 700;
			xwords = 720;
		}
		else{
			xbutton = 1000;
			xwords = 1020;
		}
		
		//draw the rectangle as a button
		JButton tableBut = new JButton("");
		tableBut.setForeground(Color.WHITE);
		tableBut.setBackground(Color.RED);
		tableBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//open up new screen
			}
		});
		tableBut.setBounds(xbutton, 100, 200, 400);
		add(tableBut);
		
		//write table number
		JTextField tableNumber;
		tableNumber = new JTextField();
		tableNumber.setEditable(false);
		tableNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tableNumber.setHorizontalAlignment(SwingConstants.CENTER);
		tableNumber.setText("Table Number: "+ t.tableNumber);
		tableNumber.setBounds(xwords, 120, 250, 30);
		add(tableNumber);
		tableNumber.setColumns(10);
		
		//status of ticket
		JTextField status;
		status = new JTextField();
		status.setEditable(false);
		status.setFont(new Font("Tahoma", Font.PLAIN, 14));
		status.setHorizontalAlignment(SwingConstants.CENTER);
		status.setText("Status: "+ getStatus(t.status));
		status.setBounds(xwords, 450, 250, 30);
		add(status);
		status.setColumns(10);
	}

	private String getStatus(char status) {
		if(status =='u'){
			return "Unstarted";
		}
		else if(status =='s'){
			return "Semi-Started";
		}
		else if(status =='S'){
			return "Started";
		}
		else if(status =='f'){
			return "Finished";
		}
		return null;
	}

	/**
	 * Sets up the Notify Manager Button
	 */
	private void makeNotifyManagerButton() {
		JButton makeNotifyManager = new JButton("Notify Manager");
		makeNotifyManager.setForeground(Color.BLACK);
		makeNotifyManager.setBackground(Color.ORANGE);
		makeNotifyManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ci.sendManagerMessage();
			}
		});
		makeNotifyManager.setBounds(1000, 0, 200, 30);
		add(makeNotifyManager);
		
		
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
				ci.loggedOut=true;
			}
		});
		logOutButton.setBounds(1000, 0, 200, 30);
		add(logOutButton);
		
	}
	
	/**
	 * Makes the name header at the top left 
	 */
	private void makeNameText() {
		JTextField nameHeader;
		nameHeader = new JTextField();
		nameHeader.setEditable(false);
		nameHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameHeader.setHorizontalAlignment(SwingConstants.CENTER);
		nameHeader.setText("Logged In As: "+ ci.name);
		nameHeader.setBounds(0, 0, 200, 30);
		add(nameHeader);
		nameHeader.setColumns(10);
		
	}
	
}
