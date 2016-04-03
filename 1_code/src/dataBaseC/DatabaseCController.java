package dataBaseC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import com.google.gson.Gson;

import configuration.Configure;
import databaseA.DatabaseAController;

/**
 * Starts the DB C.
 * Chef will use this to continually update the inventory by giving it the name of the dish it started.
 * This will also update the chef when low inventory is met.
 * Waiter will use this when logging on to get the menu.
 * @author cms549
 *
 */
public class DatabaseCController extends Thread {
	
	/**
	 * Port number that Database C will be on.
	 */
	private final static int portNumber = Configure.getPortNumber("DatabaseCController");
	
	/**
	 * Used to convert java objects to JSON format and vice versa.
	 */
	public static Gson jsonConverter = new Gson();
	
	/**
	 * Inventory of restaurant. Maps Ingredient Name to Ingredient
	 */
	private HashMap<String, Ingredient> inventory;
	
	/**
	 * Links name of dish to its dish data. 
	 * This will be used to figure out how much of an ingredient to decrement from the inventory for a started dish.
	 */
	private HashMap<String, DishData> dishData;
	
	/**
	 * Holds the menu. Waiter will need this when logging on.
	 * See Menu.java
	 */
	private static Menu menu;
	

	/**
	 * Constructor
	 * @param listener - socket that this controller will be using
	 */
	public DatabaseCController(Socket listener) {
		currListener=listener;
	}

	/**
	 * Adds ingredient to inventory returns false if ingredient already exists
	 * @param ingredientName - name of ingredient to add to inventory
	 * @param amountLeft - amount of ingredient you have at the time
	 * @param unitOfAmount - unit that the amountLeft is measured in
	 * @param threshold- the amount at which you wish to be notified when inventory is low for this ingredient
	 * 	if you put it a threshold>=amount left or threshold = 0 then this will fail
	 * @return true on success, false on failure
	 */
	public boolean addIngredientToInventory(String ingredientName,Double amountLeft, String unitOfAmount, Double threshold ){
		if(inventory.containsKey(ingredientName)){
			return false;
		}
		if(threshold>=amountLeft || threshold<0){
			return false;
		}
		inventory.put(ingredientName, new Ingredient(ingredientName, amountLeft, unitOfAmount, threshold));
		return true;
	}
	
