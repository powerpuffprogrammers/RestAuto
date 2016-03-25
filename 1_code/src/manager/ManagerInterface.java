package manager;

import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JFrame;
import configuration.Configure;
import messageController.Message;
import messageController.SenderInfo;

/**
 * Holds the list of messages that all of the employees sent the manager.
 * Controls this list of messages.
 * @author cms549
 *
 */
public class ManagerInterface {
	
	private final static String MCdomainName = Configure.getDomainName("MessageController");
	private final static int MCportNumber = Configure.getPortNumber("MessageController");
	
	/**
	 * List of messages that manager has recieved
	 * End of list is most recent
	 */
	public LinkedList<Message> listOfMessages;
	
	/**
	 * This will be used to send messages to the MC
	 */
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
	
	/**
	 * Constructor
	 * @param frame - frame to be used by this application
	 * @param eID - manager's employee id
	 * @param empName - manager's name
	 */
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
	
	/**
	 * Returns when manager logs out.
	 * Sends a message to the MC to alert it that the manager is logging out.
	 */
	public void runUntilLogOut(){
		//Don't return until i logged out
		while(!loggedOut){
			System.out.print(loggedOut);
		}
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('X'), ""));
	}

	/**
	* Updates the current panel - makes them redraw all the buttons
	 */
	public void updateScreen() {
		manScreen.updateScreen();
		frame.revalidate();
	}
	
	/**
	 * Sets up the socket to the MC
	 */
	private void setUpMessageController() {
		Socket listener;
		try {
			listener = new Socket(MCdomainName, MCportNumber);
			Thread t= new ManagerMessageListener(listener,empID, this);
			t.start();
			sender = new ManagerMessageSender(listener,empID);
			sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('L'), ""));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Delete's the message at the given index in the message list
	 * Caller should be sure to check the index is valid.
	 * @param index - index of message to be deleted in the list of messages
	 */
	public void deleteMessage(int index){
		listOfMessages.remove(index);
		updateScreen();
	}
	
	/**
	 * Sends a mass notification to all servers, hosts, and chefs
	 * @param content
	 */
	public void sendMassNotification(String content){
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('h',-1), content));
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('c',-1), content));
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('s',-1), content));
	}

	/**
	 * Adds this message to the list of messages
	 * @param m - message to be added
	 */
	public void addMessageToList(Message m) {
		if(m==null){
			return;
		}
		listOfMessages.add(m);
		updateScreen();
		
	}
}
