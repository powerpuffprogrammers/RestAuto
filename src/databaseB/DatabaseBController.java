package databaseB;

//LAST CODED BY: CHRISTINA SEGERHOLM ON 3/16

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import configuration.Configure;

/**
 * Starts the DB B- Chef will use this to continually update the inventory by giving it the name of the dish it started
 * This will also update the chef when low inventory is met
 * @author cms549
 *
 */
public class DatabaseBController extends Thread {
	
	private final static int portNumber = Configure.getPortNumber("DatabaseBController");
	
	private Socket currListener;
	
	//Maps Ingredient Name to Ingredient
	private HashMap<String, Ingredient> inventory;
	
	/**
	 * Links name of dish to its dish data
	 */
	private HashMap<String, DishData> dishData;
	
	//First String maps category (Drink, App, Entree, Dessert) to a map
	//Inner map maps dish name to dish
	private static HashMap<String, HashMap<String,Dish>> menu;
	

	public DatabaseBController(Socket listener) {
		currListener=listener;
	}

	//adds ingredient to inventory returns false if ingredient already exists
	public boolean addIngredientToInventory(String ingredientName,Double amountLeft, String unitOfAmount, Double threshold ){
		if(inventory.containsKey(ingredientName)){
			return false;
		}
		inventory.put(ingredientName, new Ingredient(ingredientName, amountLeft, unitOfAmount, threshold));
		return true;
	}
	
	public void run(){
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			DataOutputStream out = new DataOutputStream(currListener.getOutputStream());
			while(true){
				String mess =in.readUTF();
				char first = mess.charAt(0);
				if(first=='A'){ //add ingredinet
					//add the ingredient with the info into the inventory
				}
				else if(first=='R'){//Remove this ingredinet from inventory
					
				}
				else if(first =='d'){//decrement the ingredients for this dish
					
				}
				else if(first=='M'){//Waiter needs menu when loggin in
					String jmenu = JSON.toJson(menu);
					out.writeUTF(jmenu);
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Disconnected from a client.");
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args){
		//set up the inventory
		//set up dishdata converter aka menu
		
		try {
			ServerSocket server = new ServerSocket(portNumber);
			
			while(true){
				Socket listener = server.accept();
				Thread t= new DatabaseBController(listener);
				t.start();
			}
		} catch (Exception e) {
			System.out.println("ERROR: FAILED TO START SERVER.");
			e.printStackTrace();
		}
		
		
	}
	
	
}
