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
	
	public WaiterInterface(JFrame frame, long eID) {
		listOfTickets = new HashMap<Integer, Ticket>();
		empID=eID;
		loggedOut=false;
		
		//load the menu
		loadMenu();
		
		//set up MC
		setUpMessageController();
		
		//create waiter panel
		WaiterPanel waiterPanel = new WaiterPanel();
		//set the screen to the waiter panel
		frame.setContentPane(waiterPanel);
		
		//Don't return until i logged out
		while(!loggedOut){}
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

	//An event should be generated each time the waiter clicks something on his screen
	public void waiterEventListener ( WaiterEvent e ){
		
		if(e.type =='m' ){
			//notify manager problem with table
		}
		else if(e.type=='b'){
			//go back to previous screen
		}
		else if(e.type=='p'){
			//send ticket to printer
		}
		else if(e.type=='s'){
			//send ticket to chef
		}
		else if(e.type=='c'){
			//send message to host that you are paid
		}
		else if(e.type =='a' ){ //if you are adding a dish
	            double p = getPriceOfDish(e.dishName, currDishType);
	            if(p>=0){
	            	 currTicket.price = currTicket.price + p;
	            	 addDishToTicket(e.dishName, currDishType);
	            }
	    }
	    else if(e.type== 'r'){
	        double p = getPriceOfDish(e.dishName, e.dishType);
	        if(p>=0){
	        	 currTicket.price = currTicket.price -p;
	        	 removeDishFromTicket(e.dishName, e.dishType);
	        }
	    }
		redrawScreen();
	}

	private void redrawScreen() {
		
	}

	private void addDishToTicket(String dishName, String currDishType2) {
		
	}

	private void removeDishFromTicket(String dishName, String dishType) {
		
	}

	private double getPriceOfDish(String dish, String dishType) {
		Dish d =menu.menu.get(dishType).get(dish);
		return d.price;
	}

}
