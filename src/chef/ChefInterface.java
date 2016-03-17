package chef;


import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

import com.google.gson.Gson;

import configuration.Configure;
import dataBaseC.Table;
import databaseB.Dish;
import databaseB.DishData;
import databaseB.Ingredient;
import databaseB.Ticket;
import messageController.Message;
import messageController.RecieverInfo;
import messageController.SenderInfo;
import waiter.WaiterMessageHandler;

//Starts the DB B
public class ChefInterface {
	private final static String MCdomainName = Configure.getDomainName("MessageController");
	private final static int MCportNumber = Configure.getPortNumber("MessageController");
	
	public static Gson jsonConverter = new Gson();
	long empID;
	String name;
	
	public boolean loggedOut;
	
	
	
	//gives you the currTicketNumber you should give the next ticket created
	static long currTicketNumber =0;
	
	
	//Ticket number to ticket
	HashMap<Long, Ticket>ticketLookup;
	
	//Ticket Queue = holds all ticket orders
	public ArrayList<Long> ticketQueueUnstarted;
	public ArrayList<Long> ticketQueuesemiStarted;
	public ArrayList<Long> ticketQueueStarted;
	public ArrayList<Long> ticketQueueFinished;
	
	private ChefPanel chefPanel;
	
	public ChefInterface(JFrame frame, long eID, String empName){
		name= empName;
		empID = eID;
		//Pull this from SQL
		ticketQueueUnstarted = new ArrayList<Long>();
		ticketQueuesemiStarted = new ArrayList<Long>();
		ticketQueueStarted = new ArrayList<Long>();
		ticketQueueFinished = new ArrayList<Long>();
		ticketLookup = new HashMap<Long, Ticket>();
		
		setUpMessageController();
		
		chefPanel = new ChefPanel(this);
		frame.setContentPane(chefPanel);
		
		
	}

	
	//This listener will be used to read incoming tickets from servers and place them on the Q
	public void chefTicketListener(Ticket ticket){
		if(ticket!=null){
			//set up the index for this ticket
			ticket.ticketNumber = currTicketNumber;
			//add the ticket to the end of the unstarted list since it is unstarted
			ticketQueueUnstarted.add(currTicketNumber);
			ticketLookup.put(currTicketNumber, ticket);
			currTicketNumber++;
		}
	}
	    

	/**
	 * decrements each item in the dish's inventory amount by the proper amount for this dish
	 * @param dish
	 */
	private void decrementInventoryForDish(Dish dish) {
			//Send message to DB B to decrement this dish
			//message should start with a D and end with the name of the dish (and thats it)
		}
	

	/**
	 * Changes the list that the ticket is on. ie: takes ticket off of unstarted and adds it to semi started
	 * @param oldstatus
	 * @param t
	 */
	private void changeTicketLocation(char oldstatus, Ticket t) {
		if(oldstatus == 'u'){
			ticketQueueUnstarted.remove(ticketQueueUnstarted.indexOf(t.ticketNumber));
		}
		else if(oldstatus=='s'){
			ticketQueuesemiStarted.remove(ticketQueuesemiStarted.indexOf(t.ticketNumber));
		}
		else if(oldstatus =='S'){
			ticketQueueStarted.remove(ticketQueueStarted.indexOf(t.ticketNumber));
		}
		else{//finished
			ticketQueueFinished.remove(ticketQueueFinished.indexOf(t.ticketNumber));
		}
		
		char newStatus = t.status;
		if(newStatus == 'u'){
			ticketQueueUnstarted.add(t.ticketNumber);
		}
		else if(newStatus=='s'){
			ticketQueuesemiStarted.add(t.ticketNumber);
		}
		else if(newStatus =='S'){
			ticketQueueStarted.add(t.ticketNumber);
		}
		else{//finished
			ticketQueueFinished.add(t.ticketNumber);
		}
	}

	private void setUpMessageController() {
		Socket listener;
		try {
			listener = new Socket(MCdomainName, MCportNumber);
			Thread t= new ChefMessageHandler(listener,empID, this);
			t.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void generateTickets(){
		
	}


	public void runUntilLogOut() {
		//Don't return until i logged out
		while(!loggedOut){
			
		}
		
	}


	
	//To implement:
/*
	//adds ingredient to inventory returns false if ingredient already exists
	public boolean addIngredientToInventory(String ingredientName,Double amountLeft, String unitOfAmount, Double threshold ){
		//send message to DBBcontroller
		return true;
	}
//	public void removeIngredientToInventory(String ingredientName);
*/
}
