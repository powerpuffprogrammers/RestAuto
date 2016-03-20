package manager;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import messageController.Message;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerScreen extends JPanel {

	public ManagerInterface mi;
	
	private int lastMessageSelected;
	
	/**
	 * Create the panel.
	 * @param waiterInterface 
	 */
	public ManagerScreen(ManagerInterface MI) {
		lastMessageSelected=-1;
		mi = MI;
		//Set color to blue
		setBackground(new Color(51, 153, 255));
		//Array layout where you pick coordinates of each component
		setLayout(null);
		
		updateScreen();
	}

	private void makeNameText() {
		JTextField nameHeader;
		nameHeader = new JTextField();
		nameHeader.setEditable(false);
		nameHeader.setFont(new Font("Tahoma", Font.PLAIN, 14));
		nameHeader.setHorizontalAlignment(SwingConstants.CENTER);
		nameHeader.setText("Logged In As: "+ mi.name);
		nameHeader.setBounds(0, 0, 200, 30);
		add(nameHeader);
		nameHeader.setColumns(10);
		
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
				mi.loggedOut=true;
			}
		});
		logOutButton.setBounds(1000, 0, 200, 30);
		add(logOutButton);
		
	}

	/** 
	* Sets up the Delete Message Button
	 */
	private void makeDeleteButton(){
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setForeground(Color.WHITE);
		deleteButton.setBackground(Color.RED);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lastMessageSelected!=-1){
					mi.deleteMessage(lastMessageSelected);
				}
				lastMessageSelected=-1;
			}
		});
		deleteButton.setBounds(1000, 570, 200, 30);
		add(deleteButton);
		
	}

	/** 
	* Sets up the Create New Message Button
	 */
	private void makeNewMessageButton(){
		
		JButton newMess = new JButton("Create New Message");
		newMess.setForeground(Color.BLACK);
		newMess.setBackground(Color.WHITE);
		newMess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: bring up new screen for compose message
			}
		});
		newMess.setBounds(0, 570, 200, 30);
		add(newMess);
		
	}

	public void updateScreen() {
		removeAll();
		makeNameText();
		makeDeleteButton();
		makeNewMessageButton();
		makeLogOutButton();	
		makeListOfMessages();
		repaint();
		
	}

	/**
	 * prints the list of messages
	 */
	private void makeListOfMessages() {
		int size = mi.listOfMessages.size();
		for(int i =0 ; i< size; i++){
			int index = size-1 -i ;
			if(i>55){
				break;
			}
			Message m = mi.listOfMessages.get(index);
			//make a button for each message
			JButton newMess = new JButton(m.content);
			newMess.setForeground(Color.BLACK);
			newMess.setBackground(Color.WHITE);
			newMess.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lastMessageSelected=index;
				}
			});
			newMess.setBounds(100, 50+i*20, 1000, 20);
			add(newMess);
			
		}
		
	}
	

}
