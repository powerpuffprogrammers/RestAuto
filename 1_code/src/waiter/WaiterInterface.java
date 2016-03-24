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
import messageController.SenderInfo;

public class WaiterInterface {
	
	private final static String MCdomainName = Configure.getDomainName("MessageController");
	private final static int MCportNumber = Configure.getPortNumber("MessageController");
	
	private Gson jsonConverter;
	public WaiterMessageSender sender;
	
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
	Ticket currTicket;
	
	HashMap<Integer, Ticket> listOfTickets;
	
	Menu menu;
	
	WaiterTickListScreen ticketListScreen;
	WaiterOneTicketScreen oneTickScreen;
	
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
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('h'), "L"+name));
		
		generateTickets();
		
		//create waiter screen for list of tickets
		ticketListScreen = new WaiterTickListScreen(this);
		//set the screen to the waiter panel
		frame.setContentPane(ticketListScreen);
		frame.revalidate();
		
		oneTickScreen = new WaiterOneTicketScreen(this);
	}
	
	
	public void runUntilLogOut(){
		//Don't return until i logged out
		while(!loggedOut){
			System.out.print(loggedOut);
		}
		//let the host know you are logging out
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('h'), "O"+name));
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('X'), ""));
	}

	private boolean loadMenu() {
		String DBhost = Configure.getDomainName("DatabaseCController");
		int DBPortNum = Configure.getPortNumber("DatabaseCController");
		Socket sock=null;
		try {
			sock = new Socket(DBhost, DBPortNum);
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			String logInToMC = "M";
			out.writeUTF(logInToMC);
			String jmenu = in.readUTF();
			menu = jsonConverter.fromJson(jmenu, Menu.class);
			sock.close();
			
		} catch (Exception e) {
			System.out.println("ERROR: can't load menu");
			return false;
		}
		return true;
		
	}
		
	private void setUpMessageController() {
		Socket listener=null;
		try {
			listener = new Socket(MCdomainName, MCportNumber);
			Thread t= new WaiterMessageListener(listener,empID, this);
			t.start();
			sender = new WaiterMessageSender(listener,empID);
			sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('L'), ""));
			
		} catch (Exception e) {
			System.out.println("Server: Disconnected from MC.");
			try {
				listener.close();
			} catch (IOException e1) {}
		}
		
	}

	public void addDishToTicket(Dish dish) {
		currTicket.addDishToTicket(dish.makeCopyOfDish());
		updateScreen();
	}

	public void removeDishFromTicket(int indexInTicket) {
		currTicket.removeDishFromTicket(indexInTicket);
		updateScreen();
	}

	
	public void openTicketScreens(int ticketNumber) {
		currTicket = listOfTickets.get(ticketNumber);
		oneTickScreen.setTicket(currTicket);
		frame.setContentPane(oneTickScreen);
		frame.revalidate();
		
	}
	
	public void backToMainScreen(){
		currTicket = null;
		frame.setContentPane(ticketListScreen);
		ticketListScreen.updateScreen();
		frame.revalidate();
	}

	public void notifyManager(Ticket currTicket2) {
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('m'), currTicket2.waiterName+" needs help at table "+currTicket2.tableNumber+"."));
		updateScreen();
	}

	public void sendTicket(Ticket t){
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('c'),jsonConverter.toJson(t) ));
	}
	
	//By Athira
	public void generateTickets(){
			Ticket T1=new Ticket(name,2,empID);//table 2, waiter id=1
			Ticket T2=new Ticket( name ,14,empID);//table 14, waiter id=1
			Ticket T3=new Ticket(name ,18,empID);//table 18, waiter id=1
			
			listOfTickets.put(2,T1);
			listOfTickets.put(14,T2);
			listOfTickets.put(18,T3);
	}

	
	/**
	 * Adds a notification on current screen by calling another method in panel
	 * @param content
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
	 * Alert host and take this ticket off the list
	 * @param tableNumber
	 */
	public void paid(int tableNumber) {
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('h'), ""+tableNumber));
		listOfTickets.remove(tableNumber);
		backToMainScreen();
	}

/**
 * Removes the array of dish names from the menu
 * @param dishes
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
