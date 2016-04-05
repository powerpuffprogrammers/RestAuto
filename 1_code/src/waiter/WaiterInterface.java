package waiter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

import com.google.gson.Gson;

import configuration.Configure;
import dataBaseC.Dish;
import dataBaseC.Menu;
import dataBaseC.Ticket;
import messageController.Message;

/**
 * Controls what screen the waiter sees. 
 * Holds the waiters current tickets.
 * @author cms549
 *
 */
public class WaiterInterface {
	
	private final static String MCdomainName = Configure.getDomainName("MessageController");
	private final static int MCportNumber = Configure.getPortNumber("MessageController");
	
	private Gson jsonConverter;
	/**
	 * Sends messages to the Message controller
	 */
	public WaiterMessageSender sender;
	/**
	 * Sends messages to DB C - used for ticket auditing.
	 */
	public DataBaseCSender DBCSender;
	
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
	 * When this is true I return from constructor back to log in page
	 */
	public boolean loggedOut;
	
	/**
	 * current ticket open, null if none is open
	 */
	public Ticket currTicket;
	
	/**
	 * List of the tickets this waiter is in charge of.
	 * Maps the table number to the appropriate tickets.
	 */
	HashMap<Integer, Ticket> listOfTickets;
	
	/**
	 * Menu of dishes.
	 */
	Menu menu;
	
	WaiterTickListScreen ticketListScreen;
	public WaiterOneTicketScreen oneTickScreen;
	
	/**
	 * Constructor
	 * @param frame - frame that will be used by waiter app
	 * @param eID - waiter's unique id 
	 * @param empName = waiter's name
	 */
	public WaiterInterface(JFrame frame, long eID, String empName) {
		jsonConverter = new Gson();
		this.frame=frame;
		name=empName;
		listOfTickets = new HashMap<Integer, Ticket>();
		empID=eID;
		loggedOut=false;
		
		//if problem loading menu return right away
		if (!loadMenu()){
			loggedOut=true;
			return;
		}
		
		//set up MC
		setUpMessageController();
		
		//generateTickets();
		
		//create waiter screen for list of tickets
		ticketListScreen = new WaiterTickListScreen(this);
		//set the screen to the waiter panel
		frame.setContentPane(ticketListScreen);
		frame.revalidate();
		
		oneTickScreen = new WaiterOneTicketScreen(this);
	}
	
	/**
	 * Returns upon logging out. 
	 * Sends a message to the Host and MC to notify that waiter is logging out.
	 */
	public void runUntilLogOut(){
		//Don't return until i logged out
		while(!loggedOut){
			System.out.print(loggedOut);
		}
		//let the host know you are logging out
		try {
			DBCSender.sock.close();
		} catch (IOException e) {}
		sender.sendMessage(new Message('X',-1, "Log out"));
	}

	/**
	 * Loads menu from database C.
	 * @return true on success, false on failure
	 */
	public boolean loadMenu() {
		String DChost = Configure.getDomainName("DatabaseCController");
		int DCPortNum = Configure.getPortNumber("DatabaseCController");
		Socket sock=null;
		try {
			sock = new Socket(DChost, DCPortNum);
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			String logInToMC = "M";
			out.writeUTF(logInToMC);
			String jmenu = in.readUTF();
			menu = jsonConverter.fromJson(jmenu, Menu.class);
			DBCSender = new DataBaseCSender(sock, out);
			DBCSender.start();
			new DataBaseCListener( in, this).start();

		} catch (Exception e) {
			System.out.println("ERROR: can't load menu");
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
			Thread t= new WaiterMessageListener(listener, this);
			t.start();
			sender = new WaiterMessageSender(listener,empID);
			sender.start();
			sender.sendMessage(new Message('L',-1, "Logging In"));
			
		} catch (Exception e) {
			System.out.println("Problem setting up Waiter MC.");
		}
		
	}

