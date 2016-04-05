package host;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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

/**
 * Controls the jpanels being displayed and all the data for the host.
 * The host interface keeps track of all of the tables in the restaurant.
 * @author cms549
 *
 */
public class HostInterface {
	
	//Used to get Message Controller info
	private final static String MCdomainName = Configure.getDomainName("MessageController");
	private final static int MCportNumber = Configure.getPortNumber("MessageController");
	
	/**
	 * Used to convert java objects to string and vice versa.
	 */
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
	public HashMap<Integer, Table> allTables; 
	
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
	 * HashMap that maps the name of the waiter to their ID.
	 */
	HashMap<String, Integer> listOfWaiters;
	
	
    /**
     * Constructor
     * @param frame - JFrame of the app 
     * @param eID - host's employee id
     * @param empName - host's employee name
     */
	public HostInterface(JFrame frame, long eID, String empName){
		name=empName;
		empID = eID;
		jsonConverter = new Gson();
		listOfWaiters = new HashMap<String,Integer>();
		if(!loadWaiters()){
			loggedOut=true;
			return;
		}
		
		
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
			if(key>5){
				table.changeStatus('p');
				paidTables.add(key);
			}
			else{
				table.changeStatus('r');
				readyTables.add(key);
			}
		}
		
		setUpMessageController();
		
		tableScreen = new HostTableScreen(this);
		frame.setContentPane(tableScreen);
		frame.revalidate();
	}

	/**
	 * Loads the list of tables from Data base B
	 * @return true on success, false on failure
	 */
	public boolean loadTables() {
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
			TableList tab = jsonConverter.fromJson(jtables, TableList.class);
			
			allTables = tab.hm;
			sock.close();
			
		} catch (Exception e) {
			System.out.println("ERROR: can't load tables");
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * Sets up the Message Controller and alerts it that waiter is logged in.
	 */
	private void setUpMessageController() {
		Socket listener=null;
		try {
			listener = new Socket(MCdomainName, MCportNumber);
			Thread t= new HostMessageListener(listener, this);
			t.start();
			sender = new HostMessageSender(listener,empID);
			sender.start();
			sender.sendMessage(new Message('L',-1, "Logging In"));
			
		} catch (Exception e) {
			System.out.println("Problem setting up Waiter MC.");
		}
		
	}
	
	/**
	 * Sends a notification to the manager
	 */
	public void notifyManager() {
		sender.sendMessage(new Message('m',-1, name+" needs help at host stand."));
		updateScreen();
	}

	/**
	 * Seat the table number with this server
	 * @param waiterName
	 * @param tableNumber
	 * @return 0 on success and -1 on fail.
	 */
	public int seat(String waiterName, int tableNumber){//CHANGE
		Table t = allTables.get(tableNumber);
		if (!(t.status=='r')){
			return -1;
		}
		t.status = 's';
		for(int i =0; i<readyTables.size(); i++){
			if(readyTables.get(i) == tableNumber){
				readyTables.remove(i);
			}
		}
		seatedTables.add(tableNumber);
		sendSeated(listOfWaiters.get(waiterName), tableNumber);
		try{
		updateScreen();
		}catch(Exception e){
			
		}
		return 0;////CHANGE
	}
	
	/**
	 * Sends a message to the waiter whos table you just sat.
	 * @param waiterId - id of waiter you wish to send message to
	 * @param tableNumber - table you just sat 
	 */
	public void sendSeated(long waiterId, int tableNumber){
		sender.sendMessage(new Message('w',waiterId, ""+tableNumber ));
	}
	
	/**
	 * Adds a notification on current screen by calling another method in panel
	 * @param content
	 */
	public void addNotification(String content) {
		tableScreen.makeNotification(content);
	}
	
	/**
	 * Keeps tablet in host interface screen until it logs out.
	 * Then sends log out message to MC.
	 */
	public void runUntilLogOut(){
		//Don't return until i logged out
		while(!loggedOut){
			System.out.print(loggedOut);
		}
		sender.sendMessage(new Message('X',-1, "Log out"));
	}

	/**
	 * Updates the current panel - makes them redraw all the buttons
	 */
	public void updateScreen() {
		tableScreen.updateScreen();
		frame.revalidate();
	}

	/**
	 * Moves seated table to paid
	 * @param tableNumber - table number that paid
	 */
	public void paid(int tableNumber) {
		Table t = allTables.get(tableNumber);
		t.status = 'p';
		for(int i=0; i<seatedTables.size();i++){
			int curr = seatedTables.get(i);
			if(curr == tableNumber){
				seatedTables.remove(i);
				paidTables.add(tableNumber);
			}
		}
		updateScreen();
		
	}

	/**
	 * Move a table that was just cleaned from paid into ready list
	 * @param tableNumber
	 */
	public void cleaned(int tableNumber) {
		Table t = allTables.get(tableNumber);
		t.status = 'r';
		for(int i=0; i<paidTables.size();i++){
			int curr = paidTables.get(i);
			if(curr == tableNumber){
				paidTables.remove(i);
				readyTables.add(tableNumber);
			}
		}
		
		updateScreen();
		
	}

	/**
	 * Loads waiters and ids from database A.
	 * @return true on success, false on failure
	 */
	public boolean loadWaiters() {
		String DAhost = Configure.getDomainName("DatabaseAController");
		int DAPortNum = Configure.getPortNumber("DatabaseAController");
		Socket sock=null;
		try {
			sock = new Socket(DAhost, DAPortNum);
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			String requestWaiters = "W";
			out.writeUTF(requestWaiters);
			String waiterAsString = in.readUTF();
			String[] waiter = waiterAsString.split(":");
			for(int i=0; i<waiter.length;i++){
				String idString = waiter[i].substring(0, waiter[i].indexOf(","));
				int id = Integer.parseInt(idString);
				String name = waiter[i].substring(waiter[i].indexOf(",")+1);
				listOfWaiters.put(name,id);
				
			}
			
			
			sock.close();

		} catch (Exception e) {
			System.out.println("ERROR: can't get waiter list");
			return false;
		}
		return true;
		
	}
	
}