	/**
	 * Starts a new thread for the DB B controller so it can communicate with one tablet on one thread.
	 * Reads the socket in to determine the request and responds accordingly.
	 * Socket will hang up on waiters after sending them menu and after reading a ticket
	 * Socket won't hang up on chef since chef will be sending information about each dish that was started.
	 */
	public void run(){
		String mess = "";
		try(DataInputStream in = new DataInputStream(currListener.getInputStream()); 
				DataOutputStream out = new DataOutputStream(currListener.getOutputStream())) {
			while(true){
				mess =in.readUTF();
				char first = mess.charAt(0);
				if(first=='A'){ //add ingredinet
					//add the ingredient with the info into the inventory
				}
				else if(first=='R'){//Remove this ingredinet from inventory
					
				}
				else if(first =='d'){//decrement the ingredients for this dish
					
				}
				else if(first=='M'){//Waiter needs menu when loggin in- hang up after you give them the menu
					String jmenu = jsonConverter.toJson(menu);
					out.writeUTF(jmenu);
					in.close();
					out.close();
					currListener.close();
					break;
				}
				else if(first == 'T'){//waiter is sending you a paid ticket so you can save it - hang up after you read it
					String ticket = mess.substring(1);
					//add the ticket to the file that holds all tickets for today
					in.close();
					out.close();
					currListener.close();
					break;
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Before error Read in: "+ mess);
			e.printStackTrace();
		} 
	}
	
	/**
	 * Starts the Database C base thread.
	 * @param args
	 */
	public static void main(String[] args){
		menu = new Menu();
		//set up the inventory
		//set up dishdata converter
		generateDishes();
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (true) {
               new DatabaseAController(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("ERROR: DB C failed to start. Port " + portNumber+" is in use.");
            System.exit(-1);
        }
		
		
	}
	/**
	 * Adds a dish to the menu
	 * @param type - type of dish. (Appetizer, Dessert, Entree)
	 * @param dishname - name of the dish.
	 * @param price - price of the dish.
	 * @return 0 on success and -1 on failure/duplicate dish
	 */
	public static int addDishtoMenu(String type, String dishname, double price){
		Dish newdish = new Dish(dishname,price,type);
		if(menu.menu.containsKey(type)){
			HashMap<String,Dish> tem = menu.menu.get(type);
			if(tem.containsKey(dishname)){
				return -1;
			}else{
				tem.put(dishname, newdish);     //add new dish to the hashmap of the hashmap
                return 0;			
			}
		}else{//type does not exist
			HashMap<String,Dish> temp= new HashMap<String,Dish>();
			temp.put(dishname, newdish);
			menu.menu.put(type,temp);               // places the type in the hashmap
			return 0;
		}
	}
	
	/**
	 * Used for Testing.
	 */
	public static void generateDishes(){
		
		addDishtoMenu("appetizer","Buffalo Wings",7.99);
        addDishtoMenu("appetizer","Bread Sticks",4.99);
        addDishtoMenu("appetizer","Spiced Olives",5.99);
        addDishtoMenu("appetizer","Chips and Guacamole",7.99);
        addDishtoMenu("appetizer","Chicken Soup ",2.99);
        addDishtoMenu("appetizer","Spring Rolls ",4.99);
        addDishtoMenu("appetizer","Prawn Chips ",5.99);
        addDishtoMenu("appetizer","Mixed Apps  ",7.99);
        addDishtoMenu("appetizer","Sample Platter",7.50);
        addDishtoMenu("appetizer","Mozzarella sticks",4.99);
        addDishtoMenu("appetizer","French Fries",5.99);
        addDishtoMenu("appetizer","Pizza Fries",7.99);
        addDishtoMenu("appetizer","Breaded Mushrooms",2.99);
        addDishtoMenu("appetizer","Onion Rings",4.99);
        addDishtoMenu("appetizer","Shrimp Basket",5.99);
        addDishtoMenu("appetizer","Garlic Bread",7.99);
        
        addDishtoMenu("entree","pasta",12.99);
        addDishtoMenu("entree","steak",18.99);
        addDishtoMenu("entree","Eggplant Parmesan",11.99);
        addDishtoMenu("entree","Chicken salad",12.99);
        addDishtoMenu("entree","Chicken Alfredo",12.99);
        addDishtoMenu("entree","Tossed Salad",18.99);
        addDishtoMenu("entree","Fried Chicken Salad ",11.99);
        addDishtoMenu("entree","Tuna Salad",12.99);
        addDishtoMenu("entree","Stuffed Shells",12.99);
        addDishtoMenu("entree","Baked Lasagna",18.99);
        addDishtoMenu("entree","Italian Trio ",11.99);
        addDishtoMenu("entree","Baked Ziti ",12.99);
        addDishtoMenu("entree","Sandwich",12.99);
        addDishtoMenu("entree","Bean Burrito",18.99);
        addDishtoMenu("entree","Cheese Quesedilla ",11.99);
        addDishtoMenu("entree","Carnitas ",12.99);
        
        addDishtoMenu("dessert","cheesecake",8.99);
        addDishtoMenu("dessert","creme brulee",10.99);
        addDishtoMenu("dessert","Tiramasu",13.99);
        addDishtoMenu("dessert","Ice Cream",2.99);
        addDishtoMenu("dessert","Pineapple Cheesecake",8.99);
        addDishtoMenu("dessert","Creme Caramel ",10.99);
        addDishtoMenu("dessert","Peanut Butter Pie",13.99);
        addDishtoMenu("dessert","Lemon Marangue Pie ",2.99);
        addDishtoMenu("dessert","Carrot Cake",8.99);
        addDishtoMenu("dessert","Red Velvet Cake ",10.99);
        addDishtoMenu("dessert","Bread Pudding",13.99);
        addDishtoMenu("dessert","Chocolate Ice Cream ",2.99);
        addDishtoMenu("dessert","Double Chocolate Brownie",8.99);
        addDishtoMenu("dessert","Coconut Cake ",10.99);
        addDishtoMenu("dessert","Peppermint Cupcake",13.99);
        addDishtoMenu("dessert","Mini Cupcakes",2.99);
        
        addDishtoMenu("drinks","water",0.00);
        addDishtoMenu("drinks","coke",1.99);
        addDishtoMenu("drinks","pepsi",1.99);
        addDishtoMenu("drinks","milk",1.49);
        addDishtoMenu("drinks","tea",1.99);
        addDishtoMenu("drinks","coffee",1.99);
        addDishtoMenu("drinks","apple juice",1.99);
        addDishtoMenu("drinks","orange juice",1.49);
        addDishtoMenu("drinks","green juice",1.99);
        addDishtoMenu("drinks","tomato juice",1.99);
        addDishtoMenu("drinks","red wine",1.99);
        addDishtoMenu("drinks","white wine",1.49);
		
	}
	
}