	/**
	 * Adds a the given dish to the ticket that is currently selected.
	 * Caller should make sure currTicket field is not null.
	 * @param dish - Dish of dish you wish to add
	 */
	public boolean addDishToTicket(Dish dish) {
		currTicket.addDishToTicket(dish.makeCopyOfDish());
	     updateScreen();
		for(int i = 0; i < currTicket.listOfDishes.size() ; i++){
			if(dish.name.equals(currTicket.listOfDishes.get(i).name)){
				return true;
			}
		}
		return false;
		
	}

	/**
	 * Removes a the dish at the given index on the ticket that is currently selected.
	 * Caller should make sure currTicket field is not null and index is valid.
	 * @param indexInTicket = index of the dish in the current ticket
	 */
	public void removeDishFromTicket(int indexInTicket) {
		currTicket.removeDishFromTicket(indexInTicket);
		updateScreen();
	}

	/**
	 * Switches from list of tickets screen to one open ticket screen
	 * @param tableNumber - the table number of ticket you wish to open
	 */
	public void openTicketScreens(int tableNumber) {
		currTicket = listOfTickets.get(tableNumber);
		oneTickScreen.setTicket(currTicket);
		frame.setContentPane(oneTickScreen);
		frame.revalidate();
		
	}
	
	/**
	 * Switches the screen from an open ticket screen to the list of tickets screen.
	 */
	public void backToMainScreen(){
		currTicket = null;
		frame.setContentPane(ticketListScreen);
		ticketListScreen.updateScreen();
		frame.revalidate();
	}

	/**
	 * Sends a notification to the Manager that a certain table needs help
	 * @param currTicket2
	 */
	public void notifyManager(Ticket currTicket2) {
		sender.sendMessage(new Message('m',-1, currTicket2.waiterName+" needs help at table "+currTicket2.tableNumber+"."));
		updateScreen();
	}
	
	/**
	 * Sends ticket to Chef
	 * @param t - ticket to send
	 */
	public void sendTicket(Ticket t){
		sender.sendMessage(new Message('c',-1,jsonConverter.toJson(t) ));
	}
	
	/**
	 * Used for testing
	 */
	public void generateTickets(){	
		Ticket T1=new Ticket(name,1,empID);//table 1, waiter id=1
		Ticket T2=new Ticket( name ,14,empID);//table 14, waiter id=1
		if(empID ==0){
			T1.recentlySat=true;
		}else{
			T2.hotFood=true;
		}
		listOfTickets.put(1,T1);
		listOfTickets.put(14,T2);
	}

	
	/**
	 * Adds a notification on current screen by calling another method in panel
	 * @param content - content to post on the screen
	 */
	public void addNotification(String content) {
		if(currTicket==null){
			ticketListScreen.makeNotification(content);
		}
		else{
			oneTickScreen.makeNotification(content);
		}
	}


	/**
	 * Updates the current panel - makes them redraw all the buttons
	 */
	public void updateScreen() {
		if(currTicket==null){
			ticketListScreen.updateScreen();
		}
		else{
			oneTickScreen.updateScreen();
		}
		frame.revalidate();
	}

	/**
	 * This is called when a table has paid and server clicks the paid button.
	 * It alerts host and take this ticket off the list.
	 * @param tableNumber - table number of table that just paid
	 */
	public void paid(int tableNumber) {
		sender.sendMessage(new Message('h',-1, ""+tableNumber));
		DBCSender.sendTicket(listOfTickets.get(tableNumber));
		listOfTickets.remove(tableNumber);
		backToMainScreen();
	}

/**
 * This is called when low inventory is met and chef can no longer make these dishes.
 * Removes the array of dish names from the menu.
 * @param dishes - array of dish names to be removed from menu.
 */
	public void removeLowInventoryDishes(String[] dishes) {
		//for each dish that we need to mark as low inventory
		for(int i =0; i<dishes.length;i++){
			String dishName = dishes[i];
			Iterator<String> it = menu.menu.keySet().iterator();
			//check each sub category for that dish
			while(it.hasNext()){
				String category = it.next();
				HashMap<String, Dish> hm = menu.menu.get(category);
				if(hm.containsKey(dishName)){
					hm.remove(dishName);
					break;
				}
			}
		}
		updateScreen();
		
	}

	
}
