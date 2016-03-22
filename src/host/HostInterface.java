package host;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

import com.google.gson.Gson;

import configuration.Configure;
import databaseB.Table;
import databaseB.TableList;
import messageController.Message;
import messageController.SenderInfo;

/**
 * Controls the jpanels being displayed and all the data for the host.
 * @author cms549
 *
 */
public class HostInterface {
	
	//Used to get Message Controller info
	private final static String MCdomainName = Configure.getDomainName("MessageController");
	private final static int MCportNumber = Configure.getPortNumber("MessageController");
	
	private Gson jsonConverter;
	/**
	 * Used to send messages to message controller
	 */
	public HostMessageSender sender;
	
	JFrame frame;
	
	/**
	 * Employee ID - this will be used to ID the tablet for the Message Controller
	 */
	long empID;
	
	/**
	 * Employee Name- this will be displayed on the screen
	 */
	String name;
	
	/**
	 * When this is true it returns from back to log in page
	 */
	public boolean loggedOut;
	
	/**
	 * J panel for the host that displays tables
	 */
	HostTableScreen tableScreen;
	
	/**
	 * Hash map links integer (table #) to its table object - holds all the tables in the restaurant
	 */
	HashMap<Integer, Table> allTables; 
	
	/**
	 * list of all the ready tables in order they will be displayed
	 */
	ArrayList< Integer> readyTables; 
	/**
	 * list of tables that are seated 
	 */
	ArrayList< Integer> seatedTables;
	/**
	 * list of tables that need to be cleaned
	 */
	ArrayList< Integer> paidTables;
	
    /**
     * Constructor
     * @param frame - JFrame of the app 
     * @param eID - host's employee id
     * @param empName - host's employee name
     */
	public HostInterface(JFrame frame, long eID, String empName){
		name=empName;
		empID = eID;
		//if problem loading menu return right away
		if (!loadTables()){
			loggedOut=true;
			return;
		}
		readyTables = new ArrayList<Integer>();
		seatedTables = new ArrayList<Integer>();
		paidTables = new ArrayList<Integer>();
		
		Iterator<Integer> it = allTables.keySet().iterator();
		while( it.hasNext()){
			Integer key= it.next();
			Table table = allTables.get(key);
			table.changeStatus('r');
			readyTables.add(key);
		}
		
		setUpMessageController();
		
		tableScreen = new HostTableScreen(this);
	}

	/**
	 * Loads the list of tables from Data base B
	 * @return true on success, false on failure
	 */
	private boolean loadTables() {
		String DBhost = Configure.getDomainName("DatabaseBController");
		int DBPortNum = Configure.getPortNumber("DatabaseBController");
		Socket sock=null;
		try {
			sock = new Socket(DBhost, DBPortNum);
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			String logInToMC = "T";
			out.writeUTF(logInToMC);
			String jtables = in.readUTF();
			allTables = jsonConverter.fromJson(jtables, TableList.class).hm;
			sock.close();
			
		} catch (Exception e) {
			System.out.println("ERROR: can't load menu");
			return false;
		}
		return true;

	}

	/**
	 * Sets up the threads to communicate with the Message Controller 
	 */
	private void setUpMessageController() {
		Socket listener=null;
		try {
			listener = new Socket(MCdomainName, MCportNumber);
			Thread t= new HostMessageListener(listener,empID, this);
			t.start();
			sender = new HostMessageSender(listener,empID);
			
			
		} catch (Exception e) {
			System.out.println("Host: Disconnected from MC.");
			try {
				listener.close();
			} catch (IOException e1) {}
		}
		
	}
	
	/**
	 * Sends a notification to the manager
	 */
	public void notifyManager() {
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('m'), name+" needs help at host stand."));
	}

	/**
	 * Sends a message to the waiter whos table you just sat.
	 * @param waiterId - id of waiter you wish to send message to
	 * @param tableNumber - table you just sat 
	 */
	public void sendSeated(long waiterId, int tableNumber){
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('w',waiterId), ""+tableNumber ));
	}
	
	/**
	 * Adds a notification on current screen by calling another method in panel
	 * @param content
	 */
	public void addNotification(String content) {
		tableScreen.makeNotification(content);
	}
	
	/**
	 * keeps tablet in host interface screen until it logs out
	 */
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
		tableScreen.updateScreen();
		frame.revalidate();
	}

	/**
	 * moves seated table to paid
	 * @param tableNumber
	 */
	public void paid(int tableNumber) {
		for(int i=0; i<seatedTables.size();i++){
			int curr = seatedTables.get(i);
			if(curr == tableNumber){
				seatedTables.remove(i);
				paidTables.add(tableNumber);
			}
		}
		updateScreen();
		
	}

	
	
}
