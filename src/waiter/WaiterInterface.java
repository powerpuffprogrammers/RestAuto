package waiter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;
import com.google.gson.Gson;
import configuration.Configure;
import databaseB.Dish;
import databaseB.Menu;
import databaseB.Ticket;
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
		
		//load the menu
		loadMenu();
		
		//set up MC
		setUpMessageController();
		
		generateTickets(true);
		
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
			System.out.println(loggedOut);
		}
	}

	private void loadMenu() {
		String DBBhost = Configure.getDomainName("DatabaseBController");
		int DBBPortNum = Configure.getPortNumber("DatabaseBController");
		Socket sock;
		try {
			sock = new Socket(DBBhost, DBBPortNum);
			DataInputStream in = new DataInputStream(sock.getInputStream());
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			//send a message wempID to MC so they sign you in
			String logInToMC = "M";
			out.writeUTF(logInToMC);
			String jmenu = in.readUTF();
			menu = jsonConverter.fromJson(jmenu, Menu.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	private void setUpMessageController() {
		Socket listener;
		try {
			listener = new Socket(MCdomainName, MCportNumber);
			Thread t= new WaiterMessageListener(listener,empID, this);
			t.start();
			sender = new WaiterMessageSender(listener,empID);
			
			
		} catch (Exception e) {
			e.printStackTrace();
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
	}

	public void sendTicket(Ticket t){
		sender.sendMessage(new Message(new SenderInfo(), new SenderInfo('c'),jsonConverter.toJson(t) ));
	}
	//By Athira
	public void generateTickets(boolean emptyTickets){
		if(emptyTickets){
			Ticket T1=new Ticket(name,2,1,new ArrayList<Dish>());//table 2, waiter id=1
			Ticket T2=new Ticket( name ,14,1,new ArrayList<Dish>());//table 14, waiter id=1
			Ticket T3=new Ticket(name ,18,1,new ArrayList<Dish>());//table 18, waiter id=1
			
			listOfTickets.put(2,T1);
			listOfTickets.put(14,T2);
			listOfTickets.put(18,T3);
			return;
		}
		
			Dish a0 =  new Dish("Breadsticks",5.99, "Appetizer");
				Dish a1 =  new Dish("Buffalo Wings",6.99, "Appetizer");
				Dish a2 =  new Dish("Spiced Olives",5.99, "Appetizer");
				Dish a3 =  new Dish("Chips and Guacamole",7.99, "Appetizer");
				
				Dish e0 =  new Dish("Eggplant Parmesan",11.99, "Entree");
				Dish e1 =  new Dish("Pasta",8.99, "Entree");
				Dish e2 =  new Dish("Pizza",7.99, "Entree");
				Dish e3 =  new Dish("Chicken Salad",12.99, "Entree");
				
				Dish de0 =  new Dish("Tiramasu",13.99, "Dessert");
				Dish de1 =  new Dish("Ice Cream",2.99, "Dessert");
				Dish de2 =  new Dish("Chocolate Cake",6.99, "Dessert");
				Dish de3 =  new Dish("Pecan Tart",4.99, "Dessert");
				
				Dish dr0=new Dish("Water", 0.00, "Drink");
				Dish dr1=new Dish("Coke", 1.99, "Drink");
				Dish dr2=new Dish("Pepsi", 1.99, "Drink");
				Dish dr3=new Dish("Milk", 1.49, "Drink");//for chrissy :)
				
				ArrayList<Dish> appetizers= new ArrayList<Dish>();
				ArrayList<Dish> entree=new ArrayList<Dish>();
				ArrayList<Dish> dessert=new ArrayList<Dish>();
				ArrayList<Dish> drinks=new ArrayList<Dish>();
				
				appetizers.add(a0);appetizers.add(a1);appetizers.add(a2);appetizers.add(a3);
				
				entree.add(e0);entree.add(e1);entree.add(e2);entree.add(e3);
				
				dessert.add(de0);dessert.add(de1);dessert.add(de2);dessert.add(de3);
				
				drinks.add(dr0);drinks.add(dr1);drinks.add(dr2);drinks.add(dr3);
				
				//now we can make so many combinations!! 
				
				ArrayList<Dish> dishlistT1= new ArrayList<Dish>();
				dishlistT1.add(appetizers.get(0));
				dishlistT1.add(appetizers.get(2));
				dishlistT1.add(entree.get(1));
				dishlistT1.add(entree.get(0));
				dishlistT1.add(dessert.get(0));
				dishlistT1.add(drinks.get(2));
				dishlistT1.add(drinks.get(0));
				
				ArrayList<Dish> dishlistT2= new ArrayList<Dish>();
				dishlistT2.add(appetizers.get(0));
				dishlistT2.add(appetizers.get(0));
				dishlistT2.add(entree.get(2));
				dishlistT2.add(entree.get(3));
				dishlistT2.add(dessert.get(2));
				dishlistT2.add(drinks.get(1));
				dishlistT2.add(drinks.get(3));
				
				ArrayList<Dish> dishlistT3= new ArrayList<Dish>();
				dishlistT3.add(appetizers.get(3));
				dishlistT3.add(appetizers.get(2));
				dishlistT3.add(entree.get(0));
				dishlistT3.add(entree.get(2));
				dishlistT3.add(dessert.get(3));
				dishlistT3.add(drinks.get(0));
				dishlistT3.add(drinks.get(2));
				
				Ticket T1=new Ticket(name,2,1,dishlistT1);//table 2, waiter id=1
				Ticket T2=new Ticket(name,14,1,dishlistT1);//table 14, waiter id=1
				Ticket T3=new Ticket(name,18,1,dishlistT1);//table 18, waiter id=1
				
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
