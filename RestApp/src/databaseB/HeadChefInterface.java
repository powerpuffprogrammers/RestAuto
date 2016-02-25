package databaseB;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 2/24

import java.util.ArrayList;
import java.util.HashMap;

//Starts the DB B
public class HeadChefInterface {
	
	//Maps Ingredient Name to Ingredient
	private HashMap<String, Ingredient> inventory;
	
	//Ticket Queue = holds all ticket orders
	private ArrayList<Ticket> ticketQueue;
	
	public HeadChefInterface(){
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
	
	public boolean addTicketToQueue(Ticket ticket){
		if(ticket==null){
			return false;
		}
		ticketQueue.add(ticket);
		return true;
	}

	public void orderTicketQ(){
		//put it in order
		//updateScreen()
	}
}
