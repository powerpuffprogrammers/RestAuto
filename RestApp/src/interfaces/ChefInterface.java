package interfaces;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

import java.util.ArrayList;
import java.util.HashMap;

import dataBaseC.Table;
import databaseB.Dish;
import databaseB.Ingredient;
import databaseB.Ticket;
import messageController.Message;
import messageController.RecieverInfo;
import messageController.SenderInfo;

//Starts the DB B
public class ChefInterface {
	
	//Maps Ingredient Name to Ingredient
	private HashMap<String, Ingredient> inventory;
	
	//Ticket Queue = holds all ticket orders
	private ArrayList<Ticket> ticketQueue;
	
	public ChefInterface(){
		//Pull this from SQL
		inventory = new HashMap<String, Ingredient>();
		ticketQueue = new ArrayList<Ticket>();
	}
	
	//adds ingredient to inventory returns false if ingredient already exists
	public boolean addIngredientToInventory(String ingredientName,Double amountLeft, String unitOfAmount, Double threshold ){
		if(inventory.containsKey(ingredientName)){
			return false;
		}
		inventory.put(ingredientName, new Ingredient(ingredientName, amountLeft, unitOfAmount, threshold));
		return true;
	}
	
	
	public void chefTicketListener(Ticket ticket){
		if(ticket!=null){
			ticketQueue.add(ticket);
		}
	}

	public void orderTicketQ(){
		//put it in order
		//updateScreen()
	}
	
//handles all the host actions (like pushing buttons) and updates the screen and list of tables correctly
	public void chefEventListenter(ChefEvent e){
		//if event is changing status of dish
		if(e.type == 's'){
			
		}
		//if event is hitting back button
		else if(e.type == 'b'){
			
		}
		//if event is opening a ticket
		else if(e.type == 'o'){
			
		}
		//if event is sending a notification to manager
		else if(e.type == 'm'){
			chefMessageListener(new Message(new SenderInfo('c'), new RecieverInfo('m'), "Chef needs Assistance."));
		}
		
		redrawChefScreen();
		
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
//	public void changeDish(Dish d, char dishStatus);
	
//	public void removeIngredientToInventory(String ingredientName);
}
