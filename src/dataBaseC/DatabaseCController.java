package dataBaseC;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.google.gson.Gson;

import com.google.gson.Gson;

import configuration.Configure;

/**
<<<<<<< HEAD
 * Starts the DB C.
 * Chef will use this to continually update the inventory by giving it the name of the dish it started.
 * This will also update the chef when low inventory is met.
 * Waiter will use this when logging on to get the menu.
=======
 * Starts the DB C 
 * Host will use this to grab the list of tables 
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30
 * @author cms549
 *
 */
public class DatabaseCController extends Thread {
	
	/**
<<<<<<< HEAD
	 * Port number that Database C will be on.
=======
	 * port number DB B will be listening on
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30
	 */
	private final static int portNumber = Configure.getPortNumber("DatabaseCController");
	
	/**
	 * used to convert java objects to JSON format and vice versa.
	 */
	public static Gson jsonConverter = new Gson();
	
	/**
<<<<<<< HEAD
	 * Socket to one tablet that DB C will use to handle one request.
	 * Each DBBController will have their own as there will be a new DBBController for each request.
=======
	 * Listens to one tablet.
	 * Each DBCController thread gets one.
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30
	 */
	private Socket currListener;
	
	/**
<<<<<<< HEAD
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
	
=======
	 * Holds the list of tables. 
	 * See TableList.java
	 */
	public static TableList listOfTables;
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30

	/**
	 * Constructor
	 * @param listener
	 */
	public DatabaseCController(Socket listener) {
		currListener=listener;
	}

	/**
<<<<<<< HEAD
	 * adds ingredient to inventory returns false if ingredient already exists
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
=======
	 * Adds table to the list of tables
	 * @param tabNum - table number you wish to add
	 * @param maxOccupancy - max amount of people that can fit at the table
	 * @return true on success, false on failure
	 */
	public static boolean addTable(int tabNum, int maxOccupancy){
		if(listOfTables.hm.containsKey(tabNum)){
			return false;
		}
		listOfTables.hm.put(tabNum, new Table(tabNum, maxOccupancy));
		return true;
	}
	
	
	/**
	 * Each request will get its own thread. This will be used to send the host the list of tables
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30
	 */
	public void run(){
		
		try {
			DataInputStream in = new DataInputStream(currListener.getInputStream());
			DataOutputStream out = new DataOutputStream(currListener.getOutputStream());
			while(true){
				String mess =in.readUTF();
				char first = mess.charAt(0);
<<<<<<< HEAD
				if(first=='A'){ //add ingredinet
					//add the ingredient with the info into the inventory
				}
				else if(first=='R'){//Remove this ingredinet from inventory
					
				}
				else if(first =='d'){//decrement the ingredients for this dish
					
=======
				if(first=='T'){ //send the host a list of tables
					out.writeUTF(jsonConverter.toJson(listOfTables));
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30
				}
				else if(first=='M'){//Waiter needs menu when loggin in
					String jmenu = jsonConverter.toJson(menu);
					out.writeUTF(jmenu);
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Disconnected from a client.");
		} 
	}
	
	/**
<<<<<<< HEAD
	 * Starts the Database C base thread.
	 * @param args
	 */
	public static void main(String[] args){
		menu = new Menu();
		//set up the inventory
		//set up dishdata converter
		generateDishes();
		ServerSocket server=null;
=======
	 * Starts base thread for DB B
	 * @param args
	 */
	public static void main(String[] args){
		listOfTables = new TableList();
		generateTables();
		ServerSocket server = null;
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30
		try {
			server = new ServerSocket(portNumber);
			
			while(true){
				Socket listener = server.accept();
				Thread t= new DatabaseCController(listener);
				t.start();
			}
		} catch (Exception e) {
			if(server!=null){
				try {
					server.close();
				} catch (IOException e1) {}
			}
			System.out.println("ERROR: FAILED TO START SERVER.");
<<<<<<< HEAD
			
=======
>>>>>>> 1533b2efae091bb850331c5136faf388f3f9aa30
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
		addDishtoMenu("appetizer","buffalo wings",7.99);
		addDishtoMenu("appetizer","bread sticks",4.99);
		addDishtoMenu("entree","pasta",12.99);
		addDishtoMenu("entree","steak",18.99);
		addDishtoMenu("dessert","cheesecake",8.99);
		addDishtoMenu("dessert","creme brulee",10.99);
		addDishtoMenu("drinks","water",0.00);
		addDishtoMenu("drinks","coke",1.99);
		
	}
	
	/**
	 * Used for testing
	 */
	public static void generateTables(){
		addTable(1,4);
		addTable(2,4);
		addTable(3,4);
		addTable(4,6);
		addTable(5,6);
		addTable(6,2);
		addTable(7,2);
		addTable(8,4);
		addTable(9,4);
		addTable(10,4);
		addTable(11,6);
		addTable(12,6);
		addTable(13,6);
		addTable(14,2);
		addTable(15,2);
		addTable(16,2);
	}	
}
