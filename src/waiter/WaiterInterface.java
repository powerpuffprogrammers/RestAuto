package waiter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import javax.swing.JFrame;
import com.google.gson.Gson;
import configuration.Configure;
import databaseB.Dish;
import databaseB.Menu;
import databaseB.Ticket;

public class WaiterInterface {
	
	private final static String MCdomainName = Configure.getDomainName("MessageController");
	private final static int MCportNumber = Configure.getPortNumber("MessageController");
	
	public static Gson jsonConverter = new Gson();
	
	/**
	 * Employee ID - this will be used to ID the tablet for the Message Controller
	 */
	long empID;
	
	/**
	 * When this is true I return from constructor back to log in page
	 */
	public boolean loggedOut;
	
	/**
	 * current ticket open, null if none is open
	 */
	Ticket currTicket;
	
	//Appetizer, Drink, Entree, Desert
	String currDishType;
	
	HashMap<Integer, Ticket> listOfTickets;
	
	Menu menu;
	
	WaiterTickListScreen ticketListScreen;
	WaiterOneTicketScreen oneTickScreen;
	
	public WaiterInterface(JFrame frame, long eID) {
		listOfTickets = new HashMap<Integer, Ticket>();
		empID=eID;
		loggedOut=false;
		
		//load the menu
		loadMenu();
		
		//set up MC
		setUpMessageController();
		
		//create waiter screen for list of tickets
		ticketListScreen = new WaiterTickListScreen(this);
		//set the screen to the waiter panel
		frame.setContentPane(ticketListScreen);
		
		oneTickScreen = new WaiterOneTicketScreen(this);
	}
	
	public void runUntilLogOut(){
		//Don't return until i logged out
		while(!loggedOut){
			
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
			Thread t= new WaiterMessageHandler(listener,empID, this);
			t.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void addDishToTicket(Dish dish) {
		currTicket.addDishToTicket(dish.makeCopyOfDish());
	}

	public void removeDishFromTicket(int indexInTicket) {
		currTicket.removeDishFromTicket(indexInTicket);
	}

	public double getPriceOfDish(String dish, String dishType) {
		Dish d =menu.menu.get(dishType).get(dish);
		return d.price;
	}

}
