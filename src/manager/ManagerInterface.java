package manager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import configuration.Configure;
import messageController.Message;
import messageController.SenderInfo;

public class ManagerInterface {
	
	private final static String MCdomainName = Configure.getDomainName("MessageController");
	private final static int MCportNumber = Configure.getPortNumber("MessageController");
	/**
	 * End of list is most recent
	 */
	public LinkedList<Message> listOfMessages;
	
	public ManagerMessageSender sender;
	
	private JFrame frame;
	
	/**
	 * Employee ID - this will be used to ID the tablet for the Message Controller
	 */
	long empID;
	
	/**
	 * Employee Name- this will be displayed on the screen
	 */
	String name;
	
	/**
	 * When this is true I return from constructor back to log in page
	 */
	public boolean loggedOut;
	
	ManagerScreen manScreen;
	
	public ManagerInterface(JFrame frame, long eID, String empName) {
		listOfMessages = new LinkedList<Message>();
		this.frame=frame;
		name=empName;
		empID=eID;
		loggedOut=false;
		
		//set up MC
		setUpMessageController();
		
		
		//create waiter screen for list of tickets
		manScreen = new ManagerScreen(this);
		//set the screen to the waiter panel
		this.frame.setContentPane(manScreen);
		frame.revalidate();
	}
	
	
	public void runUntilLogOut(){
		//Don't return until i logged out
		while(!loggedOut){
			System.out.println(loggedOut);
		}
	}

	/**
	* Updates the current panel - makes them redraw all the buttons
	 */
	public void updateScreen() {
		manScreen.updateScreen();
		frame.revalidate();
	}
	
	private void setUpMessageController() {
		Socket listener;
		try {
			listener = new Socket(MCdomainName, MCportNumber);
			Thread t= new ManagerMessageListener(listener,empID, this);
			t.start();
			sender = new ManagerMessageSender(listener,empID);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deleteMessage(int index){
		listOfMessages.remove(index);
		updateScreen();
	}
	
	public void sendMassNotification(String content){
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('h'), content));
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('c'), content));
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('s'), content));
	}


	public void addMessageToList(Message m) {
		listOfMessages.add(m);
		updateScreen();
		
	}
}
