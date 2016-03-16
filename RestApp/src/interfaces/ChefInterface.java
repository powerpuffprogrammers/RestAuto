package interfaces;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import dataBaseC.Table;
import databaseB.Dish;
import databaseB.DishData;
import databaseB.Ingredient;
import databaseB.Ticket;
import messageController.Message;
import messageController.RecieverInfo;
import messageController.SenderInfo;

//Starts the DB B
public class ChefInterface {
	
	//gives you the currTicketNumber you should give the next ticket created
	static long currTicketNumber =0;
	
	//Ticket number to ticket
	HashMap<Long, Ticket>ticketLookup;
	
	//Ticket Queue = holds all ticket orders
	private ArrayList<Long> ticketQueueUnstarted;
	private ArrayList<Long> ticketQueuesemiStarted;
	private ArrayList<Long> ticketQueueStarted;
	private ArrayList<Long> ticketQueueFinished;
	
	
	public ChefInterface(JFrame frame){
		//Pull this from SQL
		ticketQueueUnstarted = new ArrayList<Long>();
		ticketQueuesemiStarted = new ArrayList<Long>();
		ticketQueueStarted = new ArrayList<Long>();
		ticketQueueFinished = new ArrayList<Long>();
		ticketLookup = new HashMap<Long, Ticket>();
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
	 * handles all the host actions (like pushing buttons) and updates the screen and list of tables correctly
	 * @param e = chef event = holds all info for button pushed
	 */
	public void chefEventListenter(ChefEvent e){
		//if event is changing status of dish
		if(e.type == 's'){
			e.dish.changeStatus(e.newStatusOfDish);
			if(e.newStatusOfDish == 's'){
				decrementInventoryForDish(e.dish);
			}
			
			Ticket t = ticketLookup.get(e.ticketNumber);
			char oldstatus = t.updateStatus();
			if( t.status != oldstatus ){//if the ticket changed its status
				if(t.status == 'f'){ //if the ticket is finished
					//send a message to the server to let them know it is ready
					chefMessageSender(new Message(new SenderInfo('c'), new RecieverInfo('s'), "Hot Food."));
				}
				changeTicketLocation(oldstatus, t);
			}
			
		}
		//if event is hitting back button
		else if(e.type == 'b'){
			//move back a screen
		}
		//if event is opening a ticket
		else if(e.type == 'o'){
			//change screen type to show current ticket
			//showTicketOnScreen(e.ticketNumber);
		}
		//if event is pressing get manager button -> sending a notification to manager
		else if(e.type == 'm'){
			chefMessageSender(new Message(new SenderInfo('c'), new RecieverInfo('m'), "Chef needs Assistance."));
		}
		else if(e.type == 'd'){ //delete a ticket because chef signifies that it was picked up
			ticketQueueFinished.remove(ticketQueueFinished.indexOf(e.ticketNumber));
		}
		redrawChefScreen();
		
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

	private void chefMessageSender(Message message) {
		
		
	}

	//handles messages that it gets from manager or waiter
	public void chefMessageListener(Message m){	
		if(m.getSenderPosition() == 'w'){ //if waiter sent a message
			
		}            
		else if(m.getSenderPosition()== 'm'){//if manager sent the message = 
			    
		}
		redrawChefScreen();
	}

	private void redrawChefScreen() {
		// TODO Auto-generated method stub
		
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
